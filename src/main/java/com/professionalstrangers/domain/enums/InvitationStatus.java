package com.professionalstrangers.domain.enums;

public enum InvitationStatus {

    ACCEPTED("Accepted"),
    NOT_ACCEPTED("NotAccepted");

    private String value;

    InvitationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static InvitationStatus fromValue(String value) {

        for (InvitationStatus invitationStatus : InvitationStatus.values()) {
            if (invitationStatus.getValue().equals(value)) {
                return invitationStatus;
            }
        }
        throw new UnsupportedOperationException("Value " + value + " is not supported!");
    }
}
