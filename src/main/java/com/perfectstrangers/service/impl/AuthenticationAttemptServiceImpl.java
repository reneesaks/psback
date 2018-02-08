package com.perfectstrangers.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.perfectstrangers.event.UsernameLockedEvent;
import com.perfectstrangers.service.AuthenticationAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Username attempts are also cached here because an attacker could reset the remote address
 * counter by logging in to a valid account.
 */
@Service
public class AuthenticationAttemptServiceImpl implements AuthenticationAttemptService {

    private LoadingCache<String, Integer> remoteAddressAttemptsCache;

    private LoadingCache<String, Integer> usernameAttemptsCache;

    private ApplicationEventPublisher applicationEventPublisher;

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

    @Override
    public void authenticationSucceeded(String username) {
        usernameAttemptsCache.invalidate(username);
    }

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

        usernameAttempts++;
        remoteAddressAttempts++;

        System.out.println("usernameAttempts: " + usernameAttempts);
        System.out.println("remoteAddressAttempts: " + remoteAddressAttempts);

        usernameAttemptsCache.put(username, usernameAttempts);
        remoteAddressAttemptsCache.put(remoteAddress, remoteAddressAttempts);

    }

    @Override
    public boolean isRemoteAddressBlocked(String remoteAddress) {

        try {
            return remoteAddressAttemptsCache.get(remoteAddress) >= REMOTE_ADDRESS_MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }

    }

    @Override
    public boolean isUsernameBlocked(String username) {

        try {

            boolean isUsernameBlocked = usernameAttemptsCache.get(username) >= USERNAME_MAX_ATTEMPT;

            // BadCredentials event stops firing after username attempt limit has been reached
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
