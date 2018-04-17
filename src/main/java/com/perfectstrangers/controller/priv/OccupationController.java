package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Occupation;
import com.perfectstrangers.dto.OccupationDTO;
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
@RequestMapping("api/private/occupation")
public class OccupationController {

    private GenericService genericService;

    @Autowired
    public OccupationController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all occupations as a list.
     *
     * @return occupations list.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Occupation> getOccupations() {
        return genericService.getAllOccupations();
    }

    /**
     * Get one occupation by id.
     *
     * @param occupationId id of an existing occupation.
     * @return occupation object.
     * @throws EntityNotFoundException when occupation with given id is not found.
     */
    @GetMapping("{occupationId}")
    @ResponseStatus(HttpStatus.OK)
    public Occupation getOccupation(@PathVariable("occupationId") Long occupationId)
            throws EntityNotFoundException {
        return genericService.getOccupationById(occupationId);
    }

    /**
     * Create a new occupation.
     *
     * @param occupationDTO occupation object.
     */
    @PostMapping("new")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public Occupation newOccupation(@RequestBody @Valid OccupationDTO occupationDTO) {

        Occupation occupation = new Occupation();
        occupation.setName(occupationDTO.getName());
        occupation.setDescription(occupationDTO.getDescription());
        genericService.saveOccupation(occupation);
        return occupation;
    }

    /**
     * Update an existing occupation.
     *
     * @param occupationDTO occupation object.
     * @param occupationId id of an existing occupation.
     * @throws EntityNotFoundException when occupation with given id is not found.
     */
    @PutMapping("update/{occupationId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public Occupation updateOccupation(
            @RequestBody @Valid OccupationDTO occupationDTO,
            @PathVariable("occupationId") Long occupationId
    ) throws EntityNotFoundException {

        Occupation occupation = genericService.getOccupationById(occupationId);
        occupation.setName(occupationDTO.getName());
        occupation.setDescription(occupationDTO.getDescription());
        genericService.saveOccupation(occupation);
        return occupation;
    }

    /**
     * Delete an existing occupation.
     *
     * @param occupationId id of an existing occupation.
     * @throws EntityNotFoundException when occupation with given id is not found.
     */
    @DeleteMapping("delete/{occupationId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOccupation(
            @PathVariable("occupationId") Long occupationId
    ) throws EntityNotFoundException {

        Occupation occupation = genericService.getOccupationById(occupationId);
        genericService.deleteOccupation(occupation);
    }
}
