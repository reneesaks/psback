package com.perfectstrangers.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.perfectstrangers.domain.enums.ResponseStatus;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "response")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "response_text")
    private String responseText;

    @Column(name = "proposed_time")
    private String proposedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_status")
    private ResponseStatus responseStatus;

    @ManyToOne
    @JoinTable(
            name = "response_user",
            joinColumns = @JoinColumn(name = "response_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({ "firstName", "lastName", "email", "activated", "regDate", "lastVisit" })
    private User user;

    @ManyToOne
    @JoinTable(
            name = "response_advert",
            joinColumns = @JoinColumn(name = "response_id"),
            inverseJoinColumns = @JoinColumn(name = "advert_id")
    )
    @JsonIgnore
    private Advert advert;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public String getProposedTime() {
        return proposedTime;
    }

    public void setProposedTime(String proposedTime) {
        this.proposedTime = proposedTime;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }
}
