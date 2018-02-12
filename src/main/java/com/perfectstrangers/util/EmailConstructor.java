package com.perfectstrangers.util;

import com.perfectstrangers.domain.User;
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
     * @param serverAddress Server address that will hold the endpoint.
     * @param token Token that will be put in the request parameter.
     * @param user User who will receive the email.
     */
    public SimpleMailMessage constructConfirmationEmail(String serverAddress, String token, User user) {
        String confirmationUrl =
                serverAddress + "api/public/register/registration-confirm.html?token=" + token;
        String message = "Here is your activation URL: ";
        return constructEmail("Registration Confirmation", message + confirmationUrl, user);
    }

    /**
     * Constructs the resent confirmation email.
     *
     * @param serverAddress Server address that will hold the endpoint.
     * @param newToken Newly generated token that will be put in the request parameter.
     * @param user User who will receive the email.
     */
    public SimpleMailMessage constructResendConfirmationEmail(String serverAddress,
            String newToken, User user) {
        String confirmationUrl =
                serverAddress + "api/public/register/registration-confirm.html?token=" + newToken;
        String message = "Here is your new activation URL: ";
        return constructEmail("Resend Registration Confirmation", message + confirmationUrl, user);
    }
}
