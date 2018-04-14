package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.enums.AdvertStatus;
import com.perfectstrangers.dto.AdvertDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// TODO: Advert logic is more complicated. This is simplified. Right now will focus on this in front end.
// TODO: When front end is ready, this logic should be also here so server validation is done.
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
     * @param id id of an existing advert.
     * @return advert object.
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public Advert getAdvert(@PathVariable("id") Long id) throws EntityNotFoundException {
        return genericService.getAdvertById(id);
    }

    /**
     * Creates new advert.
     *
     * @param advertDTO advert object.
     */
    @PostMapping(value = "new")
    @ResponseStatus(HttpStatus.OK)
    public void newAdvert(@RequestBody @Valid AdvertDTO advertDTO) {

        Advert advert = new Advert();
        String currentTime = Instant.now().toString();

        advert.setAdvertStatus(AdvertStatus.NOT_ACCEPTED);
        advert.setCreatedDate(currentTime);
        advert.setAdvertText(advertDTO.getAdvertText());
        advert.setMealType(advertDTO.getMealType());
        advert.setPreferredStart(advertDTO.getPreferredStart());
        advert.setPreferredEnd(advertDTO.getPreferredEnd());
        advert.setRestos(advertDTO.getRestos());
        advert.setHotels(advertDTO.getHotels());

        this.genericService.saveAdvert(advert);
    }

    /**
     * Updates existing advert.
     *
     * @param advertDTO advert object.
     * @param id id of an existing advert.
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @PutMapping(value = "update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAdvert(
            @RequestBody @Valid AdvertDTO advertDTO,
            @PathVariable("id") Long id
    ) throws EntityNotFoundException {

        Advert advert = genericService.getAdvertById(id);

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

        this.genericService.saveAdvert(advert);
    }

    /**
     * Delete an existing advert.
     *
     * @param id id of an existing advert.
     * @throws EntityNotFoundException when advert with given id is not found.
     */
    @DeleteMapping(value = "delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAdvert(@PathVariable("id") Long id) throws EntityNotFoundException {
        Advert advert = genericService.getAdvertById(id);
        genericService.deleteAdvert(advert);
    }
}
