package com.perfectstrangers.dto;

import com.perfectstrangers.domain.enums.ResponseStatus;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResponseDTO {

    @NotNull
    @Size(max = 140)
    private String responseText;

    @NotNull
    @Size(max = 50)
    private String proposedTime;

    private ResponseStatus responseStatus;

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
}
