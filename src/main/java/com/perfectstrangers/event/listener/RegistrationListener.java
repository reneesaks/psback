package com.perfectstrangers.event.listener;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.event.OnRegistrationCompleteEvent;
import com.perfectstrangers.service.RegistrationService;
import com.perfectstrangers.util.EmailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @see com.perfectstrangers.event.OnRegistrationCompleteEvent
 */
@Component
@Profile({"production", "deployment"})
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private RegistrationService registrationService;
    private JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(RegistrationService registrationService, JavaMailSender mailSender) {
        this.registrationService = registrationService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    /**
     * Sends the user the confirmation link.
     *
     * @param event OnRegistrationCompleteEvent holds the info necessary to create the activation link.
     */
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        registrationService.createVerificationToken(user);
        String token = registrationService.getVerificationTokenByUser(user).getToken();
        SimpleMailMessage email = new EmailConstructor()
                .constructConfirmationEmail(event.getAppUrl(), token, user);
        mailSender.send(email);
    }
}
