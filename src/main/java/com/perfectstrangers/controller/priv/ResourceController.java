package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.service.GenericService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class ResourceController {

    private GenericService genericService;

    @Autowired
    public ResourceController(GenericService genericService) {
        this.genericService = genericService;
    }

    @GetMapping(value = "hotels")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<Hotel> getHotels() {
        return genericService.getAllHotels();
    }
}
