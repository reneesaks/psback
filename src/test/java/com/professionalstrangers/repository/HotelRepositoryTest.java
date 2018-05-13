package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.Resto;
import java.util.Collections;
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
public class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Before
    public void setHotels() {
        Resto firstResto = new Resto();
        firstResto.setName("First Resto");

        Resto secondResto = new Resto();
        secondResto.setName("Second Resto");

        Hotel firstHotel = new Hotel();
        firstHotel.setName("First Hotel");
        firstHotel.setRestos(Collections.singletonList(firstResto));

        Hotel secondHotel = new Hotel();
        secondHotel.setName("Second Hotel");
        secondHotel.setRestos(Collections.singletonList(secondResto));

        hotelRepository.save(firstHotel);
        hotelRepository.save(secondHotel);
    }

    @Test
    public void whenFindAll_thenReturnHotelList() {

        // When
        List<Hotel> hotels = hotelRepository.findAll();

        // Then
        Assert.assertEquals(2, hotels.size());
    }

    @Test
    public void whenFindByName_thenReturnHotel() {

        // When
        Hotel firstHotel = hotelRepository.findByName("First Hotel");
        Hotel secondHotel = hotelRepository.findByName("Second Hotel");

        // Then
        Assert.assertEquals("First Hotel", firstHotel.getName());
        Assert.assertEquals("Second Hotel", secondHotel.getName());
    }

    @Test
    public void whenGetById_thenReturnHotel() {

        // When
        Hotel firstHotel = hotelRepository.getById(1L);
        Hotel secondHotel = hotelRepository.getById(2L);

        // Then
        Assert.assertEquals("First Hotel", firstHotel.getName());
        Assert.assertEquals("Second Hotel", secondHotel.getName());
        Assert.assertEquals("First Resto", firstHotel.getRestos().get(0).getName());
        Assert.assertEquals("Second Resto", secondHotel.getRestos().get(0).getName());
    }
}
