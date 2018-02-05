package com.perfectstrangers.domain.enums;

public enum MealType {

    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner");

    private String value;

    MealType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static MealType fromValue(String value) {
        for (MealType mealType :MealType.values()){
            if (mealType.getValue().equals(value)){
                return mealType;
            }
        }
        throw new UnsupportedOperationException(
                "Value " + value + " is not supported!");
    }

}
