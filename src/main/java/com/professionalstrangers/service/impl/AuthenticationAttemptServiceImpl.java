package com.professionalstrangers.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.professionalstrangers.event.UsernameLockedEvent;
import com.professionalstrangers.service.AuthenticationAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Registers authentication events cached by event listeners. Blocks username and IP address if they exceed
 * the maximum failure count. Username attempts are also cached here because an attacker could reset the
 * remote address counter by logging in to a valid account.
 *
 * @see com.professionalstrangers.event.listener.AuthenticationFailureEventListener
 * @see com.professionalstrangers.event.listener.AuthenticationSuccessEventListener
 */
@Service
public class AuthenticationAttemptServiceImpl implements AuthenticationAttemptService {

    private LoadingCache<String, Integer> remoteAddressAttemptsCache;
    private LoadingCache<String, Integer> usernameAttemptsCache;
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Builds the caches for storing failed authentication attempts.
     *
     * @param applicationEventPublisher ApplicationEventPublisher
     */
    @Autowired
    public AuthenticationAttemptServiceImpl(ApplicationEventPublisher applicationEventPublisher) {
        super();
        this.remoteAddressAttemptsCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(REMOTE_ADDRESS_BLOCK, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
        this.usernameAttemptsCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(USERNAME_BLOCK, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Resets the username attempts cache.
     *
     * @param username username(email)
     */
    @Override
    public void authenticationSucceeded(String username) {
        usernameAttemptsCache.invalidate(username);
    }

    /**
     * Counts authentication attempts per username and per IP.
     *
     * @param username username(email)
     * @param remoteAddress IP address
     */
    @Override
    public void authenticationFailed(String username, String remoteAddress) {
        int usernameAttempts, remoteAddressAttempts;
        try {
            usernameAttempts = usernameAttemptsCache.get(username);
        } catch (ExecutionException e) {
            usernameAttempts = 0;
        }
        try {
            remoteAddressAttempts = remoteAddressAttemptsCache.get(remoteAddress);
        } catch (ExecutionException e) {
            remoteAddressAttempts = 0;
        }

        usernameAttemptsCache.put(username, ++usernameAttempts);
        remoteAddressAttemptsCache.put(remoteAddress, ++remoteAddressAttempts);
    }

    /**
     * Checks if the IP address is blocked.
     *
     * @param remoteAddress IP address
     * @return true if IP address is blocked.
     */
    @Override
    public boolean isRemoteAddressBlocked(String remoteAddress) {
        try {
            return remoteAddressAttemptsCache.get(remoteAddress) >= REMOTE_ADDRESS_MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

    /**
     * Checks if the username is blocked.BadCredentials event stops firing after username attempt limit has
     * been reached so the UsernameLockedEvent will start firing after that.
     *
     * @param username username(email)
     * @return true if username is blocked.
     */
    @Override
    public boolean isUsernameBlocked(String username) {
        try {
            boolean isUsernameBlocked = usernameAttemptsCache.get(username) >= USERNAME_MAX_ATTEMPT;
            if (isUsernameBlocked) {
                applicationEventPublisher.publishEvent(new UsernameLockedEvent(this, username));
            }
            return isUsernameBlocked;
        } catch (ExecutionException e) {
            return false;
        }
    }

    @Override
    public int getUSERNAME_MAX_ATTEMPT() {
        return USERNAME_MAX_ATTEMPT;
    }

    @Override
    public int getREMOTE_ADDRESS_MAX_ATTEMPT() {
        return REMOTE_ADDRESS_MAX_ATTEMPT;
    }

    @Override
    public int getUSERNAME_BLOCK() {
        return USERNAME_BLOCK;
    }

    @Override
    public int getREMOTE_ADDRESS_BLOCK() {
        return REMOTE_ADDRESS_BLOCK;
    }
}
