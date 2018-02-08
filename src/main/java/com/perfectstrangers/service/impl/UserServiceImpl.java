package com.perfectstrangers.service.impl;

import com.perfectstrangers.domain.Role;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.error.CustomRuntimeException;
import com.perfectstrangers.repository.RoleRepository;
import com.perfectstrangers.repository.UserRepository;
import com.perfectstrangers.repository.VerificationTokenRepository;
import com.perfectstrangers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            VerificationTokenRepository verificationTokenRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public User registerNewUserAccount(User userDTO) {
        if (emailExist(userDTO.getEmail())) {
            throw new CustomRuntimeException(
                    "There is an account with that email address: " + userDTO.getEmail());
        }
        User user = new User();
        List<Role> role = roleRepository.findByRoleName("STANDARD_USER");
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(role);
        return userRepository.save(user);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken createNewVerificationToken(String existingVerificationToken) {
        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(existingVerificationToken);
        verificationToken.updateToken(UUID.randomUUID().toString());
        verificationToken = verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    @Override
    public VerificationToken getVerificationTokenByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }
}
