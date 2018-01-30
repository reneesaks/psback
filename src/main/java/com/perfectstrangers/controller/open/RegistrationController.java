package com.perfectstrangers.controller.open;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.dto.UserDTO;
import com.perfectstrangers.error.CustomRuntimeException;
import com.perfectstrangers.registration.OnRegistrationCompleteEvent;
import com.perfectstrangers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@RestController
@RequestMapping("/api/public/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @RequestMapping(value = "/new-user", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class) // If any exception is thrown, roll back db changes
    public ResponseEntity<HashMap<String, String>> registerNewUser(@Valid UserDTO userDTO, WebRequest request) {

        // Create new user and hash password with SHA256
        User user = new User();
        String sha256 = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userDTO.getPassword());
        user.setPassword(sha256);
        user.setEmail(userDTO.getEmail());
        User registered = userService.registerNewUserAccount(user);

        try {
            // Trigger the event listener
            String appUrl = request.getContextPath(); // Get home URL
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
            LOGGER.info("User with email " + userDTO.getEmail() + " is registered. Waiting for activation.");
        } catch (Exception e) {
            LOGGER.error("Error creating user with email: " + userDTO.getEmail() + ". ", e);
            throw new CustomRuntimeException("There was an unexpected error with email service.");
        }

        // Return JSON response
        HashMap<String, String> map = new HashMap<>();
        map.put("email", userDTO.getEmail());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(map);
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token, HttpServletResponse httpServletResponse) {

        VerificationToken verificationToken = userService.getVerificationToken(token);

        if (verificationToken == null) {
            return "Invalid token!";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Verification link has expired!";
        }

        user.setActivated(true);
        userService.saveRegisteredUser(user);

        try {
            httpServletResponse.sendRedirect("https://www.google.com");
        } catch (IOException e) {
            LOGGER.error("Problem redirecting to login URL. " + e);
        }

        return "Registration successful!";

    }
}