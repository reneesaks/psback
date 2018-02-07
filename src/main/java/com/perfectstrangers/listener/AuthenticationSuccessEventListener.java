package com.perfectstrangers.listener;

import com.perfectstrangers.service.impl.LoginAttemptServiceImpl;
import com.perfectstrangers.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private LoginAttemptServiceImpl loginAttemptServiceImpl;

    private HttpServletRequest httpServletRequest;

    @Autowired AuthenticationSuccessEventListener(LoginAttemptServiceImpl loginAttemptServiceImpl, HttpServletRequest httpServletRequest) {
        this.loginAttemptServiceImpl = loginAttemptServiceImpl;
        this.httpServletRequest = httpServletRequest;
    }

    public void onApplicationEvent(AuthenticationSuccessEvent e) {

        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);

        loginAttemptServiceImpl.loginSucceeded(remoteAddress);

    }

}
