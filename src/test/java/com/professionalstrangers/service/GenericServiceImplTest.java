package com.professionalstrangers.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.repository.HotelRepository;
import com.professionalstrangers.service.impl.GenericServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

// TODO: Not done yet
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfessionalStrangersApplication.class)
@ActiveProfiles("dev")
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
