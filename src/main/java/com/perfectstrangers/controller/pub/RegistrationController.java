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
import com.perfectstrangers.service.UserService;
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


/**
 * Should the resend registration confirmation only respond with HttpStatus.OK? Potential security problem.
 * This endpoint could be used to fish out valid emails because the response differs when the email is already
 * activated, thus giving away the email.
 */

@RestController
@RequestMapping("/api/public/register")
@Profile({"production", "deployment"})
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private UserService userService;
    private ApplicationEventPublisher eventPublisher;
    private MailSender mailSender;

    @Value("${serverAddress}")
    String serverAddress;

    @Autowired
    public RegistrationController(
            UserService userService,
            ApplicationEventPublisher eventPublisher,
            MailSender mailSender) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.mailSender = mailSender;
    }

    @PostMapping(value = "/new-user")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class) // If any exception is thrown, roll back db changes
    public User registerNewUser(@RequestBody UserDTO userDTO, WebRequest request)
            throws MailServiceNoConnectionException, UsernameExistsException {

        // Create new user and hash password with SHA256
        User user = new User();
        String sha256 = sha256Hex(userDTO.getPassword());
        user.setPassword(sha256);
        user.setEmail(userDTO.getEmail());
        User registered = userService.registerNewUserAccount(user);

        try {
            // Trigger the event listener
            String appUrl = serverAddress;
            eventPublisher
                    .publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
            LOGGER.info("User with email " + user.getEmail() + " is registered. Waiting for activation.");
        } catch (MailSendException e) {
            LOGGER.error("Error creating user with email: " + user.getEmail() + ". "
                    + "Message: " + e.getFailedMessages());
            throw new MailServiceNoConnectionException();
        }
        return user;
    }

    @GetMapping(value = "/registration-confirm")
    @ResponseStatus(HttpStatus.OK)
    public String confirmRegistration(@RequestParam("token") String token,
            HttpServletResponse httpServletResponse) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "Invalid token!";
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Verification link has expired!";
        }

        User user = verificationToken.getUser();
        user.setActivated(true);
        userService.saveRegisteredUser(user);

        try {
            httpServletResponse.sendRedirect("https://www.google.com");
        } catch (IOException e) {
            LOGGER.error("Problem redirecting to login URL. " + e);
        }
        return "Registration successful!";
    }

    @PostMapping(value = "/resend-registration-confirmation")
    @ResponseStatus(HttpStatus.OK)
    public UsernameDTO resendRegistrationToken(
            @RequestBody @Valid UsernameDTO usernameDTO)
            throws EntityNotFoundException, UsernameIsActivatedException {

        User user = userService.getUserByEmail(usernameDTO.getEmail());
        if (!user.isActivated()) {
            VerificationToken oldToken = userService.getVerificationTokenByUser(user);
            VerificationToken newToken = userService.createNewVerificationToken(oldToken.getToken());
            SimpleMailMessage email = new EmailConstructor()
                    .constructResendConfirmationEmail(serverAddress, newToken.getToken(), user);
            mailSender.send(email);
        } else {
            throw new UsernameIsActivatedException();
        }
        return usernameDTO;
    }
}
