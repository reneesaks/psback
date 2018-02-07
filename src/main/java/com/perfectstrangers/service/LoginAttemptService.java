package com.perfectstrangers.service;

public interface LoginAttemptService {

    int MAX_ATTEMPT = 20;

    void loginSucceeded(String key);

    void loginFailed(String key);

    boolean isBlocked(String key);

    int getMAX_ATTEMPT();

}
