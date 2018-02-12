package com.perfectstrangers.controller.pub;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.dto.UserDTO;
import com.perfectstrangers.dto.UsernameDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.error.MailServiceNoConnectionException;
import com.perfectstrangers.error.UsernameExistsException;
import com.perfectstrangers.error.UsernameIsActivatedException;
import com.perfectstrangers.event.OnRegistrationCompleteEvent;
import com.perfectstrangers.service.GenericService;
import com.perfectstrangers.service.RegistrationService;
import com.perfectstrangers.util.EmailConstructor;
import java.io.IOException;
import java.util.Calendar;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

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

    @Value("${serverAddress}")
    String serverAddress;

    @Autowired
    public RegistrationController(
            RegistrationService registrationService,
            GenericService genericService,
            ApplicationEventPublisher eventPublisher,
            MailSender mailSender) {
        this.registrationService = registrationService;
        this.genericService = genericService;
        this.eventPublisher = eventPublisher;
        this.mailSender = mailSender;
    }

    /**
     * Creates new user and hashes password with SHA256. If any exception is thrown it will roll back any
     * database changes.
     *
     * @param userDTO User object for validation.
     * @param request WebRequest
     * @return the newly created user.
     * @throws MailServiceNoConnectionException when email service is not available.
     * @throws UsernameExistsException when the username already exists.
     */
    @PostMapping(value = "/new-user")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public User registerNewUser(@RequestBody @Valid UserDTO userDTO, WebRequest request)
            throws MailServiceNoConnectionException, UsernameExistsException {

        User user = new User();
        String sha256 = sha256Hex(userDTO.getPassword());
        user.setPassword(sha256);
        user.setEmail(userDTO.getEmail());
        User registered = registrationService.registerNewUserAccount(user);

        try {
            // Trigger our registration complete event that will send the activation email
            eventPublisher
                    .publishEvent(new OnRegistrationCompleteEvent(
                            registered,
                            request.getLocale(),
                            this.serverAddress));
            LOGGER.info("User with email " + user.getEmail() + " is registered. Waiting for activation.");
        } catch (MailSendException e) {
            LOGGER.error("Error creating user with email: " + user.getEmail() + ". "
                    + "Message: " + e.getFailedMessages());
            throw new MailServiceNoConnectionException();
        }
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

        VerificationToken verificationToken = registrationService.getVerificationToken(token);
        if (verificationToken == null) {
            return "Invalid token!";
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Verification link has expired!";
        }

        // Activate our user
        User user = verificationToken.getUser();
        user.setActivated(true);
        registrationService.saveRegisteredUser(user);

        try {
            httpServletResponse.sendRedirect("https://www.google.com");
        } catch (IOException e) {
            LOGGER.error("Problem redirecting to login URL. " + e);
        }
        return "Registration successful!";
    }

    /**
     * Sends a new activation link for the specified username.
     *
     * @param usernameDTO Username in object form.
     * @return Returns username object.
     * @throws EntityNotFoundException Occurs when the username doesn't exist.
     * @throws UsernameIsActivatedException Occurs when the username is already activated.
     */
    @PostMapping(value = "/resend-registration-confirmation")
    @ResponseStatus(HttpStatus.OK)
    public UsernameDTO resendRegistrationToken(@RequestBody @Valid UsernameDTO usernameDTO)
            throws EntityNotFoundException, UsernameIsActivatedException {

        User user = genericService.getUserByEmail(usernameDTO.getEmail());
        if (!user.isActivated()) {
            VerificationToken newToken = registrationService.createNewVerificationToken(user);
            SimpleMailMessage email = new EmailConstructor()
                    .constructResendConfirmationEmail(serverAddress, newToken.getToken(), user);
            mailSender.send(email);
        } else {
            throw new UsernameIsActivatedException();
        }
        return usernameDTO;
    }
}
