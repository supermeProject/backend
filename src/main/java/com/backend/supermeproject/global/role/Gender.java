package com.backend.supermeproject.global.role;

public enum Gender {
    Male("남성"),
    Female("여성");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
