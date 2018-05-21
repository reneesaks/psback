package com.professionalstrangers.controller.pub;

import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.VerificationToken;
import com.professionalstrangers.dto.NewUserDTO;
import com.professionalstrangers.dto.UsernameDTO;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.error.MailServiceNoConnectionException;
import com.professionalstrangers.error.UsernameExistsException;
import com.professionalstrangers.error.UsernameIsActivatedException;
import com.professionalstrangers.service.GenericService;
import com.professionalstrangers.service.RegistrationService;
import com.professionalstrangers.util.EmailConstructor;
import java.time.Instant;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles all registration associated events that are exposed by an endpoint.
 */
@RestController
@RequestMapping("/api/public/register")
@Profile({"staging", "production"})
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private RegistrationService registrationService;
    private GenericService genericService;
    private MailSender mailSender;

    @Value("${client.serverAddress}")
    String serverAddress;

    @Autowired
    public RegistrationController(
            RegistrationService registrationService,
            GenericService genericService,
            MailSender mailSender) {
        this.registrationService = registrationService;
        this.genericService = genericService;
        this.mailSender = mailSender;
    }

    /**
     * Creates new user. If any exception is thrown it will roll back any database changes.
     *
     * @param newUserDTO User object for validation.
     * @throws MailServiceNoConnectionException when email service is not available.
     * @return null if OK
     */
    @PostMapping(value = "/new-user")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public String registerNewUser(@RequestBody @Valid NewUserDTO newUserDTO)
            throws MailServiceNoConnectionException {

        String token;
        User user = new User();
        User registeredUser;

        try {
            user.setPassword(newUserDTO.getPassword());
            user.setEmail(newUserDTO.getEmail());
            user.setRegDate(Instant.now().toString());
            registeredUser = registrationService.registerNewUserAccount(user);
            registrationService.createVerificationToken(registeredUser);
            token = registrationService.getVerificationTokenByUser(registeredUser).getToken();
        } catch (UsernameExistsException e) {
            LOGGER.info("User with email " + user.getEmail() + " already exists.");
            return null;
        }

        try {
            mailSender.send(EmailConstructor.constructConfirmationEmail(
                    this.serverAddress, token, registeredUser));
        } catch (MailSendException e) {
            LOGGER.error("Failed email to: " + user.getEmail() + ". " + e.getFailedMessages());
            throw new MailServiceNoConnectionException();
        }

        LOGGER.info("User with email " + user.getEmail() + " is registered. Waiting for activation.");
        return null;
    }

    /**
     * The activation link sent to user email contains the token. This handles the verification process and
     * redirects the user to an URL.
     *
     * @param token The token that was generated for the activation link.
     * @return null if OK
     */
    @GetMapping(value = "/registration-confirm")
    @ResponseStatus(HttpStatus.OK)
    public String confirmRegistration(@RequestParam("token") String token) {

        String validation = registrationService.validateVerificationToken(token);
        if (validation != null) {
            throw new BadCredentialsException(validation);
        }

        // Activate our user
        User user = registrationService.getVerificationToken(token).getUser();
        user.setActivated(true);
        registrationService.saveRegisteredUser(user);
        LOGGER.info("User with email " + user.getEmail() + " is activated.");

        return null;
    }

    /**
     * Sends a new activation link for the specified username.
     *
     * @param usernameDTO Username in object form.
     * @return Returns username object.
     * @throws EntityNotFoundException when the username doesn't exist.
     * @throws UsernameIsActivatedException when the username is already activated.
     * @throws MailServiceNoConnectionException when mail service fails.
     */
    @PostMapping(value = "/resend-registration-confirmation")
    @ResponseStatus(HttpStatus.OK)
    public UsernameDTO resendRegistrationToken(@RequestBody @Valid UsernameDTO usernameDTO)
            throws EntityNotFoundException, UsernameIsActivatedException, MailServiceNoConnectionException {

        User user = genericService.getUserByEmail(usernameDTO.getEmail());
        if (!user.isActivated()) {
            VerificationToken newToken = registrationService.createNewVerificationToken(user);

            try {
                mailSender.send(EmailConstructor.constructResendConfirmationEmail(
                        this.serverAddress, newToken.getToken(), user));
            } catch (MailSendException e) {
                LOGGER.error("Failed creating user: " + user.getEmail() + "." + e.getFailedMessages());
                throw new MailServiceNoConnectionException();
            }

        } else {
            throw new UsernameIsActivatedException();
        }
        LOGGER.info("User with email " + user.getEmail() + " asked to resend registration confirmation.");
        return usernameDTO;
    }
}
