package com.perfectstrangers.repository;

import com.perfectstrangers.PerfectStrangersApplication;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Resto;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PerfectStrangersApplication.class)
@DataJpaTest
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
        List<Hotel> found = hotelRepository.findAll();

        // Then
        Assert.assertEquals(2, found.size());
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
    public void whenFindById_thenReturnHotel() {

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
