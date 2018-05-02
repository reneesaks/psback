package com.perfectstrangers.controller.pub;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.dto.NewUserDTO;
import com.perfectstrangers.dto.UsernameDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.error.MailServiceNoConnectionException;
import com.perfectstrangers.error.UsernameExistsException;
import com.perfectstrangers.error.UsernameIsActivatedException;
import com.perfectstrangers.service.GenericService;
import com.perfectstrangers.service.RegistrationService;
import com.perfectstrangers.util.EmailConstructor;
import java.io.IOException;
import java.time.Instant;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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

/*
 * TODO:
 * Should the resend registration confirmation only respond with "HttpStatus.OK"? Potential security problem.
 * This endpoint could be used to fish out valid emails because the response differs when the email is already
 * activated, thus giving away the email.
 */

/**
 * Handles all registration associated events that are exposed by an endpoint.
 */
@RestController
@RequestMapping("/api/public/register")
@Profile({"production", "deployment"})
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private RegistrationService registrationService;
    private GenericService genericService;
    private ApplicationEventPublisher eventPublisher;
    private MailSender mailSender;
    private EmailConstructor emailConstructor;

    @Value("${client.loginUrl}")
    String clientLoginUrl;

    @Autowired
    public RegistrationController(
            RegistrationService registrationService,
            GenericService genericService,
            ApplicationEventPublisher eventPublisher,
            MailSender mailSender,
            EmailConstructor emailConstructor) {
        this.registrationService = registrationService;
        this.genericService = genericService;
        this.eventPublisher = eventPublisher;
        this.mailSender = mailSender;
        this.emailConstructor = emailConstructor;
    }

    /**
     * Creates new user. If any exception is thrown it will roll back any database changes.
     *
     * @param newUserDTO User object for validation.
     * @return the newly created user.
     * @throws MailServiceNoConnectionException when email service is not available.
     * @throws UsernameExistsException when the username already exists.
     */
    @PostMapping(value = "/new-user")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public User registerNewUser(@RequestBody @Valid NewUserDTO newUserDTO)
            throws MailServiceNoConnectionException, UsernameExistsException {

        User user = new User();
        user.setPassword(newUserDTO.getPassword());
        user.setEmail(newUserDTO.getEmail());
        user.setRegDate(Instant.now().toString());
        User registeredUser = registrationService.registerNewUserAccount(user);
        registrationService.createVerificationToken(registeredUser);
        String token = registrationService.getVerificationTokenByUser(registeredUser).getToken();

        try {
            mailSender.send(emailConstructor.constructConfirmationEmail(token, registeredUser));
        } catch (MailSendException e) {
            LOGGER.error("Failed email to: " + user.getEmail() + ". " + e.getFailedMessages());
            throw new MailServiceNoConnectionException();
        }

        LOGGER.info("User with email " + user.getEmail() + " is registered. Waiting for activation.");
        return user;
    }

    /**
     * The activation link sent to user email contains the token. This handles the verification process and
     * redirects the user to an URL.
     *
     * @param token The token that was generated for the activation link.
     * @param httpServletResponse HttpServletResponse
     * @return Redirects the user to a specified URL.
     */
    @GetMapping(value = "/registration-confirm")
    @ResponseStatus(HttpStatus.OK)
    public String confirmRegistration(@RequestParam("token") String token,
            HttpServletResponse httpServletResponse) {

        String validation = registrationService.validateVerificationToken(token);
        if (validation != null) {
            throw new BadCredentialsException(validation);
        }

        // Activate our user
        User user = registrationService.getVerificationToken(token).getUser();
        user.setActivated(true);
        registrationService.saveRegisteredUser(user);

        try {
            httpServletResponse.sendRedirect(this.clientLoginUrl);
        } catch (IOException | IllegalStateException e) {
            LOGGER.error("Problem redirecting to login URL. " + e);
        }
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
                mailSender.send(emailConstructor.constructResendConfirmationEmail(newToken.getToken(), user));
            } catch (MailSendException e) {
                LOGGER.error("Failed creating user: " + user.getEmail() + "." + e.getFailedMessages());
                throw new MailServiceNoConnectionException();
            }

        } else {
            throw new UsernameIsActivatedException();
        }
        return usernameDTO;
    }
}
