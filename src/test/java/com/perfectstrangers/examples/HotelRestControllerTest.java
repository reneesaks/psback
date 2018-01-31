package com.perfectstrangers.examples;

import com.perfectstrangers.PerfectStrangersApplication;
import com.perfectstrangers.controller.closed.ResourceController;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.service.GenericService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *  Default profile specified in pom.xml is being loaded in.
 *  Use @WithMockUser(username="admin",roles={"USER","ADMIN"}) annotation to mock users.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PerfectStrangersApplication.class)
@WebMvcTest(ResourceController.class)
public class HotelRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenericService genericService;

    // Write test cases here
    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void givenHotels_whenGetHotels_thenReturnJsonArray() throws Exception {

        Hotel glamourHotel = new Hotel("Glamour Hotel");

        List<Hotel> allHotels = Arrays.asList(glamourHotel);

        given(genericService.getAllHotels()).willReturn(allHotels);

        mvc.perform(get("/api/private/hotels")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(glamourHotel.getName())));
    }

}
