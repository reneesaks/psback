package com.perfectstrangers.controller.pub;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.dto.PasswordResetDTO;
import com.perfectstrangers.dto.UsernameDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.error.MailServiceNoConnectionException;
import com.perfectstrangers.service.GenericService;
import com.perfectstrangers.service.UserService;
import com.perfectstrangers.util.EmailConstructor;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles password resetting for users.
 */
@RestController
@RequestMapping("/api/public/password-reset/")
@Profile({"production", "deployment"})
public class PasswordResetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetController.class);

    private GenericService genericService;
    private UserService userService;
    private MailSender mailSender;
    private EmailConstructor emailConstructor;

    @Value("{$client.passwordResetUrl}")
    private String clientPasswordResetUrl;


    @Autowired
    public PasswordResetController(
            GenericService genericService,
            UserService userService,
            MailSender mailSender,
            EmailConstructor emailConstructor) {
        this.genericService = genericService;
        this.userService = userService;
        this.mailSender = mailSender;
        this.emailConstructor = emailConstructor;
    }

    /**
     * Handles requests to reset password. Sends an email to user with a password reset URL.
     *
     * @param usernameDTO UsernameDTO
     * @throws EntityNotFoundException when the username is not found.
     * @throws MailServiceNoConnectionException when email service is not available.
     */
    @PostMapping(value = "request-password-reset")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus resetPassword(@RequestBody @Valid UsernameDTO usernameDTO)
            throws EntityNotFoundException, MailServiceNoConnectionException {

        User user;
        String token;

        try {
            user = genericService.getUserByEmail(usernameDTO.getEmail());
            userService.createPasswordResetToken(user);
            token = userService.getPasswordTokenByUser(user);
        } catch (EntityNotFoundException e) {
            LOGGER.info(usernameDTO.getEmail() + " email provided for password reset does not exist.");
            return HttpStatus.OK;
        }

        try {
            mailSender.send(emailConstructor.constructPasswordResetEmail(token, user));
        } catch (MailSendException e) {
            LOGGER.error("Failed email to: " + user.getEmail() + ". " + e.getFailedMessages());
            throw new MailServiceNoConnectionException();
        }

        return HttpStatus.OK;
    }

    /**
     * Handles password changes that are requested via password reset token.
     *
     * @param passwordResetDTO PasswordResetDTO that contains the user id, token and new password.
     * @throws EntityNotFoundException when user is not found.
     */
    @PostMapping(value = "change-password")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus changePassword(@RequestBody @Valid PasswordResetDTO passwordResetDTO)
            throws EntityNotFoundException {

        User user;
        String validation;

        try {
            user = genericService.getUserById(passwordResetDTO.getId());
            validation = userService.validatePasswordResetToken(user.getId(), passwordResetDTO.getToken());
        } catch (EntityNotFoundException e) {
            LOGGER.info(passwordResetDTO.getId() + " id provided for password change does not exist.");
            return HttpStatus.OK;
        }


        if (validation != null) {
            throw new BadCredentialsException(validation);
        }
        userService.updatePassword(user, passwordResetDTO.getPassword());

        return HttpStatus.OK;
    }
}
