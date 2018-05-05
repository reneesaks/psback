package com.perfectstrangers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.perfectstrangers.domain.enums.AdvertStatus;
import com.perfectstrangers.domain.enums.MealType;
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
@Table(name = "advert")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "advert_status")
    private AdvertStatus advertStatus;

    @Column(name = "created_date")
    private String createdDate;

    @Lob
    @Column(name = "advert_text")
    private String advertText;

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
            name = "advert_resto",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "resto_id")
    )
    @JsonIgnoreProperties({"hotel"})
    private List<Resto> restos;

    @ManyToMany
    @JoinTable(
            name = "advert_hotel",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    @JsonIgnoreProperties({ "restos" })
    private List<Hotel> hotels;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Response> responses;

    @ManyToOne
    @JoinTable(
            name = "advert_user",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({ "email", "activated", "regDate", "lastVisit" })
    private User user;

    public Advert() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdvertStatus getAdvertStatus() {
        return advertStatus;
    }

    public void setAdvertStatus(AdvertStatus advertStatus) {
        this.advertStatus = advertStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
