package com.perfectstrangers.dto;

import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.Resto;
import com.perfectstrangers.domain.enums.AdvertStatus;
import com.perfectstrangers.domain.enums.MealType;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// TODO: Add more logic here (validation etc)
public class AdvertDTO {

    private AdvertStatus advertStatus;

    @NotNull
    @Size(max = 140)
    private String advertText;

    @NotNull
    private MealType mealType;

    @NotNull
    @Size(max = 50)
    private String preferredStart;

    @NotNull
    @Size(max = 50)
    private String preferredEnd;

    @NotNull
    private List<Resto> restos;

    @NotNull
    private List<Hotel> hotels;

    public AdvertStatus getAdvertStatus() {
        return advertStatus;
    }

    public void setAdvertStatus(AdvertStatus advertStatus) {
        this.advertStatus = advertStatus;
    }

    public String getAdvertText() {
        return advertText;
    }

    public void setAdvertText(String advertText) {
        this.advertText = advertText;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getPreferredStart() {
        return preferredStart;
    }

    public void setPreferredStart(String preferredStart) {
        this.preferredStart = preferredStart;
    }

    public String getPreferredEnd() {
        return preferredEnd;
    }

    public void setPreferredEnd(String preferredEnd) {
        this.preferredEnd = preferredEnd;
    }

    public List<Resto> getRestos() {
        return restos;
    }

    public void setRestos(List<Resto> restos) {
        this.restos = restos;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
