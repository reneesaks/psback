package com.perfectstrangers.service;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;

public interface UserService {

    User registerNewUserAccount(User user);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    User findByEmail(String email);

}
