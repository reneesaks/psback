package com.perfectstrangers.controller.closed;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Restaurant;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/private")
public class ResourceController {

    @Autowired
    private GenericService genericService;

    @RequestMapping(value ="hotels", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<Hotel> getHotels(){
        return genericService.getAllHotels();
    }

    @RequestMapping(value ="users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return genericService.getAllUsers();
    }

    // Custom endpoints testing (giving away only some information)
    @RequestMapping(value ="test1", method = RequestMethod.GET)
    public Map<String, Object> getUserByEmail() {

        User user = genericService.getUserByEmail("standard@user.com");
        Map<String, Object> testMap = new HashMap<>();

        testMap.put("id", user.getId());
        testMap.put("firstname", user.getFirstName());
        testMap.put("gender", user.getGender());

        return testMap;

    }

    // Getting a restaurant with hotel test
    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public Restaurant getRestaraunt() {

        Restaurant restaurant = genericService.getRestaurantByName("FancyResto");
        // System.out.println(restaurant.getHotel().getName());

        return restaurant;

    }

}
