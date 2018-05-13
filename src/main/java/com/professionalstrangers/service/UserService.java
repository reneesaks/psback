package com.professionalstrangers.service;

import com.professionalstrangers.domain.User;
import com.professionalstrangers.error.EntityNotFoundException;

public interface UserService {

    void createPasswordResetToken(User user);

    void updatePassword(User user, String password) throws EntityNotFoundException;

    String getPasswordTokenByUser(User user);

    String validatePasswordResetToken(Long id, String token);
}
