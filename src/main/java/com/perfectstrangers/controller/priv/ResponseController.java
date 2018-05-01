package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.dto.ResponseDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.error.ResponseLimitException;
import com.perfectstrangers.error.ResponseTimeException;
import com.perfectstrangers.service.GenericService;
import com.perfectstrangers.validation.ResponseValidator;
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
@RequestMapping("api/private/advert/{advertId}/response")
public class ResponseController {

    private GenericService genericService;

    @Autowired
    public ResponseController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all responses related belonging to an advert.
     *
     * @param advertId advert id.
     * @return list of responses.
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAdvertResponses(@PathVariable Long advertId) throws EntityNotFoundException {
        Advert advert = genericService.getAdvertById(advertId);
        return genericService.getResponsesByAdvert(advert);
    }

    /**
     * Create a new response to an advert.
     *
     * @param responseDTO response object.
     * @param advertId advert id.
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @PostMapping(value = "new")
    @ResponseStatus(HttpStatus.OK)
    public Response newResponse(
            @RequestBody @Valid ResponseDTO responseDTO,
            @PathVariable Long advertId
    ) throws EntityNotFoundException, ResponseLimitException, ResponseTimeException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        Advert advert = genericService.getAdvertById(advertId);
        User user = genericService.getUserById(id);
        user.setTotalResponses(user.getTotalResponses() + 1);

        Response response = new Response();
        response.setResponseText(responseDTO.getResponseText());
        response.setProposedTime(responseDTO.getProposedTime());
        response.setResponseStatus(com.perfectstrangers.domain.enums.ResponseStatus.NOT_ANSWERED);
        response.setUser(user);
        response.setAdvert(advert);

        ResponseValidator.validate(user, advert, response);

        genericService.saveUser(user);
        genericService.saveResponse(response);

        return response;
    }

    /**
     * Update an existing response.
     *
     * @param responseDTO response.
     * @param responseId response id.
     * @throws EntityNotFoundException when advert or response with given id is not found.
     */
    @PutMapping(value = "update/{responseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateResponse(
            @RequestBody @Valid ResponseDTO responseDTO,
            @PathVariable("responseId") Long responseId
    ) throws EntityNotFoundException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        String email = genericService.getUserById(id).getEmail();
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        Response response = genericService.getResponseById(responseId);

        if (response.getUser().getEmail().equals(email) || isAdmin) {
            response.setResponseText(responseDTO.getResponseText());
            response.setProposedTime(responseDTO.getProposedTime());

            if (isAdmin) {
                response.setResponseStatus(responseDTO.getResponseStatus());
            }
            genericService.saveResponse(response);

            return response;
        } else {
            throw new BadCredentialsException("Only response owner or admin can edit this response");
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
            throw new BadCredentialsException("Only response owner or admin can delete this response");
        }
    }
}
