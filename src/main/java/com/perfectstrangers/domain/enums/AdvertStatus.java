package com.perfectstrangers.domain.enums;

public enum AdvertStatus {

    ACCEPTED("Accepted"),
    NOT_ACCEPTED("NotAccepted");

    private String value;

    AdvertStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static AdvertStatus fromValue(String value) {

        for (AdvertStatus advertStatus : AdvertStatus.values()) {
            if (advertStatus.getValue().equals(value)) {
                return advertStatus;
            }
        }
        throw new UnsupportedOperationException("Value " + value + " is not supported!");
    }
}
