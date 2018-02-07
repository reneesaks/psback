package com.perfectstrangers.listener;

import com.perfectstrangers.service.impl.LoginAttemptServiceImpl;
import com.perfectstrangers.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private LoginAttemptServiceImpl loginAttemptServiceImpl;

    private HttpServletRequest httpServletRequest;

    @Autowired AuthenticationFailureEventListener(LoginAttemptServiceImpl loginAttemptServiceImpl, HttpServletRequest httpServletRequest) {
        this.loginAttemptServiceImpl = loginAttemptServiceImpl;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent e) {

        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);

        loginAttemptServiceImpl.loginFailed(remoteAddress);

    }


}
