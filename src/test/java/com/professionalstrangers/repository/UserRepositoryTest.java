package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.User;
import java.util.List;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void seUsers() {

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
    }

    @Test
    public void whenFindAll_thenReturnUserList() {

        // When
        List<User> users = userRepository.findAll();

        // Then
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void whenFindByEmail_thenReturnUserList() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");

        // Then
        Assert.assertEquals("firstUser@user.com", firstUser.getEmail());
        Assert.assertEquals("secondUser@user.com", secondUser.getEmail());
    }

    @Test
    public void whenGetById_thenReturnUser() {

        // When
        User firstUser = userRepository.getById(1L);
        User secondUser = userRepository.getById(2L);

        // Then
        Assert.assertEquals("firstUser@user.com", firstUser.getEmail());
        Assert.assertEquals("secondUser@user.com", secondUser.getEmail());
    }
}
