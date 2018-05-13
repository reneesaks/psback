package com.professionalstrangers.dto;

import com.professionalstrangers.validation.ValidPassword;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordResetDTO {

    @NotNull
    private Long id;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    @Size(max = 240)
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
