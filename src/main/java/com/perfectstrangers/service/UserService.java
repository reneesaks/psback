package com.perfectstrangers.service;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.error.UsernameExistsException;

public interface UserService {

    boolean emailExists(String email);

    User registerNewUserAccount(User user) throws UsernameExistsException;

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken createNewVerificationToken(String existingVerificationToken);

    User getUserByEmail(String email) throws EntityNotFoundException;

    User getUserByVerificationToken(String verificationToken);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken getVerificationTokenByUser(User user);
}
