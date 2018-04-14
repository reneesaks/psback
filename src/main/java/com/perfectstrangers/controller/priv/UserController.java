package com.perfectstrangers.controller.priv;

import com.perfectstrangers.domain.User;
import com.perfectstrangers.dto.EditUserDTO;
import com.perfectstrangers.dto.PasswordChangeDTO;
import com.perfectstrangers.error.EntityNotFoundException;
import com.perfectstrangers.service.GenericService;
import com.perfectstrangers.util.PasswordHasher;
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
     * @return all users as list.
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers() {
        return genericService.getAllUsers();
    }

    /**
     * Get one user by id.
     *
     * @param id id of an existing user.
     * @return advert object.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("id") Long id) throws EntityNotFoundException {
        return genericService.getUserById(id);
    }

    /**
     * Edit the current logged in user.
     *
     * @param editUserDTO user object.
     * @throws EntityNotFoundException when user with given id is not found.
     */
    @PutMapping(value = "edit")
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@RequestBody @Valid EditUserDTO editUserDTO) throws EntityNotFoundException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = genericService.getUserByEmail(email);

        user.setFirstName(editUserDTO.getFirstName());
        user.setLastName(editUserDTO.getLastName());
        user.setGender(editUserDTO.getGender());
        user.setAge(editUserDTO.getAge());
        user.setDegree(editUserDTO.getDegree());
        user.setOccupation(editUserDTO.getOccupation());

        genericService.saveUser(user);
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

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = genericService.getUserByEmail(email);
        String password = new PasswordHasher().hashPasswordWithSha256(passwordChangeDTO.getPassword());
        user.setPassword(password);

        genericService.saveUser(user);
    }

    /**
     * Deletes the current logged in user.
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
}
