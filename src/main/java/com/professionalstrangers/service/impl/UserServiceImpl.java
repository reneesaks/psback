package com.professionalstrangers.service.impl;

import com.professionalstrangers.domain.PasswordResetToken;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.repository.PasswordResetTokenRepository;
import com.professionalstrangers.service.GenericService;
import com.professionalstrangers.service.UserService;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles all services related to users. For example: updating personal information, resetting password,
 * changing password etc.
 */
@Service
public class UserServiceImpl implements UserService {

    private PasswordResetTokenRepository passwordResetTokenRepository;
    private GenericService genericService;

    @Autowired
    public UserServiceImpl(
            PasswordResetTokenRepository passwordResetTokenRepository,
            GenericService genericService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.genericService = genericService;
    }

    /**
     * Creates password reset token for user.
     *
     * @param user user
     */
    public void createPasswordResetToken(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(UUID.randomUUID().toString(), user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    /**
     * Updates user password. If it was updated with a password reset token, then it deletes the token also.
     *
     * @param user user
     * @param password password
     * @throws EntityNotFoundException when user is not found
     */
    public void updatePassword(User user, String password) throws EntityNotFoundException {
        user.setPassword(password);
        genericService.updateUser(user);

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);
        if (passwordResetToken != null) {
            passwordResetTokenRepository.delete(passwordResetToken);
        }
    }

    public String getPasswordTokenByUser(User user) {
        return passwordResetTokenRepository.findByUser(user).getToken();
    }

    /**
     * Creates a string message if the validation process went wrong.
     *
     * @param id user id
     * @param token password reset token
     * @return null
     */
    public String validatePasswordResetToken(Long id, String token) {

        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if ((passToken == null) || (!Objects.equals(passToken.getUser().getId(), id))) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }
        return null;
    }
}
