package com.professionalstrangers.dto;

import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.Resto;
import com.professionalstrangers.domain.enums.InvitationStatus;
import com.professionalstrangers.domain.enums.MealType;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InvitationDTO {

    private InvitationStatus invitationStatus;

    @NotNull
    @Size(max = 140)
    private String invitationText;

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

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getInvitationText() {
        return invitationText;
    }

    public void setInvitationText(String invitationText) {
        this.invitationText = invitationText;
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
