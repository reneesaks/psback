package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.VerificationToken;
import java.time.Instant;
import java.util.Date;
import java.util.List;
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
public class VerificationTokenRepositoryTest {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUpVerificationTokens() {

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

        VerificationToken firstToken = new VerificationToken(UUID.randomUUID().toString(), firstUser);
        VerificationToken secondToken = new VerificationToken(UUID.randomUUID().toString(), secondUser);

        verificationTokenRepository.save(firstToken);
        verificationTokenRepository.save(secondToken);
    }

    @Test
    public void whenFindAll_thenReturnVerificationTokenList() {

        // When
        List<VerificationToken> verificationTokens = verificationTokenRepository.findAll();

        // Then
        Assert.assertEquals(2, verificationTokens.size());
    }

    @Test
    public void whenGetById_thenReturnVerificationToken() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        VerificationToken firstToken = verificationTokenRepository.getById(1L);
        VerificationToken secondToken = verificationTokenRepository.getById(2L);

        // Then
        Assert.assertEquals(firstUser, firstToken.getUser());
        Assert.assertEquals(secondUser, secondToken.getUser());
    }

    @Test
    public void whenFindByToken_thenReturnVerificationToken() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        VerificationToken firstTokenByUser = verificationTokenRepository.findByUser(firstUser);
        VerificationToken secondTokenByUser = verificationTokenRepository.findByUser(secondUser);
        VerificationToken firstTokenByToken =
                verificationTokenRepository.findByToken(firstTokenByUser.getToken());
        VerificationToken secondTokenByToken =
                verificationTokenRepository.findByToken(secondTokenByUser.getToken());

        // Then
        Assert.assertEquals(firstTokenByUser, firstTokenByToken);
        Assert.assertEquals(secondTokenByUser, secondTokenByToken);
    }

    @Test
    public void whenFindByUser_thenReturnVerificationToken() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        VerificationToken firstTokenByUser = verificationTokenRepository.findByUser(firstUser);
        VerificationToken secondTokenByUser = verificationTokenRepository.findByUser(secondUser);

        // Then
        Assert.assertEquals(firstUser, firstTokenByUser.getUser());
        Assert.assertEquals(secondUser, secondTokenByUser.getUser());
    }

    @Test
    public void whenDeleteAllExpiredSince_thenDeleteExpiredTokens() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        VerificationToken firstTokenByUser = verificationTokenRepository.findByUser(firstUser);
        VerificationToken secondTokenByUser = verificationTokenRepository.findByUser(secondUser);
        Date now = Date.from(Instant.now());
        Date expired = DateUtils.addDays(now, -5);
        firstTokenByUser.setExpiryDate(expired);
        verificationTokenRepository.deleteAllExpiredSince(now);

        // Then
        Assert.assertNull(verificationTokenRepository.findByUser(firstUser));
        Assert.assertEquals(secondUser, secondTokenByUser.getUser());
    }
}
