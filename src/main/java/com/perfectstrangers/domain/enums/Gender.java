package com.perfectstrangers.domain.enums;

public enum Gender {

    PENDING("P"),
    ACTIVE("A"),
    INACTIVE("I"),
    DELETED("D");

    private String code;

    Status(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Gender fromCode(String code) {
        for (Gender status :Gender.values()){
            if (status.getCode().equals(code)){
                return status;
            }
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }

}
