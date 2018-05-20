package com.professionalstrangers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.professionalstrangers.domain.enums.InvitationStatus;
import com.professionalstrangers.domain.enums.MealType;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "invitation_status")
    private InvitationStatus invitationStatus;

    @Column(name = "created_date")
    private String createdDate;

    @Lob
    @Column(name = "invitation_text")
    private String invitationText;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;

    @Column(name = "preferred_start")
    private String preferredStart;

    @Column(name = "preferred_end")
    private String preferredEnd;

    @Column(name = "accepted_time")
    private String acceptedTime;

    @ManyToMany
    @JoinTable(
            name = "invitation_resto",
            joinColumns = @JoinColumn(name = "invitation_id"),
            inverseJoinColumns = @JoinColumn(name = "resto_id")
    )
    @JsonIgnoreProperties({"hotel"})
    private List<Resto> restos;

    @ManyToMany
    @JoinTable(
            name = "invitation_hotel",
            joinColumns = @JoinColumn(name = "invitation_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    @JsonIgnoreProperties({ "restos" })
    private List<Hotel> hotels;

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "invitation" })
    private List<Response> responses;

    @ManyToOne
    @JoinTable(
            name = "invitation_user",
            joinColumns = @JoinColumn(name = "invitation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({ "email", "activated", "regDate", "lastVisit" })
    private User user;

    public Invitation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(String acceptedTime) {
        this.acceptedTime = acceptedTime;
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

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
