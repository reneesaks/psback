package com.professionalstrangers.domain.enums;

public enum Gender {

    MALE("M"),
    FEMALE("F");

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender fromValue(String value) {

        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        throw new UnsupportedOperationException("Value " + value + " is not supported!");
    }
}
