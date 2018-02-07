package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Role;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.error.CustomRuntimeException;
import com.perfectstrangers.service.UserService;
import com.perfectstrangers.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * User authorization.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    private HttpServletRequest httpServletRequest;

    private LoginAttemptServiceImpl loginAttemptServiceImpl;

    @Autowired
    public UserDetailsServiceImpl(UserService userService,
                                  HttpServletRequest httpServletRequest,
                                  LoginAttemptServiceImpl loginAttemptServiceImpl) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
        this.loginAttemptServiceImpl = loginAttemptServiceImpl;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);

        if (loginAttemptServiceImpl.isBlocked(remoteAddress)) {
            throw new CustomRuntimeException("This IP has been blocked for 24 hours because it exceeded the maximum allowed wrong authentication attempts.");
        }

        User user = userService.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.isActivated(),
                true,
                true,
                true,
                user.getRoles().stream().map(Role::getRoleName).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

    }

    private String getClientIP() {
        String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return httpServletRequest.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
