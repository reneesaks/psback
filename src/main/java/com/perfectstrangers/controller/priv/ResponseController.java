package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.dto.ResponseDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
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

// TODO: Response logic is more complicated. This is simplified. Right now will focus on this in front end.
// TODO: When front end is ready, this logic should be also here so server validation is done.
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
     * @param advertId advert id
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @PostMapping(value = "new")
    @ResponseStatus(HttpStatus.OK)
    public Response newResponse(
            @RequestBody @Valid ResponseDTO responseDTO,
            @PathVariable Long advertId
    ) throws EntityNotFoundException {

        Advert advert = genericService.getAdvertById(advertId);
        List<Response> advertResponses = advert.getResponses();
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = genericService.getUserByEmail(email);

        Response response = new Response();
        response.setResponseText(responseDTO.getResponseText());
        response.setProposedTime(responseDTO.getProposedTime());
        response.setResponseStatus(com.perfectstrangers.domain.enums.ResponseStatus.NOT_ANSWERED);
        response.setUser(user);

        advertResponses.add(response);
        advert.setResponses(advertResponses);
        genericService.saveAdvert(advert);
        return response;
    }

    /**
     * Update an existing response.
     *
     * @param responseDTO response
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
        String email = auth.getPrincipal().toString();
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        Response response = genericService.getResponseById(responseId);

        if (response.getUser().getEmail().equals(email) || isAdmin) {
            response.setResponseText(responseDTO.getResponseText());
            response.setProposedTime(responseDTO.getProposedTime());
            response.setResponseStatus(responseDTO.getResponseStatus());
            genericService.saveResponse(response);

            return response;
        } else {
            throw new BadCredentialsException("Only response owner or admin can edit this response");
        }
    }

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
