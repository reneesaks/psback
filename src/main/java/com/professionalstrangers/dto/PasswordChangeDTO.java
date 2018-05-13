package com.professionalstrangers.dto;

import com.professionalstrangers.validation.ValidPassword;
import javax.validation.constraints.NotNull;

public class PasswordChangeDTO {

    @NotNull
    @ValidPassword
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
