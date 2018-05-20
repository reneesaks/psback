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

    public static AgeGroup fromValue(String value) {

        for (AgeGroup ageGroup : AgeGroup.values()) {
            if (ageGroup.getValue().equals(value)) {
                return ageGroup;
            }
        }
        throw new UnsupportedOperationException("Value " + value + " is not supported!");
    }
}
