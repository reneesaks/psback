package com.professionalstrangers.domain.enums;

public enum AgeGroup {

    YOUNG("Young"),
    ADULT("Adult"),
    SENIOR("Senior");

    private String value;

    AgeGroup(String value) {
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
