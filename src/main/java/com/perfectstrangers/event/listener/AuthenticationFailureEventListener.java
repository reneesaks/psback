package com.perfectstrangers.event.listener;

import com.perfectstrangers.event.UsernameLockedEvent;
import com.perfectstrangers.service.impl.AuthenticationAttemptServiceImpl;
import com.perfectstrangers.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureEventListener {

    private AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl;

    private HttpServletRequest httpServletRequest;

    @Autowired AuthenticationFailureEventListener(AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl,
                                                  HttpServletRequest httpServletRequest) {
        this.authenticationAttemptServiceImpl = authenticationAttemptServiceImpl;
        this.httpServletRequest = httpServletRequest;
    }

    @EventListener
    public void handleBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {

        String username = authenticationFailureBadCredentialsEvent.getAuthentication().getName();
        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);

        authenticationAttemptServiceImpl.authenticationFailed(username, remoteAddress);

    }

    @EventListener
    public void handleUsernameLockedEvent(UsernameLockedEvent usernameLockedEvent) {

        String username = usernameLockedEvent.getUsername();
        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);

        authenticationAttemptServiceImpl.authenticationFailed(username, remoteAddress);

    }

}
