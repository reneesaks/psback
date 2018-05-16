package com.professionalstrangers.dto;

import com.professionalstrangers.domain.Degree;
import com.professionalstrangers.domain.Occupation;
import com.professionalstrangers.domain.enums.AgeGroup;
import com.professionalstrangers.domain.enums.Gender;
import javax.validation.constraints.Size;

public class UpdateUserDTO {

    @Size(max = 60)
    private String alias;

    private Gender gender;

    private AgeGroup age;

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

    public AgeGroup getAge() {
        return age;
    }

    public void setAge(AgeGroup age) {
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
