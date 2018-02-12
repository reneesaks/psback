package com.perfectstrangers.event.listener;

import com.perfectstrangers.service.impl.AuthenticationAttemptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Listens on any authentication events that results in a success. Such events are sent by Spring Security and
 * need to be registered in configuration.
 *
 * @see com.perfectstrangers.config.SecurityConfig
 */
@Component
public class AuthenticationSuccessEventListener {

    private AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl;

    @Autowired
    AuthenticationSuccessEventListener(
            AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl) {
        this.authenticationAttemptServiceImpl = authenticationAttemptServiceImpl;
    }

    /**
     * Sends username to authentication succeeded method in authentication attempt service.
     *
     * @param authenticationSuccessEvent event
     */
    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        String username = authenticationSuccessEvent.getAuthentication().getName();
        authenticationAttemptServiceImpl.authenticationSucceeded(username);
    }
}
