package com.perfectstrangers.dto;

import com.perfectstrangers.validation.ValidEmail;
import javax.validation.constraints.NotNull;

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
