package com.perfectstrangers.service;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.error.UsernameExistsException;

public interface RegistrationService {

    boolean emailExists(String email);

    User registerNewUserAccount(User user) throws UsernameExistsException;

    void saveRegisteredUser(User user);

    void createVerificationToken(User user);

    VerificationToken createNewVerificationToken(User user);

    User getUserByVerificationToken(String verificationToken);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken getVerificationTokenByUser(User user);
}
