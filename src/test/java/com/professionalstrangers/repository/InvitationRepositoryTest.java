package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.domain.enums.InvitationStatus;
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
public class InvitationRepositoryTest {

    @Autowired
    private InvitationRepository invitationRepository;

    @Before
    public void setInvitations() {

        Invitation firstInvitation = new Invitation();
        firstInvitation.setInvitationText("First Invitation");
        firstInvitation.setInvitationStatus(InvitationStatus.NOT_ACCEPTED);

        Invitation secondInvitation = new Invitation();
        secondInvitation.setInvitationText("Second Invitation");
        secondInvitation.setInvitationStatus(InvitationStatus.ACCEPTED);

        invitationRepository.save(firstInvitation);
        invitationRepository.save(secondInvitation);
    }

    @Test
    public void whenFindAllByInvitationStatusIsNot_thenReturnInvitationList() {

        // When
        List<Invitation> firstInvitation = invitationRepository.findAllByInvitationStatusIsNot(InvitationStatus.ACCEPTED);
        List<Invitation> secondInvitation = invitationRepository.findAllByInvitationStatusIsNot(InvitationStatus.NOT_ACCEPTED);

        //Then
        Assert.assertEquals(1, firstInvitation.size());
        Assert.assertEquals(1, secondInvitation.size());
        Assert.assertEquals(InvitationStatus.NOT_ACCEPTED, firstInvitation.get(0).getInvitationStatus());
        Assert.assertEquals(InvitationStatus.ACCEPTED, secondInvitation.get(0).getInvitationStatus());
    }

    @Test
    public void whenGetById_thenReturnInvitation() {

        // When
        Invitation firstInvitation = invitationRepository.getById(1L);
        Invitation secondInvitation = invitationRepository.getById(2L);

        //Then
        Assert.assertEquals("First Invitation", firstInvitation.getInvitationText());
        Assert.assertEquals(InvitationStatus.NOT_ACCEPTED, firstInvitation.getInvitationStatus());
        Assert.assertEquals("Second Invitation", secondInvitation.getInvitationText());
        Assert.assertEquals(InvitationStatus.ACCEPTED, secondInvitation.getInvitationStatus());
    }
}
