package com.professionalstrangers.dto;

import com.professionalstrangers.domain.Hotel;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RestoDTO {

    @NotNull
    @Size(max = 120)
    private String name;

    @NotNull
    @Size(max = 120)
    private String webpage;

    @NotNull
    @Size(max = 240)
    private String country;

    @NotNull
    @Size(max = 240)
    private String state;

    @NotNull
    @Size(max = 240)
    private String city;

    @NotNull
    @Size(max = 240)
    private String address;

    @NotNull
    @Size(max = 240)
    private String zipCode;

    @NotNull
    private Hotel hotel;

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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
