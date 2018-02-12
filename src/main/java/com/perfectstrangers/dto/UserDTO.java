package com.perfectstrangers.dto;

import com.perfectstrangers.validation.ValidEmail;
import com.perfectstrangers.validation.ValidPassword;
import javax.validation.constraints.NotNull;

/**
 * Used to validate new users.
 */
public class UserDTO {

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @ValidPassword
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
