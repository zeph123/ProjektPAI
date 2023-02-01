package com.example.projekt.enums;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

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

    public static UserRole valueOf(int roleId) {
        UserRole role = resolve(roleId);
        if (role == null) {
            throw new IllegalArgumentException("No matching constant for [" + roleId + "]");
        } else {
            return role;
        }
    }

    @Nullable
    public static UserRole resolve(int roleId) {
        UserRole[] var1 = VALUES;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            UserRole role = var1[var3];
            if (role.value == roleId) {
                return role;
            }
        }

        return null;
    }

}
