package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.enums.InvitationStatus;
import com.professionalstrangers.domain.enums.ResponseStatus;
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
public class ResponseRepositoryTest {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setResponses() {

        User firstUser = new User();
        firstUser.setActivated(true);
        firstUser.setEmail("firstUser@user.com");
        firstUser.setPassword("password");
        firstUser.setAlias("firstUser");

        User secondUser = new User();
        secondUser.setActivated(true);
        secondUser.setEmail("secondUser@user.com");
        secondUser.setPassword("password");
        secondUser.setAlias("secondUser");

        userRepository.save(firstUser);
        userRepository.save(secondUser);

        Invitation firstInvitation = new Invitation();
        firstInvitation.setInvitationText("First Invitation");
        firstInvitation.setInvitationStatus(InvitationStatus.NOT_ACCEPTED);
        firstInvitation.setUser(firstUser);

        Invitation secondInvitation = new Invitation();
        secondInvitation.setInvitationText("Second Invitation");
        secondInvitation.setInvitationStatus(InvitationStatus.NOT_ACCEPTED);
        secondInvitation.setUser(secondUser);

        invitationRepository.save(firstInvitation);
        invitationRepository.save(secondInvitation);

        Response firstResponse = new Response();
        firstResponse.setResponseStatus(ResponseStatus.NOT_ANSWERED);
        firstResponse.setUser(secondUser);
        firstResponse.setInvitation(firstInvitation);
        firstResponse.setResponseText("First Response");

        Response secondResponse = new Response();
        secondResponse.setResponseStatus(ResponseStatus.NOT_ANSWERED);
        secondResponse.setUser(firstUser);
        secondResponse.setInvitation(secondInvitation);
        secondResponse.setResponseText("Second Response");

        responseRepository.save(firstResponse);
        responseRepository.save(secondResponse);
    }

    @Test
    public void whenFindAll_thenReturnResponseList() {

        // When
        List<Response> responses = responseRepository.findAll();

        //Then
        Assert.assertEquals(2, responses.size());
    }

    @Test
    public void whenGetById_thenReturnResponse() {

        // When
        Response firstResponse = responseRepository.getById(1L);
        Response secondResponse = responseRepository.getById(2L);

        //Then
        Assert.assertEquals("First Response", firstResponse.getResponseText());
        Assert.assertEquals(ResponseStatus.NOT_ANSWERED, firstResponse.getResponseStatus());
        Assert.assertEquals("Second Response", secondResponse.getResponseText());
        Assert.assertEquals(ResponseStatus.NOT_ANSWERED, secondResponse.getResponseStatus());
    }

    @Test
    public void whenFindAllByUser_thenReturnResponsesList() {

        // When
        User firstUser = userRepository.findByEmail("firstUser@user.com");
        User secondUser = userRepository.findByEmail("secondUser@user.com");
        List<Response> firstUserResponses = responseRepository.findAllByUser(firstUser);
        List<Response> secondUserResponses = responseRepository.findAllByUser(secondUser);

        //Then
        Assert.assertEquals(1, firstUserResponses.size());
        Assert.assertEquals(1, secondUserResponses.size());
        Assert.assertEquals(secondUser, firstUserResponses.get(0).getInvitation().getUser());
        Assert.assertEquals(firstUser, secondUserResponses.get(0).getInvitation().getUser());
    }
}
