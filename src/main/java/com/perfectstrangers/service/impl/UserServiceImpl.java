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

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired RoleRepository roleRepository;

    @Override
    public User registerNewUserAccount(User userDTO) {

        if (emailExist(userDTO.getEmail())) {
            throw new CustomRuntimeException("There is an account with that email address: " + userDTO.getEmail());
        }

        User user = new User();
        List<Role> role = roleRepository.findByRoleName("STANDARD_USER");
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(role);

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return true;
        }

        return false;
    }

    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}