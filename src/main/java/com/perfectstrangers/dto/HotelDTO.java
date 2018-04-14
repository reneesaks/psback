package com.perfectstrangers.dto;

import com.perfectstrangers.domain.Resto;
import java.util.List;
import javax.validation.constraints.NotNull;

public class HotelDTO {

    @NotNull
    private String name;

    @NotNull
    private String webpage;

    @NotNull
    private String country;

    @NotNull
    private String state;

    @NotNull
    private String city;

    @NotNull
    private String address;

    @NotNull
    private String zipCode;

    private List<Resto> restos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<Resto> getRestos() {
        return restos;
    }

    public void setRestos(List<Resto> restos) {
        this.restos = restos;
    }
}
