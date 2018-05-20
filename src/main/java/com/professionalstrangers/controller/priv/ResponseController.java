package com.professionalstrangers.controller.priv;

import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.dto.ResponseDTO;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.error.ResponseLimitException;
import com.professionalstrangers.error.ResponseTimeException;
import com.professionalstrangers.service.GenericService;
import com.professionalstrangers.validation.ResponseValidator;
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
@RequestMapping("api/private/invitation/{invitationId}/response")
public class ResponseController {

    private GenericService genericService;

    @Autowired
    public ResponseController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all responses related belonging to an invitation.
     *
     * @param invitationId invitation id.
     * @return list of responses.
     * @throws EntityNotFoundException when response with given id is not found.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getInvitationResponses(@PathVariable Long invitationId) throws EntityNotFoundException {
        Invitation invitation = genericService.getInvitationById(invitationId);
        return genericService.getResponsesByInvitation(invitation);
    }

    /**
     * Create a new response to an invitation.
     *
     * @param responseDTO response object.
     * @param invitationId response id.
     * @return created response object.
     * @throws EntityNotFoundException when response with given id is not found.
     * @throws ResponseLimitException when user has exceeded response limit.
     * @throws ResponseTimeException when response time is invalid.
     */
    @PostMapping(value = "new")
    @ResponseStatus(HttpStatus.OK)
    public Response newResponse(
            @RequestBody @Valid ResponseDTO responseDTO,
            @PathVariable Long invitationId
    ) throws EntityNotFoundException, ResponseLimitException, ResponseTimeException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        Invitation invitation = genericService.getInvitationById(invitationId);
        User user = genericService.getUserById(id);
        user.setTotalResponses(user.getTotalResponses() + 1);

        Response response = new Response();
        response.setResponseText(responseDTO.getResponseText());
        response.setProposedTime(responseDTO.getProposedTime());
        response.setResponseStatus(com.professionalstrangers.domain.enums.ResponseStatus.NOT_ANSWERED);
        response.setUser(user);
        response.setInvitation(invitation);

        ResponseValidator.validate(user, invitation, response);

        genericService.saveUser(user);
        genericService.saveResponse(response);

        return response;
    }

    /**
     * Update an existing response.
     *
     * @param responseDTO response.
     * @param responseId response id.
     * @return updated response object.
     * @throws EntityNotFoundException when invitation with given id is not found.
     * @throws ResponseLimitException when user has exceeded response limit.
     * @throws ResponseTimeException when response time is invalid.
     */
    @PutMapping(value = "update/{responseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateResponse(
            @RequestBody @Valid ResponseDTO responseDTO,
            @PathVariable("responseId") Long responseId
    ) throws EntityNotFoundException, ResponseLimitException, ResponseTimeException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        User user = genericService.getUserById(id);
        Response response = genericService.getResponseById(responseId);

        if (response.getUser().getEmail().equals(user.getEmail()) || isAdmin) {
            response.setResponseText(responseDTO.getResponseText());
            response.setProposedTime(responseDTO.getProposedTime());

            if (isAdmin) {
                response.setResponseStatus(responseDTO.getResponseStatus());
            }

            ResponseValidator.validate(user, response.getInvitation(), response);

            genericService.saveResponse(response);

            return response;
        } else {
            throw new BadCredentialsException("Only the response owner or admin can edit this response");
        }
    }

    /**
     * Delete response by id.
     *
     * @param responseId id of an existing response.
     * @throws EntityNotFoundException when response is not found.
     * @throws BadCredentialsException when user is not an admin and not the owner of the response.
     */
    @DeleteMapping(value = "delete/{responseId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResponse(@PathVariable("responseId") Long responseId)
            throws EntityNotFoundException, BadCredentialsException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        Response response = genericService.getResponseById(responseId);

        if (response.getUser().getEmail().equals(email) || isAdmin) {
            genericService.deleteResponse(response);
        } else {
            throw new BadCredentialsException("Only the response owner or admin can delete this response");
        }
    }
}
