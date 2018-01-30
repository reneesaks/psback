package com.perfectstrangers.controller.closed;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/private")
public class ResourceController {

    @Autowired
    private com.perfectstrangers.service.GenericService userService;

    @RequestMapping(value ="hotels", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<Hotel> getHotels(){
        return userService.findAllHotels();
    }

    @RequestMapping(value ="users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

}
