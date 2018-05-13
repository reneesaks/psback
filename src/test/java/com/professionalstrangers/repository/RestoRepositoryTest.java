package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.Resto;
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
public class RestoRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestoRepository restoRepository;

    @Before
    public void setRestos() {

        Hotel firstHotel = new Hotel();
        firstHotel.setName("First Hotel");

        Hotel secondHotel = new Hotel();
        secondHotel.setName("Second Hotel");

        hotelRepository.save(firstHotel);
        hotelRepository.save(secondHotel);

        Resto firstResto = new Resto();
        firstResto.setName("First Resto");
        firstResto.setHotel(firstHotel);

        Resto secondResto = new Resto();
        secondResto.setName("Second Resto");
        secondResto.setHotel(secondHotel);

        restoRepository.save(firstResto);
        restoRepository.save(secondResto);
    }

    @Test
    public void whenFindAll_thenReturnRestoList() {

        // When
        List<Resto> restos = restoRepository.findAll();

        // Then
        Assert.assertEquals(2, restos.size());
    }

    @Test
    public void whenFindByName_thenReturnResto() {

        // When
        Resto firstResto = restoRepository.findByName("First Resto");
        Resto secondResto = restoRepository.findByName("Second Resto");

        // Then
        Assert.assertEquals("First Resto", firstResto.getName());
        Assert.assertEquals("Second Resto", secondResto.getName());
    }

    @Test
    public void whenGetById_thenReturnResto() {

        // When
        Resto firstResto = restoRepository.getById(1L);
        Resto secondResto = restoRepository.getById(2L);

        // Then
        Assert.assertEquals("First Resto", firstResto.getName());
        Assert.assertEquals("Second Resto", secondResto.getName());
        Assert.assertEquals("First Hotel", firstResto.getHotel().getName());
        Assert.assertEquals("Second Hotel", secondResto.getHotel().getName());
    }
}
