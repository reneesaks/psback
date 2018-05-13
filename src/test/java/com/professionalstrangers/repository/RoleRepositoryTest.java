package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Role;
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
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void seRoles() {

        Role firstRole = new Role();
        firstRole.setRoleName("First Role");

        Role secondRole = new Role();
        secondRole.setRoleName("Second Role");

        roleRepository.save(firstRole);
        roleRepository.save(secondRole);
    }

    @Test
    public void whenFindAll_thenReturnRoleList() {

        // When
        List<Role> roles = roleRepository.findAll();

        // Then
        Assert.assertEquals(2, roles.size());
    }

    @Test
    public void whenFindByRoleName_thenReturnRoleList() {

        // When
        List<Role> firstRole = roleRepository.findByRoleName("First Role");
        List<Role> secondRole = roleRepository.findByRoleName("Second Role");

        // Then
        Assert.assertEquals("First Role", firstRole.get(0).getRoleName());
        Assert.assertEquals("Second Role", secondRole.get(0).getRoleName());
    }

    @Test
    public void whenGetById_thenReturnRole() {

        // When
        Role firstRole = roleRepository.getById(1L);
        Role secondRole = roleRepository.getById(2L);

        // Then
        Assert.assertEquals("First Role", firstRole.getRoleName());
        Assert.assertEquals("Second Role", secondRole.getRoleName());
    }
}
