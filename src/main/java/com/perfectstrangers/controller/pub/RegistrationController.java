package com.perfectstrangers.controller.pub;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.VerificationToken;
import com.perfectstrangers.dto.UserDTO;
import com.perfectstrangers.error.CustomRuntimeException;
import com.perfectstrangers.event.OnRegistrationCompleteEvent;
import com.perfectstrangers.service.UserService;
import com.perfectstrangers.util.EmailConstructor;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/public/register")
@Profile({"production", "deployment"})
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private UserService userService;
    private ApplicationEventPublisher eventPublisher;
    private MailSender mailSender;

    @Value("${serverAddress}")
    String serverAddress;

    @Autowired
    public RegistrationController(
            UserService userService,
            ApplicationEventPublisher eventPublisher,
            MailSender mailSender) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.mailSender = mailSender;
    }

    @RequestMapping(
            value = "/new-user",
            params = {"email", "password"},
            produces = "application/json",
            method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class) // If any exception is thrown, roll back db changes
    public ResponseEntity<HashMap<String, String>> registerNewUser(
            @Valid UserDTO userDTO,
            WebRequest request) {

        // Create new user and hash password with SHA256
        User user = new User();
        String sha256 = sha256Hex(userDTO.getPassword());
        user.setPassword(sha256);
        user.setEmail(userDTO.getEmail());
        User registered = userService.registerNewUserAccount(user);

        try {
            // Trigger the event listener
            String appUrl = serverAddress;
            eventPublisher
                    .publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
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

    @RequestMapping(value = "/registration-confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token,
            HttpServletResponse httpServletResponse) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "Invalid token!";
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Verification link has expired!";
        }

        User user = verificationToken.getUser();
        user.setActivated(true);
        userService.saveRegisteredUser(user);

        try {
            httpServletResponse.sendRedirect("https://www.google.com");
        } catch (IOException e) {
            LOGGER.error("Problem redirecting to login URL. " + e);
        }
        return "Registration successful!";
    }

    @RequestMapping(value = "/resend-registration-token", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<HashMap<String, String>> resendRegistrationToken(
            @RequestParam("username") String username) throws UsernameNotFoundException {

        User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }

        HashMap<String, String> map = new HashMap<>();
        if (!user.isActivated()) {
            VerificationToken oldToken = userService.getVerificationTokenByUser(user);
            VerificationToken newToken = userService.createNewVerificationToken(oldToken.getToken());
            SimpleMailMessage email = new EmailConstructor()
                    .constructResendConfirmationEmail(serverAddress, newToken.getToken(), user);
            mailSender.send(email);
            map.put("email", username);
        } else {
            map.put("error", "This username is already activated");
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(map);
    }
}
