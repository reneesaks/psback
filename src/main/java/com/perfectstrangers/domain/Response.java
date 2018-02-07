package com.perfectstrangers.domain;

import com.perfectstrangers.domain.enums.ResponseStatus;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "response")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "response_text")
    private String responseText;

    @Column(name = "proposed_time", columnDefinition = "DATETIME")
    private Instant proposedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_status")
    private ResponseStatus responseStatus;

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

    public Instant getProposedTime() {
        return proposedTime;
    }

    public void setProposedTime(Instant proposedTime) {
        this.proposedTime = proposedTime;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}