package com.professionalstrangers.util;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfessionalStrangersApplication.class)
@ActiveProfiles("dev")
@DataJpaTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class EmailConstructorTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setupTest() {
        User user = new User();
        user.setActivated(true);
        user.setEmail("user@user.com");
        user.setPassword("password");
        user.setAlias("user");

        userRepository.save(user);
    }

    @Test
    public void whenConstructConfirmationEmail_thenReturnConfirmationEmail() {

        // When
        User user = userRepository.findByEmail("user@user.com");
        SimpleMailMessage message = EmailConstructor.constructConfirmationEmail(
                "server", "12345", user);

        // Then
        Assert.assertEquals("Registration Confirmation", message.getSubject());
        Assert.assertEquals(
                "Here is your activation URL: server/registration-confirm?token=12345",
                message.getText()
        );
        Assert.assertEquals(message.getTo()[0], user.getEmail());
    }

    @Test
    public void whenConstructResendConfirmationEmail_thenReturnResendConfirmationEmail() {

        // When
        User user = userRepository.findByEmail("user@user.com");
        SimpleMailMessage message = EmailConstructor.constructResendConfirmationEmail(
                "server", "12345", user);

        // Then
        Assert.assertEquals("Resend Registration Confirmation", message.getSubject());
        Assert.assertEquals(
                "Here is your new activation URL: server/registration-confirm?token=12345",
                message.getText()
        );
        Assert.assertEquals(message.getTo()[0], user.getEmail());
    }

    @Test
    public void whenConstructPasswordResetEmail_thenReturnPasswordResetEmail() {

        // When
        User user = userRepository.findByEmail("user@user.com");
        SimpleMailMessage message = EmailConstructor.constructPasswordResetEmail(
                "server", "12345", user);

        // Then
        Assert.assertEquals("Password Reset", message.getSubject());
        Assert.assertEquals(
                "You have requested a password reset. If this was not you, please ignore "
                        + "this message. You can reset your password with the following link: "
                        + "server/password-reset?id="
                        + user.getId() + "&token=12345",
                message.getText()
        );
        Assert.assertEquals(message.getTo()[0], user.getEmail());
    }
}
