package com.professionalstrangers.controller.priv;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.service.GenericService;
import java.util.Collections;
import java.util.List;
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

// TODO: Not done yet
/**
 *  Default profile specified in pom.xml is being loaded in.
 *  Use @WithMockUser(username="admin",roles={"USER","ADMIN"}) annotation to mock users.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ProfessionalStrangersApplication.class)
@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenericService genericService;

    // Write test cases here
    @Test
    @WithMockUser(username="admin@user.com",roles={"USER","ADMIN"})
    public void givenHotels_whenGetHotels_thenReturnJsonArray() throws Exception {

        Hotel glamourHotel = new Hotel("Glamour Hotel");

        List<Hotel> allHotels = Collections.singletonList(glamourHotel);

        given(genericService.getAllHotels()).willReturn(allHotels);

        mvc.perform(get("/api/private/hotel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(glamourHotel.getName())));
    }

}
