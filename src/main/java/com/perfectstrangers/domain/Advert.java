package com.perfectstrangers.domain;

import com.perfectstrangers.domain.enums.AdvertStatus;
import com.perfectstrangers.domain.enums.MealType;
import java.time.Instant;
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

    @Column(name = "created_date", columnDefinition = "DATETIME")
    private Instant createdDate; // Timestamp is outdated

    @Lob
    @Column(name = "advert_text")
    private String advertText;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;

    @Column(name = "preferred_start", columnDefinition = "DATETIME")
    private Instant preferredStart;

    @Column(name = "preferred_end", columnDefinition = "DATETIME")
    private Instant preferredEnd;

    @Column(name = "accepted_time", columnDefinition = "DATETIME")
    private Instant acceptedTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "advert_resto",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "resto_id")
    )
    private List<Resto> restos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "advert_hotels",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    private List<Hotel> hotels;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "advert_response",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "response_id")
    )
    private List<Response> responses;

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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
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

    public Instant getPreferredStart() {
        return preferredStart;
    }

    public void setPreferredStart(Instant preferredStart) {
        this.preferredStart = preferredStart;
    }

    public Instant getPreferredEnd() {
        return preferredEnd;
    }

    public void setPreferredEnd(Instant preferredEnd) {
        this.preferredEnd = preferredEnd;
    }

    public Instant getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(Instant acceptedTime) {
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
}
