package com.professionalstrangers.service;

import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.VerificationToken;
import com.professionalstrangers.error.UsernameExistsException;

public interface RegistrationService {

    boolean emailExists(String email);

    User registerNewUserAccount(User user) throws UsernameExistsException;

    void saveRegisteredUser(User user);

    void createVerificationToken(User user);

    VerificationToken createNewVerificationToken(User user);

    String validateVerificationToken(String token);

    User getUserByVerificationToken(String token);

    VerificationToken getVerificationToken(String token);

    VerificationToken getVerificationTokenByUser(User user);
}
