package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.PasswordResetToken;
import com.professionalstrangers.domain.User;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfessionalStrangersApplication.class)
@DataJpaTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PasswordResetTokenRepositoryTest {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUpPasswordResetTokens() {

        User firstUser = new User();
        firstUser.setActivated(true);
        firstUser.setEmail("firstUser@user.com");
        firstUser.setPassword("password");
        firstUser.setAlias("firstUser");

        User secondUser = new User();
        secondUser.setActivated(true);
        secondUser.setEmail("secondUser@user.com");
        secondUser.setPassword("password");
        secondUser.setAlias("secondUser");

        userRepository.save(firstUser);
        userRepository.save(secondUser);

        PasswordResetToken firstToken = new PasswordResetToken(UUID.randomUUID().toString(), firstUser);
        PasswordResetToken secondToken = new PasswordResetToken(UUID.randomUUID().toString(), secondUser);

        passwordResetTokenRepository.save(firstToken);
        passwordResetTokenRepository.save(secondToken);
    }

    @Test
    public void whenFindByToken_thenReturnPasswordToken() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        PasswordResetToken firstTokenByUser = passwordResetTokenRepository.findByUser(firstUser);
        PasswordResetToken secondTokenByUser = passwordResetTokenRepository.findByUser(secondUser);
        PasswordResetToken firstTokenByToken =
                passwordResetTokenRepository.findByToken(firstTokenByUser.getToken());
        PasswordResetToken secondTokenByToken =
                passwordResetTokenRepository.findByToken(secondTokenByUser.getToken());

        // Then
        Assert.assertEquals(firstTokenByUser, firstTokenByToken);
        Assert.assertEquals(secondTokenByUser, secondTokenByToken);
    }

    @Test
    public void whenFindByUser_thenReturnPasswordToken() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        PasswordResetToken firstTokenByUser = passwordResetTokenRepository.findByUser(firstUser);
        PasswordResetToken secondTokenByUser = passwordResetTokenRepository.findByUser(secondUser);

        // Then
        Assert.assertEquals(firstUser, firstTokenByUser.getUser());
        Assert.assertEquals(secondUser, secondTokenByUser.getUser());
    }

    @Test
    public void whenDeleteAllExpiredSince_thenDeleteExpiredTokens() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        PasswordResetToken firstTokenByUser = passwordResetTokenRepository.findByUser(firstUser);
        PasswordResetToken secondTokenByUser = passwordResetTokenRepository.findByUser(secondUser);
        Date now = Date.from(Instant.now());
        Date expired = DateUtils.addDays(now, -5);
        firstTokenByUser.setExpiryDate(expired);
        passwordResetTokenRepository.deleteAllExpiredSince(now);

        // Then
        Assert.assertNull(passwordResetTokenRepository.findByUser(firstUser));
        Assert.assertEquals(secondUser, secondTokenByUser.getUser());
    }
}
