package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private GenericService genericService;

    @Autowired
    public ResourceController(GenericService genericService) {
        this.genericService = genericService;
    }

    @RequestMapping(value = "hotels", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<Hotel> getHotels() {
        return genericService.getAllHotels();
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers() {
        return genericService.getAllUsers();
    }

    // Custom endpoints testing (giving away only some information)
    @RequestMapping(value = "test1", method = RequestMethod.GET)
    public Map<String, Object> getUserByEmail() {
        User user = genericService.getUserByEmail("standard@user.com");
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("id", user.getId());
        testMap.put("firstname", user.getFirstName());
        testMap.put("gender", user.getGender());
        return testMap;
    }

    // Getting hotels with pages (?page=0&size=1) https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting
    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public Page<Hotel> getHotelsPagination(Pageable pageable) {
        return genericService.getAllHotelsByPage(pageable);
    }

    // Getting user responses test
    @RequestMapping(value = "test3", method = RequestMethod.GET)
    public List<Response> getUserResponses() {
        return genericService.getUserById(2L).getResponses();
    }
}
