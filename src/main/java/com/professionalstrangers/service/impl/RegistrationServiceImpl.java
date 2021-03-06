package com.professionalstrangers.service.impl;

import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.VerificationToken;
import com.professionalstrangers.error.UsernameExistsException;
import com.professionalstrangers.repository.RoleRepository;
import com.professionalstrangers.repository.UserRepository;
import com.professionalstrangers.repository.VerificationTokenRepository;
import com.professionalstrangers.service.RegistrationService;
import java.util.Calendar;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(
            UserRepository userRepository,
            VerificationTokenRepository verificationTokenRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
     * @param user user object.
     * @return user object
     * @throws UsernameExistsException when the username already exists.
     */
    @Override
    public User registerNewUserAccount(User user) throws UsernameExistsException {
        if (emailExists(user.getEmail())) {
            throw new UsernameExistsException();
        }

        // Hash password here because .save will trigger also setPassword and double hash will occur
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByRoleName("STANDARD_USER"));
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
