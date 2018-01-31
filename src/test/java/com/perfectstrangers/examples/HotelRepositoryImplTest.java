package com.perfectstrangers.examples;

import com.perfectstrangers.PerfectStrangersApplication;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.repository.HotelRepository;
import com.perfectstrangers.service.HotelService;
import com.perfectstrangers.service.impl.HotelServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PerfectStrangersApplication.class)
public class HotelRepositoryImplTest {

    @Autowired
    private HotelService hotelService;

    @MockBean
    private HotelRepository hotelRepository;


    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public HotelService hotelService() {
            return new HotelServiceImpl();
        }
    }

    @Before
    public void setUp() {

        Hotel glamourHotel = new Hotel("Glamour Hotel");

        Mockito.when(hotelRepository.findByName(glamourHotel.getName())).thenReturn(glamourHotel);

    }

    // Write test cases here
    @Test
    public void whenValidName_thenHotelShouldBeFound() {

        String name = "Glamour Hotel";
        Hotel found = hotelService.getHotelByName(name);

        assertThat(found.getName()).isEqualTo(name);

    }

}
