package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Role;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.service.UserService;
import com.perfectstrangers.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
    private AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl;

    @Autowired
    public UserDetailsServiceImpl(
            UserService userService,
            HttpServletRequest httpServletRequest,
            AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
        this.authenticationAttemptServiceImpl = authenticationAttemptServiceImpl;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String remoteAddress = new HttpServletRequestUtil().getRemoteAddress(httpServletRequest);
        if (authenticationAttemptServiceImpl.isRemoteAddressBlocked(remoteAddress)) {
            throw new InvalidGrantException("This IP address has been blocked for " +
                    authenticationAttemptServiceImpl.getREMOTE_ADDRESS_BLOCK()
                    + " minutes because it exceeded the maximum allowed attempts.");
        }

        if (authenticationAttemptServiceImpl.isUsernameBlocked(username)) {
            throw new InvalidGrantException("This username has been blocked for " +
                    authenticationAttemptServiceImpl.getUSERNAME_BLOCK()
                    + " minutes because it exceeded the maximum allowed attempts.");
        }

        User user = userService.getUserByEmail(username);
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
                user.getRoles().stream().map(Role::getRoleName).map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}
