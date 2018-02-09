package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Role;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.repository.UserRepository;
import com.perfectstrangers.util.HttpServletRequestUtil;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

/**
 * User authorization.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private HttpServletRequest httpServletRequest;
    private AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl;

    @Autowired
    public UserDetailsServiceImpl(
            UserRepository userRepository,
            HttpServletRequest httpServletRequest,
            AuthenticationAttemptServiceImpl authenticationAttemptServiceImpl) {
        this.httpServletRequest = httpServletRequest;
        this.authenticationAttemptServiceImpl = authenticationAttemptServiceImpl;
        this.userRepository = userRepository;
    }

    @Override
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

        // Overriding methods cannot throw exceptions broader than overridden method
        // Hence the repository use instead of service (service throws EntityNotFoundException)
        User user = userRepository.findByEmail(username);

        if (user == null) {
            // Give the same message as if the password was wrong
            // This will not give away which one was wrong
            throw new InvalidGrantException("Bad credentials");
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
