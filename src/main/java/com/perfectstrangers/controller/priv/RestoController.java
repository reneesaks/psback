package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Resto;
import com.perfectstrangers.dto.RestoDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/private/resto")
public class RestoController {

    private GenericService genericService;

    @Autowired
    public RestoController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all restos as a list.
     *
     * @return restos list.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Resto> getRestos() {
        return genericService.getAllRestos();
    }

    /**
     * Get one resto by id.
     *
     * @param restoId id of an existing resto.
     * @return resto object.
     * @throws EntityNotFoundException when resto with given id is not found.
     */
    @GetMapping("{restoId}")
    @ResponseStatus(HttpStatus.OK)
    public Resto getResto(@PathVariable("restoId") Long restoId)
            throws EntityNotFoundException {
        return genericService.getRestoById(restoId);
    }

    /**
     * Create a new resto.
     *
     * @param restoDTO resto object.
     */
    @PostMapping("new")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void newResto(@RequestBody @Valid RestoDTO restoDTO) {

        Resto resto = new Resto();

        resto.setName(restoDTO.getName());
        resto.setWebpage(restoDTO.getWebpage());
        resto.setCountry(restoDTO.getCountry());
        resto.setState(restoDTO.getState());
        resto.setCity(restoDTO.getCity());
        resto.setAddress(restoDTO.getAddress());
        resto.setZipCode(restoDTO.getZipCode());
        resto.setHotel(restoDTO.getHotel());

        genericService.saveResto(resto);
    }

    /**
     * Update an existing resto.
     *
     * @param restoDTO resto object.
     * @param restoId id of an existing resto.
     * @throws EntityNotFoundException when resto with given id is not found.
     */
    @PutMapping("update/{restoId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void updateResto(
            @RequestBody @Valid RestoDTO restoDTO,
            @PathVariable("restoId") Long restoId
    ) throws EntityNotFoundException {

        Resto resto = genericService.getRestoById(restoId);

        resto.setName(restoDTO.getName());
        resto.setWebpage(restoDTO.getWebpage());
        resto.setCountry(restoDTO.getCountry());
        resto.setState(restoDTO.getState());
        resto.setCity(restoDTO.getCity());
        resto.setAddress(restoDTO.getAddress());
        resto.setZipCode(restoDTO.getZipCode());
        resto.setHotel(restoDTO.getHotel());

        genericService.saveResto(resto);
    }

    /**
     * Delete an existing resto.
     *
     * @param restoId id of an existing resto.
     * @throws EntityNotFoundException when resto with given id is not found.
     */
    @DeleteMapping("delete/{restoId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResto(@PathVariable("restoId") Long restoId) throws EntityNotFoundException {
        Resto resto = genericService.getRestoById(restoId);
        genericService.deleteResto(resto);
    }
}
