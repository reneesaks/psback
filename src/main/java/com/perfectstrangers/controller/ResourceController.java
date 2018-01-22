package com.perfectstrangers.controller;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResourceController {
    @Autowired
    private com.perfectstrangers.service.GenericService userService;

    @RequestMapping(value ="private/hotels", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<Hotel> getHotels(){
        return userService.findAllHotels();
    }

    @RequestMapping(value ="private/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @RequestMapping(value ="public/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello!";
    }
}
