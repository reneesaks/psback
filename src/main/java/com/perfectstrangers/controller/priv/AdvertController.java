package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.enums.AdvertStatus;
import com.perfectstrangers.dto.AdvertDTO;
import com.perfectstrangers.error.AdvertTimeException;
import com.perfectstrangers.error.DailyAdvertLimitException;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
import com.perfectstrangers.validation.AdvertValidator;
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
@RequestMapping("api/private/advert")
public class AdvertController {

    private GenericService genericService;

    @Autowired
    public AdvertController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all adverts as as a list.
     *
     * @return advert list.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Advert> getAdverts() {
        return genericService.getAllAdverts();
    }

    /**
     * Get one advert by id.
     *
     * @param advertId id of an existing advert.
     * @return advert object.
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @GetMapping(value = "{advertId}")
    @ResponseStatus(HttpStatus.OK)
    public Advert getAdvert(@PathVariable("advertId") Long advertId) throws EntityNotFoundException {

        return genericService.getAdvertById(advertId);
    }

    /**
     * Get all adverts belonging to an hotel.
     *
     * @param hotelId id of an existing hotel.
     * @return list of adverts.
     * @throws EntityNotFoundException when hotel with given id is not found.
     */
    @GetMapping(value = "hotel/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Advert> getAdvertsByHotelId(@PathVariable("hotelId") Long hotelId)
            throws EntityNotFoundException {
        return genericService.getAdvertsByHotelId(hotelId);
    }

    /**
     * Create a new advert.
     *
     * @param advertDTO advert object.
     * @return created advert object.
     * @throws EntityNotFoundException when hotel with given id is not found.
     * @throws DailyAdvertLimitException when user has exceeded daily adverts limit.
     * @throws AdvertTimeException when advert time is invalid.
     */
    @PostMapping(value = "new")
    @ResponseStatus(HttpStatus.OK)
    public Advert newAdvert(@RequestBody @Valid AdvertDTO advertDTO) throws
            EntityNotFoundException, DailyAdvertLimitException, AdvertTimeException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        User user = genericService.getUserById(id);
        user.setTotalAdverts(user.getTotalAdverts() + 1);

        Advert advert = new Advert();
        advert.setAdvertStatus(AdvertStatus.NOT_ACCEPTED);
        advert.setCreatedDate(Instant.now().toString());
        advert.setAdvertText(advertDTO.getAdvertText());
        advert.setMealType(advertDTO.getMealType());
        advert.setPreferredStart(advertDTO.getPreferredStart());
        advert.setPreferredEnd(advertDTO.getPreferredEnd());
        advert.setRestos(advertDTO.getRestos());
        advert.setHotels(advertDTO.getHotels());
        advert.setUser(user);

        AdvertValidator.validate(advert, genericService.getAdvertsByUserId(id));

        genericService.saveUser(user);
        genericService.saveAdvert(advert);

        return advert;
    }

    /**
     * Update an existing advert.
     *
     * @param advertDTO advert object.
     * @param advertId id of an existing advert.
     * @return updated advert object.
     * @throws EntityNotFoundException when hotel with given id is not found.
     * @throws DailyAdvertLimitException when user has exceeded daily adverts limit.
     * @throws AdvertTimeException when advert time is invalid.
     */
    @PutMapping(value = "update/{advertId}")
    @ResponseStatus(HttpStatus.OK)
    public Advert updateAdvert(
            @RequestBody @Valid AdvertDTO advertDTO,
            @PathVariable("advertId") Long advertId
    ) throws EntityNotFoundException, DailyAdvertLimitException, AdvertTimeException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        String email = genericService.getUserById(id).getEmail();
        Advert advert = genericService.getAdvertById(advertId);

        if (advert.getUser().getEmail().equals(email) || isAdmin) {
            // Set advert status to new one if it is provided
            if (advertDTO.getAdvertStatus() != null) {
                advert.setAdvertStatus(advertDTO.getAdvertStatus());
            }
            advert.setAdvertText(advertDTO.getAdvertText());
            advert.setMealType(advertDTO.getMealType());
            advert.setPreferredStart(advertDTO.getPreferredStart());
            advert.setPreferredEnd(advertDTO.getPreferredEnd());
            advert.setRestos(advertDTO.getRestos());
            advert.setHotels(advertDTO.getHotels());

            AdvertValidator.validate(advert, genericService.getAdvertsByUserId(id));

            this.genericService.saveAdvert(advert);

            return advert;
        } else {
            throw new BadCredentialsException("Only the advert owner or admin can edit this advert");
        }
    }

    /**
     * Accept a response.
     *
     * @param advertId id of an existing advert.
     * @param responseId id of an existing response.
     * @throws EntityNotFoundException when advert or response with given id is not found.
     */
    @PutMapping(value = "{advertId}/accept/{responseId}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptResponse(
            @PathVariable("advertId") Long advertId,
            @PathVariable("responseId") Long responseId) throws EntityNotFoundException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        String email = genericService.getUserById(id).getEmail();
        Advert advert = genericService.getAdvertById(advertId);
        List<Response> responses = genericService.getResponsesByAdvert(advert);

        if (advert.getUser().getEmail().equals(email) || isAdmin) {

            advert.setAdvertStatus(AdvertStatus.ACCEPTED);
            for (Response response : responses) {
                if (response.getId().equals(responseId)) {
                    response.setResponseStatus(com.perfectstrangers.domain.enums.ResponseStatus.ACCEPTED);
                } else {
                    response.setResponseStatus(com.perfectstrangers.domain.enums.ResponseStatus.DECLINED);
                }
                genericService.saveResponse(response);
            }
            genericService.saveAdvert(advert);

        } else {
            throw new BadCredentialsException("Only the advert owner or admin can edit this advert");
        }
    }

    /**
     * Delete an existing advert.
     *
     * @param advertId id of an existing advert.
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @DeleteMapping(value = "delete/{advertId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAdvert(@PathVariable("advertId") Long advertId)
            throws EntityNotFoundException, BadCredentialsException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(auth.getPrincipal().toString());
        Boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"));
        String email = genericService.getUserById(id).getEmail();
        Advert advert = genericService.getAdvertById(advertId);

        if (advert.getUser().getEmail().equals(email) || isAdmin) {
            genericService.deleteAdvert(advert);
        } else {
            throw new BadCredentialsException("Only the advert owner or admin can delete this advert");
        }
    }
}
