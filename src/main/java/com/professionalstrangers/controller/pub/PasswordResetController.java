package com.professionalstrangers.controller.pub;

import com.professionalstrangers.domain.User;
import com.professionalstrangers.dto.PasswordResetDTO;
import com.professionalstrangers.dto.UsernameDTO;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.error.MailServiceNoConnectionException;
import com.professionalstrangers.service.GenericService;
import com.professionalstrangers.service.UserService;
import com.professionalstrangers.util.EmailConstructor;
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
@Profile({"staging", "production"})
public class PasswordResetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetController.class);

    private GenericService genericService;
    private UserService userService;
    private MailSender mailSender;

    @Value("${client.serverAddress}")
    String serverAddress;

    @Autowired
    public PasswordResetController(
            GenericService genericService,
            UserService userService,
            MailSender mailSender) {
        this.genericService = genericService;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    /**
     * Handles requests to reset password. Sends an email to user with a password reset URL.
     *
     * @param usernameDTO UsernameDTO
     * @throws MailServiceNoConnectionException when email service is not available.
     */
    @PostMapping(value = "request-password-reset")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus resetPassword(@RequestBody @Valid UsernameDTO usernameDTO)
            throws MailServiceNoConnectionException {

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
            mailSender.send(EmailConstructor.constructPasswordResetEmail(this.serverAddress, token, user));
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
