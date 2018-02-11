package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(value = "users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers() {
        return genericService.getAllUsers();
    }

    // Custom endpoints testing (giving away only some information)
    @GetMapping(value = "test1")
    public Map<String, Object> getUserByEmail() throws EntityNotFoundException {
        User user = genericService.getUserByEmail("standarddd@user.com");
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("id", user.getId());
        testMap.put("firstname", user.getFirstName());
        testMap.put("gender", user.getGender());
        return testMap;
    }

    // Getting hotels with pages (?page=0&size=1) https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting
    @GetMapping(value = "test2")
    public Page<Hotel> getHotelsPagination(Pageable pageable) {
        return genericService.getAllHotelsByPage(pageable);
    }

    // Getting user responses test
    @GetMapping(value = "test3")
    public List<Response> getUserResponses() throws EntityNotFoundException {
        return genericService.getUserById(66L).getResponses();
    }
}
