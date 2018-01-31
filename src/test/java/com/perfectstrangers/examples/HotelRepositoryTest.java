package com.perfectstrangers.examples;

import com.perfectstrangers.PerfectStrangersApplication;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.repository.HotelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PerfectStrangersApplication.class)
@DataJpaTest
public class HotelRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HotelRepository hotelRepository;


    // Write test cases here
    @Test
    public void whenFindByName_thenReturnHotel() {

        // Given
        Hotel hotelGlamour = new Hotel("Hotel Glamour");
        entityManager.persist(hotelGlamour);
        entityManager.flush();

        // When
        Hotel found = hotelRepository.findByName(hotelGlamour.getName());

        // Then
        assertThat(found.getName()).isEqualTo(hotelGlamour.getName());

    }

}
