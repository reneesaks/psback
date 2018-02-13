package com.perfectstrangers.service;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.error.EntityNotFoundException;

public interface UserService {

    void createPasswordResetToken(User user);

    void updatePassword(User user, String password) throws EntityNotFoundException;

    String getPasswordTokenByUser(User user);

    String validatePasswordResetToken(Long id, String token);
}
