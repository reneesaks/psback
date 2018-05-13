package com.professionalstrangers.event.listener;

import com.professionalstrangers.event.UsernameLockedEvent;
import com.professionalstrangers.service.impl.AuthenticationAttemptServiceImpl;
import com.professionalstrangers.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Listens on any authentication events that results in a failure. Such events are sent by Spring Security and
 * need to be registered in configuration.
 *
 * @see com.professionalstrangers.config.SecurityConfig
 */
@Component
public class AuthenticationFailureEventListener {

    private AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl;
    private HttpServletRequest httpServletRequest;

    @Autowired
    AuthenticationFailureEventListener(
            AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl,
            HttpServletRequest httpServletRequest) {
        this.authenticationAttemptServiceImpl = authenticationAttemptServiceImpl;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * Sends username and IP address to authentication failed method in authentication attempts service on
     * BadCredentialsEvent created by Spring Security.
     *
     * @param authenticationFailureBadCredentialsEvent event
     */
    @EventListener
    public void handleBadCredentialsEvent(
            AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        String username = authenticationFailureBadCredentialsEvent.getAuthentication().getName();
        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);
        authenticationAttemptServiceImpl.authenticationFailed(username, remoteAddress);

    }

    /**
     * Sends username and IP address to authentication failed method in authentication attempts service on
     * UsernameLockedEvent when BadCredentialsEvent stop firing.
     *
     * @param usernameLockedEvent event
     */
    @EventListener
    public void handleUsernameLockedEvent(UsernameLockedEvent usernameLockedEvent) {
        String username = usernameLockedEvent.getUsername();
        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);
        authenticationAttemptServiceImpl.authenticationFailed(username, remoteAddress);
    }
}
