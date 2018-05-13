package com.professionalstrangers.dto;

import com.professionalstrangers.validation.ValidEmail;
import javax.validation.constraints.NotNull;

/**
 * Used to validate only the username.
 */
public class UsernameDTO {

    @NotNull
    @ValidEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
