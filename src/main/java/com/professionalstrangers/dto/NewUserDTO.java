package com.professionalstrangers.dto;

import com.professionalstrangers.validation.ValidEmail;
import com.professionalstrangers.validation.ValidPassword;
import javax.validation.constraints.NotNull;

/**
 * Used to validate new users.
 */
public class NewUserDTO {

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
