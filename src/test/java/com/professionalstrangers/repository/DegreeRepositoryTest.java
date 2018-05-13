package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Degree;
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
public class DegreeRepositoryTest {

    @Autowired
    DegreeRepository degreeRepository;

    @Before
    public void setUpDegrees() {

        Degree firstDegree = new Degree();
        firstDegree.setName("First Degree");

        Degree secondDegree = new Degree();
        secondDegree.setName("Second Degree");

        degreeRepository.save(firstDegree);
        degreeRepository.save(secondDegree);
    }

    @Test
    public void whenFindAll_thenReturnDegreeList() {

        // When
        List<Degree> degrees = degreeRepository.findAll();

        // Then
        Assert.assertEquals(2, degrees.size());
    }

    @Test
    public void whenFindByName_thenReturnDegree() {

        // When
        Degree firstDegree = degreeRepository.findByName("First Degree");
        Degree secondDegree = degreeRepository.findByName("Second Degree");

        // Then
        Assert.assertEquals("First Degree", firstDegree.getName());
        Assert.assertEquals("Second Degree", secondDegree.getName());
    }

    @Test
    public void whenGetById_thenReturnDegree() {

        // When
        Degree firstDegree = degreeRepository.getById(1L);
        Degree secondDegree = degreeRepository.getById(2L);

        // Then
        Assert.assertEquals("First Degree", firstDegree.getName());
        Assert.assertEquals("Second Degree", secondDegree.getName());
    }
}
