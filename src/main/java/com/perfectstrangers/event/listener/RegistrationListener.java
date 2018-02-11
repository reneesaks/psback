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

import java.util.UUID;

@Component
@Profile({"production", "deployment"})
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private RegistrationService service;
    private JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(RegistrationService service, JavaMailSender mailSender) {
        this.service = service;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        SimpleMailMessage email = new EmailConstructor()
                .constructConfirmationEmail(event.getAppUrl(), token, user);
        mailSender.send(email);
    }
}
