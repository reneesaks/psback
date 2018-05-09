package com.perfectstrangers.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.perfectstrangers.PerfectStrangersApplication;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.repository.HotelRepository;
import com.perfectstrangers.service.impl.GenericServiceImpl;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PerfectStrangersApplication.class)
public class GenericServiceImplTest {

    @Autowired
    private GenericService genericService;

    @MockBean
    private HotelRepository hotelRepository;


    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public GenericService hotelService() {
            return new GenericServiceImpl();
        }
    }

    @Before
    public void setUp() {

        Hotel glamourHotel = new Hotel("Glamour Hotel");

        Mockito.when(hotelRepository.findByName(glamourHotel.getName())).thenReturn(glamourHotel);

    }

    // Write test cases here
    @Test
    public void whenValidName_thenHotelShouldBeFound() throws EntityNotFoundException {

        String name = "Glamour Hotel";
        Hotel found = genericService.getHotelByName(name);

        assertThat(found.getName()).isEqualTo(name);

    }

}
