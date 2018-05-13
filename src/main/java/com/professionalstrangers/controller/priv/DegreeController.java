package com.professionalstrangers.controller.priv;

import com.professionalstrangers.domain.Degree;
import com.professionalstrangers.dto.DegreeDTO;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.service.GenericService;
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
@RequestMapping("api/private/degree")
public class DegreeController {

    private GenericService genericService;

    @Autowired
    public DegreeController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Get all degrees as a list.
     *
     * @return degrees list.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Degree> getDegrees() {
        return genericService.getAllDegrees();
    }

    /**
     * Get one degree by id.
     *
     * @param degreeId id of an existing degree.
     * @return degree object.
     * @throws EntityNotFoundException when degree with given id is not found.
     */
    @GetMapping("{degreeId}")
    @ResponseStatus(HttpStatus.OK)
    public Degree getDegree(@PathVariable("degreeId") Long degreeId) throws EntityNotFoundException {
        return genericService.getDegreeById(degreeId);
    }

    /**
     * Create a new degree.
     *
     * @param degreeDTO degree object.
     * @return created degree object.
     */
    @PostMapping("new")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public Degree newDegree(@RequestBody @Valid DegreeDTO degreeDTO) {

        Degree degree = new Degree();
        degree.setName(degreeDTO.getName());
        degree.setDescription(degreeDTO.getDescription());
        genericService.saveDegree(degree);
        return degree;
    }

    /**
     * Update an existing degree.
     *
     * @param degreeDTO degree object.
     * @param degreeId id of an existing degree.
     * @return updated degree object.
     * @throws EntityNotFoundException when degree with given id is not found.
     */
    @PutMapping("update/{degreeId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public Degree updateDegree(
            @RequestBody @Valid DegreeDTO degreeDTO,
            @PathVariable("degreeId") Long degreeId
    ) throws EntityNotFoundException {

        Degree degree = genericService.getDegreeById(degreeId);
        degree.setName(degreeDTO.getName());
        degree.setDescription(degreeDTO.getDescription());
        genericService.saveDegree(degree);
        return degree;
    }

    /**
     * Delete an existing degree.
     *
     * @param degreeId id of an existing degree.
     * @throws EntityNotFoundException when degree with given id is not found.
     */
    @DeleteMapping("delete/{degreeId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDegree(@PathVariable("degreeId") Long degreeId) throws EntityNotFoundException {
        Degree degree = genericService.getDegreeById(degreeId);
        genericService.deleteDegree(degree);
    }
}
