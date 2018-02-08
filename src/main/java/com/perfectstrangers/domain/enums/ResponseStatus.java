package com.perfectstrangers.domain.enums;

public enum ResponseStatus {

    DECLINED("Declined"),
    NOT_ANSWERED("NotAnswered"),
    ACCEPTED("Accepted");

    private String value;

    ResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ResponseStatus fromValue(String value) {

        for (ResponseStatus responseStatus : ResponseStatus.values()) {
            if (responseStatus.getValue().equals(value)) {
                return responseStatus;
            }
        }
        throw new UnsupportedOperationException("Value " + value + " is not supported!");
    }
}
