package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Occupation;
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
public class OccupationRepositoryTest {

    @Autowired
    OccupationRepository occupationRepository;

    @Before
    public void setUpOccupations() {

        Occupation firstOccupation = new Occupation();
        firstOccupation.setName("First Occupation");

        Occupation secondOccupation = new Occupation();
        secondOccupation.setName("Second Occupation");

        occupationRepository.save(firstOccupation);
        occupationRepository.save(secondOccupation);
    }

    @Test
    public void whenFindAll_thenReturnOccupationList() {

        // When
        List<Occupation> occupations = occupationRepository.findAll();

        // Then
        Assert.assertEquals(2, occupations.size());
    }

    @Test
    public void whenFindByName_thenReturnOccupation() {

        // When
        Occupation firstOccupation = occupationRepository.findByName("First Occupation");
        Occupation secondOccupation = occupationRepository.findByName("Second Occupation");

        // Then
        Assert.assertEquals("First Occupation", firstOccupation.getName());
        Assert.assertEquals("Second Occupation", secondOccupation.getName());
    }

    @Test
    public void whenGetById_thenReturnOccupation() {

        // When
        Occupation firstOccupation = occupationRepository.getById(1L);
        Occupation secondOccupation = occupationRepository.getById(2L);

        // Then
        Assert.assertEquals("First Occupation", firstOccupation.getName());
        Assert.assertEquals("Second Occupation", secondOccupation.getName());
    }
}
