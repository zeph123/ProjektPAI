package com.example.projekt.enums;

public enum UserRole {

    ROLE_ADMINISTRATOR(1, "Administrator"),
    ROLE_USER(2, "Użytkownik");

    private static final UserRole[] VALUES = values();
    private final int value;
    private final String name;

    private UserRole(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int value() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.value + " " + this.name();
    }

    public static UserRole valueOf(int value) {
        return VALUES[value];
    }

}
