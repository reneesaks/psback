package com.perfectstrangers.event.listener;

import com.perfectstrangers.service.impl.AuthenticationAttemptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener {

    private AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl;

    @Autowired
    AuthenticationSuccessEventListener(
            AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl) {
        this.authenticationAttemptServiceImpl = authenticationAttemptServiceImpl;
    }

    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        String username = authenticationSuccessEvent.getAuthentication().getName();
        authenticationAttemptServiceImpl.authenticationSucceeded(username);
    }
}
