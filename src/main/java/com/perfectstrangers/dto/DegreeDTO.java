package com.perfectstrangers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DegreeDTO {

    @NotNull
    @Size(max = 90)
    private String name;

    @NotNull
    @Size(max = 240)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
