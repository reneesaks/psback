package com.perfectstrangers.service;

public interface AuthenticationAttemptService {

    int USERNAME_MAX_ATTEMPT = 5;
    int REMOTE_ADDRESS_MAX_ATTEMPT = 50;
    int USERNAME_BLOCK = 5;
    int REMOTE_ADDRESS_BLOCK = 1440;

    void authenticationSucceeded(String username);

    void authenticationFailed(String username, String remoteAddress);

    boolean isRemoteAddressBlocked(String remoteAddress);

    boolean isUsernameBlocked(String username);

    int getUSERNAME_MAX_ATTEMPT();

    int getREMOTE_ADDRESS_MAX_ATTEMPT();

    int getUSERNAME_BLOCK();

    int getREMOTE_ADDRESS_BLOCK();
}
