package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.dto.PasswordChangeDTO;
import com.perfectstrangers.dto.UpdateUserDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
import com.perfectstrangers.util.PasswordHasher;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("api/private/user")
public class UserController {

    private GenericService genericService;

    @Autowired
    public UserController(GenericService genericService) {
        this.genericService = genericService;
    }

    /**
     * Gets all users. Has to be an admin.
     *
     * @return user list.
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    public List<User> getUsers() {
        return genericService.getAllUsers();
    }

    /**
     * Get one user by id.
     *
     * @param userId id of an existing user.
     * @return advert object.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @GetMapping(value = "{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("userId") Long userId) throws EntityNotFoundException {
        return genericService.getUserById(userId);
    }

    /**
     * Get logged in user.
     *
     * @return user object
     * @throws EntityNotFoundException when user is not found
     */
    @GetMapping(value = "current-user")
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser() throws EntityNotFoundException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );

        return genericService.getUserById(id);
    }

    /**
     * Get logged in user adverts.
     *
     * @return list of adverts
     * @throws EntityNotFoundException when user with given id is not found
     */
    @GetMapping(value = "adverts")
    @ResponseStatus(HttpStatus.OK)
    public List<Advert> getCurrentUserAdverts() throws EntityNotFoundException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );

        return genericService.getAdvertsByUserId(id);
    }

    /**
     * Get logged in user responses
     *
     * @return responses list
     * @throws EntityNotFoundException when user with given id is not found
     */
    @GetMapping(value = "responses")
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getCurrentUserResponses() throws EntityNotFoundException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        List<Response> responses = genericService.getResponsesByUserId(id);

        for (Response response : responses) {
            response.setUser(null);
        }
        return responses;
    }


    /**
     * Update the current logged in user.
     *
     * @param updateUserDTO user object.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @PutMapping(value = "update")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO) throws EntityNotFoundException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        User user = genericService.getUserById(id);

        user.setAlias(updateUserDTO.getAlias());
        user.setGender(updateUserDTO.getGender());
        user.setAge(updateUserDTO.getAge());
        user.setDegree(updateUserDTO.getDegree());
        user.setOccupation(updateUserDTO.getOccupation());

        genericService.saveUser(user);
        return user;
    }

    /**
     * Change the password of current logged in user.
     *
     * @param passwordChangeDTO new password as object.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @PostMapping(value = "change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid PasswordChangeDTO passwordChangeDTO)
            throws EntityNotFoundException {

        Long id = Long.valueOf(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
        );
        User user = genericService.getUserById(id);
        String password = new PasswordHasher().hashPasswordWithSha256(passwordChangeDTO.getPassword());
        user.setPassword(password);

        genericService.saveUser(user);
    }

    /**
     * Delete the current logged in user.
     *
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @DeleteMapping(value = "delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser() throws EntityNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = genericService.getUserByEmail(email);
        genericService.deleteUser(user);
    }

    /**
     * Delete user by id.
     *
     * @param userId id of an existing user.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @ApiOperation(value = "ADMIN_ONLY")
    public void deleteSpecificUser(@PathVariable("userId") Long userId) throws EntityNotFoundException {
        User user = genericService.getUserById(userId);
        genericService.deleteUser(user);
    }
}