package com.perfectstrangers.util;

import com.perfectstrangers.domain.User;
import org.springframework.mail.SimpleMailMessage;

public class EmailConstructor {

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    public SimpleMailMessage constructConfirmationEmail(String serverAddress, String token, User user) {
        String confirmationUrl =
                serverAddress + "api/public/register/registration-confirm.html?token=" + token;
        String message = "Here is your activation URL: ";
        return constructEmail("Registration Confirmation", message + confirmationUrl, user);
    }

    public SimpleMailMessage constructResendConfirmationEmail(String serverAddress,
            String newToken, User user) {
        String confirmationUrl =
                serverAddress + "api/public/register/registration-confirm.html?token=" + newToken;
        String message = "Here is your new activation URL: ";
        return constructEmail("Resend Registration Confirmation", message + confirmationUrl, user);
    }
}
