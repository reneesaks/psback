package com.perfectstrangers.domain.enums;

public enum Gender {

    MALE("M"),
    FEMALE("F");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender fromValue(String value) {
        for (Gender status :Gender.values()){
            if (status.getValue().equals(value)){
                return status;
            }
        }
        throw new UnsupportedOperationException(
                "Value " + value + " is not supported!");
    }

}
