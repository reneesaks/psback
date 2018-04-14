package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Role;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.error.UsernameExistsException;
import com.perfectstrangers.repository.RoleRepository;
import com.perfectstrangers.repository.UserRepository;
import com.perfectstrangers.repository.VerificationTokenRepository;
import com.perfectstrangers.service.RegistrationService;
import com.perfectstrangers.util.PasswordHasher;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles registration.
 */
@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private RoleRepository roleRepository;

    @Autowired
    public RegistrationServiceImpl(
            UserRepository userRepository,
            VerificationTokenRepository verificationTokenRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Checks if the email exists
     *
     * @param email email(username)
     * @return true if email exists.
     */
    @Override
    public boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    /**
     * Saves the newly created user with activated field set to false.
     *
     * @param userDTO User object for validation.
     * @return User object
     * @throws UsernameExistsException when the username already exists.
     */
    @Override
    public User registerNewUserAccount(User userDTO) throws UsernameExistsException {
        if (emailExists(userDTO.getEmail())) {
            throw new UsernameExistsException();
        }
        User user = new User();
        List<Role> role = roleRepository.findByRoleName("STANDARD_USER");
        user.setEmail(userDTO.getEmail());
        // Hash password here because .save will trigger also setPassword and double hash will occur
        String password = new PasswordHasher().hashPasswordWithSha256(userDTO.getPassword());
        user.setPassword(password);
        user.setRoles(role);
        return userRepository.save(user);
    }

    /**
     * Saves the activated user
     *
     * @param user User object where the activated field has been set to true.
     */
    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    /**
     * Creates new token for newly created User.
     *
     * @param user User object
     */
    @Override
    public void createVerificationToken(User user) {
        VerificationToken myToken = new VerificationToken(UUID.randomUUID().toString(), user);
        verificationTokenRepository.save(myToken);
    }

    /**
     * Updates token with a new token for an existing user.
     *
     * @param user User object
     * @return newly created verification token
     */
    @Override
    public VerificationToken createNewVerificationToken(User user) {
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
        verificationToken.updateToken(UUID.randomUUID().toString());
        verificationToken = verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = this.getVerificationToken(token);

        if (verificationToken == null) {
            return "Invalid token!";
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Verification link has expired!";
        }
        return null;
    }

    @Override
    public User getUserByVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token).getUser();
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken getVerificationTokenByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }
}
