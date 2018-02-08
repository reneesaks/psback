package com.perfectstrangers.service;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;

public interface UserService {

    boolean emailExist(String email);

    User registerNewUserAccount(User user);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken createNewVerificationToken(String existingVerificationToken);

    User getUserByEmail(String email);

    User getUserByVerificationToken(String verificationToken);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken getVerificationTokenByUser(User user);

}
