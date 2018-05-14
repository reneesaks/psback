package com.professionalstrangers.util;

import com.professionalstrangers.domain.User;
import org.springframework.mail.SimpleMailMessage;

/**
 * Helper class for constructing emails.
 */
public class EmailConstructor {

    /**
     * Constructs the email.
     *
     * @param subject Email subject.
     * @param body Email body.
     * @param user User who will receive the email.
     */
    private static SimpleMailMessage constructEmail(String subject, String body, User user) {
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
    public static SimpleMailMessage constructConfirmationEmail(
            String serverAddress,
            String token,
            User user) {
        String confirmationUrl = serverAddress
                + "/registration-confirm?token="
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
    public static SimpleMailMessage constructResendConfirmationEmail(
            String serverAddress,
            String newToken,
            User user) {
        String confirmationUrl = serverAddress
                + "/registration-confirm?token="
                + newToken;
        String message = "Here is your new activation URL: ";
        return constructEmail("Resend Registration Confirmation", message + confirmationUrl, user);
    }

    /**
     * @param token Token that will be put in the request parameter.
     * @param user User who will receive the email.
     * @return SimpleMailMessage.
     */
    public static SimpleMailMessage constructPasswordResetEmail(
            String serverAddress,
            String token,
            User user) {
        String resetUrl = serverAddress + "/password-reset?id=" + user.getId() + "&token=" + token;
        String message = "You have requested a password reset. If this was not you, please ignore "
                + "this message. You can reset your password with the following link: ";
        return constructEmail("Password Reset", message + resetUrl, user);
    }
}
