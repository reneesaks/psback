package com.perfectstrangers.dto;

import com.perfectstrangers.domain.Degree;
import com.perfectstrangers.domain.Occupation;
import com.perfectstrangers.domain.enums.Gender;

public class UpdateUserDTO {

    private String alias;

    private Gender gender;

    private String age;

    private Degree degree;

    private Occupation occupation;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
