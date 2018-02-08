package com.perfectstrangers.listener;

import com.perfectstrangers.service.impl.AuthenticationAttemptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccessEventListener {

    private AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl;

    private HttpServletRequest httpServletRequest;

    @Autowired AuthenticationSuccessEventListener(AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl, HttpServletRequest httpServletRequest) {
        this.authenticationAttemptServiceImpl = authenticationAttemptServiceImpl;
        this.httpServletRequest = httpServletRequest;
    }

    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {

        String username = authenticationSuccessEvent.getAuthentication().getName();

        System.out.println(username);

        authenticationAttemptServiceImpl.authenticationSucceeded(username);

    }

}
