package com.perfectstrangers.dto;

import com.perfectstrangers.domain.Degree;
import com.perfectstrangers.domain.Occupation;
import com.perfectstrangers.domain.enums.Gender;
import java.util.List;

public class EditUserDTO {

    private String firstName;

    private String lastName;

    private String alias;

    private Gender gender;

    private String age;

    private List<Degree> degree;

    private List<Occupation> occupation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public List<Degree> getDegree() {
        return degree;
    }

    public void setDegree(List<Degree> degree) {
        this.degree = degree;
    }

    public List<Occupation> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<Occupation> occupation) {
        this.occupation = occupation;
    }
}
