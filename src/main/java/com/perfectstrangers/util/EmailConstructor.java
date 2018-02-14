package com.perfectstrangers.util;

import com.perfectstrangers.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Helper class for constructing emails.
 */
@Component
public class EmailConstructor {

    @Value("${host.serverAddress}")
    String hostServerAddress;

    @Value("${client.passwordResetUrl}")
    String clientPasswordResetUrl;

    public EmailConstructor() {
    }

    /**
     * Constructs the email.
     *
     * @param subject Email subject.
     * @param body Email body.
     * @param user User who will receive the email.
     */
    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    /**
     * Constructs the confirmation email.
     *
     * @param token Token that will be put in the request parameter.
     * @param user User who will receive the email.
     * @return SimpleMailMessage.
     */
    public SimpleMailMessage constructConfirmationEmail(String token, User user) {
        String confirmationUrl = this.hostServerAddress
                + "/api/public/register/registration-confirm.html?token="
                + token;
        String message = "Here is your activation URL: ";
        return constructEmail("Registration Confirmation", message + confirmationUrl, user);
    }

    /**
     * Constructs the resent confirmation email.
     *
     * @param newToken Newly generated token that will be put in the request parameter.
     * @param user User who will receive the email.
     * @return SimpleMailMessage.
     */
    public SimpleMailMessage constructResendConfirmationEmail(String newToken, User user) {
        String confirmationUrl = this.hostServerAddress
                + "/api/public/register/registration-confirm.html?token="
                + newToken;
        String message = "Here is your new activation URL: ";
        return constructEmail("Resend Registration Confirmation", message + confirmationUrl, user);
    }

    /**
     *
     * @param token Token that will be put in the request parameter.
     * @param user User who will receive the email.
     * @return SimpleMailMessage.
     */
    public SimpleMailMessage constructPasswordResetEmail(String token, User user) {
        String resetUrl = this.clientPasswordResetUrl + "?id=" + user.getId() + "&token=" + token;
        String message = "You have requested a password reset. If this was not you, please ignore "
                + "this message. You can reset your password with the following link: ";
        return constructEmail("Password Reset", message + resetUrl, user);
    }
}
