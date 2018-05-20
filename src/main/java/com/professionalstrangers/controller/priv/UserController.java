package com.professionalstrangers.controller.priv;

import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.dto.PasswordChangeDTO;
import com.professionalstrangers.dto.UpdateUserDTO;
import com.professionalstrangers.error.EntityNotFoundException;
import com.professionalstrangers.service.GenericService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(GenericService genericService, PasswordEncoder passwordEncoder) {
        this.genericService = genericService;
        this.passwordEncoder = passwordEncoder;
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
     * @return invitation object.
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
     * @return user object.
     * @throws EntityNotFoundException when user is not found.
     */
    @GetMapping(value = "current-user")
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser() throws EntityNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(authentication.getPrincipal().toString());

        return genericService.getUserById(id);
    }

    /**
     * Get logged in user invitations.
     *
     * @return list of invitation.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @GetMapping(value = "invitations")
    @ResponseStatus(HttpStatus.OK)
    public List<Invitation> getCurrentUserInvitations() throws EntityNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(authentication.getPrincipal().toString());

        return genericService.getInvitationsByUserId(id);
    }

    /**
     * Get logged in user responses.
     *
     * @return responses list.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @GetMapping(value = "responses")
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getCurrentUserResponses() throws EntityNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(authentication.getPrincipal().toString());
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
     * @return updated user object.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @PutMapping(value = "update")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO) throws EntityNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(authentication.getPrincipal().toString());
        User user = genericService.getUserById(id);

        if (updateUserDTO.getAlias() != null) {
            user.setAlias(updateUserDTO.getAlias());
        }

        if (updateUserDTO.getGender() != null) {
            user.setGender(updateUserDTO.getGender());
        }

        if (updateUserDTO.getAge() != null) {
            user.setAge(updateUserDTO.getAge());
        }

        if (updateUserDTO.getDegree() != null) {
            user.setDegree(genericService.getDegreeById(updateUserDTO.getDegree().getId()));
        }

        if (updateUserDTO.getOccupation() != null) {
            user.setOccupation(genericService.getOccupationById(updateUserDTO.getOccupation().getId()));
        }

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf(authentication.getPrincipal().toString());
        User user = genericService.getUserById(id);
        String password = passwordEncoder.encode(passwordChangeDTO.getPassword());

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
        genericService.deleteUser(genericService.getUserByEmail(email));
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
        genericService.deleteUser(genericService.getUserById(userId));
    }
}