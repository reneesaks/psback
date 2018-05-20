package com.professionalstrangers.controller.priv;

import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.enums.InvitationStatus;
import com.professionalstrangers.dto.InvitationDTO;
import com.professionalstrangers.error.InvitationTimeException;
import com.professionalstrangers.error.DailyInvitationLimitException;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.service.GenericService;
import com.professionalstrangers.validation.InvitationValidator;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private/invitation")
public class InvitationController {

    private GenericService genericService;

    @Autowired
    public InvitationController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all invitations as as a list.
     *
     * @return invitation list.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Invitation> getInvitations() {
        return genericService.getAllInvitations();
    }

    /**
     * Get one invitation by id.
     *
     * @param invitationId id of an existing invitation.
     * @return invitation object.
     * @throws EntityNotFoundException when invitation with given id is not found.
     */
    @GetMapping(value = "{invitationId}")
    @ResponseStatus(HttpStatus.OK)
    public Invitation getInvitation(@PathVariable("invitationId") Long invitationId) throws EntityNotFoundException {

        return genericService.getInvitationById(invitationId);
    }

    /**
     * Get all invitations belonging to an hotel.
     *
     * @param hotelId id of an existing hotel.
     * @return list of invitations.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @GetMapping(value = "hotel/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invitation> getInvitationsByHotelId(@PathVariable("hotelId") Long hotelId)
            throws EntityNotFoundException {
        return genericService.getInvitationsByHotelId(hotelId);
    }

    /**
     * Create a new invitation.
     *
     * @param invitationDTO invitation object.
     * @return created invitation object.
     * @throws EntityNotFoundException when hotel with given id is not found.
     * @throws DailyInvitationLimitException when user has exceeded daily invitations limit.
     * @throws InvitationTimeException when invitation time is invalid.
     */
    @PostMapping(value = "new")
    @ResponseStatus(HttpStatus.OK)
    public Invitation newInvitation(@RequestBody @Valid InvitationDTO invitationDTO) throws
            EntityNotFoundException, DailyInvitationLimitException, InvitationTimeException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        User user = genericService.getUserById(id);
        user.setTotalInvitations(user.getTotalInvitations() + 1);

        Invitation invitation = new Invitation();
        invitation.setInvitationStatus(InvitationStatus.NOT_ACCEPTED);
        invitation.setCreatedDate(Instant.now().toString());
        invitation.setInvitationText(invitationDTO.getInvitationText());
        invitation.setMealType(invitationDTO.getMealType());
        invitation.setPreferredStart(invitationDTO.getPreferredStart());
        invitation.setPreferredEnd(invitationDTO.getPreferredEnd());
        invitation.setRestos(invitationDTO.getRestos());
        invitation.setHotels(invitationDTO.getHotels());
        invitation.setUser(user);

        InvitationValidator.validate(invitation, genericService.getInvitationsByUserId(id));

        genericService.saveUser(user);
        genericService.saveInvitation(invitation);

        return invitation;
    }

    /**
     * Update an existing invitation.
     *
     * @param invitationDTO invitation object.
     * @param invitationId id of an existing invitation.
     * @return updated invitation object.
     * @throws EntityNotFoundException when hotel with given id is not found.
     * @throws DailyInvitationLimitException when user has exceeded daily invitations limit.
     * @throws InvitationTimeException when invitation time is invalid.
     */
    @PutMapping(value = "update/{invitationId}")
    @ResponseStatus(HttpStatus.OK)
    public Invitation updateInvitation(
            @RequestBody @Valid InvitationDTO invitationDTO,
            @PathVariable("invitationId") Long invitationId
    ) throws EntityNotFoundException, DailyInvitationLimitException, InvitationTimeException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        String email = genericService.getUserById(id).getEmail();
        Invitation invitation = genericService.getInvitationById(invitationId);

        if (invitation.getUser().getEmail().equals(email) || isAdmin) {
            // Set invitation status to new one if it is provided
            if (invitationDTO.getInvitationStatus() != null) {
                invitation.setInvitationStatus(invitationDTO.getInvitationStatus());
            }
            invitation.setInvitationText(invitationDTO.getInvitationText());
            invitation.setMealType(invitationDTO.getMealType());
            invitation.setPreferredStart(invitationDTO.getPreferredStart());
            invitation.setPreferredEnd(invitationDTO.getPreferredEnd());
            invitation.setRestos(invitationDTO.getRestos());
            invitation.setHotels(invitationDTO.getHotels());

            InvitationValidator.validate(invitation, genericService.getInvitationsByUserId(id));

            this.genericService.saveInvitation(invitation);

            return invitation;
        } else {
            throw new BadCredentialsException("Only the invitation owner or admin can edit this invitation");
        }
    }

    /**
     * Accept a response.
     *
     * @param invitationId id of an existing invitation.
     * @param responseId id of an existing response.
     * @throws EntityNotFoundException when invitation or response with given id is not found.
     */
    @PutMapping(value = "{invitationId}/accept/{responseId}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptResponse(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("responseId") Long responseId) throws EntityNotFoundException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        String email = genericService.getUserById(id).getEmail();
        Invitation invitation = genericService.getInvitationById(invitationId);
        List<Response> responses = genericService.getResponsesByInvitation(invitation);

        if (invitation.getUser().getEmail().equals(email) || isAdmin) {

            invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
            for (Response response : responses) {
                if (response.getId().equals(responseId)) {
                    response.setResponseStatus(com.professionalstrangers.domain.enums.ResponseStatus.ACCEPTED);
                } else {
                    response.setResponseStatus(com.professionalstrangers.domain.enums.ResponseStatus.DECLINED);
                }
                genericService.saveResponse(response);
            }
            genericService.saveInvitation(invitation);

        } else {
            throw new BadCredentialsException("Only the invitation owner or admin can edit this invitation");
        }
    }

    /**
     * Decline a response.
     *
     * @param invitationId id of an existing invitation.
     * @param responseId id of an existing response.
     * @throws EntityNotFoundException when invitation or response with given id is not found.
     */
    @PutMapping(value = "{invitationId}/decline/{responseId}")
    @ResponseStatus(HttpStatus.OK)
    public void declineResponse(
            @PathVariable("invitationId") Long invitationId,
            @PathVariable("responseId") Long responseId) throws EntityNotFoundException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        String email = genericService.getUserById(id).getEmail();
        Invitation invitation = genericService.getInvitationById(invitationId);
        Response response = genericService.getResponseById(responseId);

        if (invitation.getUser().getEmail().equals(email) || isAdmin) {
            response.setResponseStatus(com.professionalstrangers.domain.enums.ResponseStatus.DECLINED);
            genericService.saveResponse(response);
        } else {
            throw new BadCredentialsException("Only the invitation owner or admin can edit this invitation");
        }
    }

    /**
     * Delete an existing invitation.
     *
     * @param invitationId id of an existing invitation.
     * @throws EntityNotFoundException when invitation with given id is not found.
     */
    @DeleteMapping(value = "delete/{invitationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvitation(@PathVariable("invitationId") Long invitationId)
            throws EntityNotFoundException, BadCredentialsException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        String email = genericService.getUserById(id).getEmail();
        Invitation invitation = genericService.getInvitationById(invitationId);

        if (invitation.getUser().getEmail().equals(email) || isAdmin) {
            genericService.deleteInvitation(invitation);
        } else {
            throw new BadCredentialsException("Only the invitation owner or admin can delete this invitation");
        }
    }
}
