package com.example.projekt.enums;

import org.springframework.lang.Nullable;

public enum TaskStatus {

    STATUS_TODO(1, "Do zrobienia"),
    STATUS_INPROGRESS(2, "W trakcie"),
    STATUS_DONE(3, "Ukończone");

    private static final TaskStatus[] VALUES = values();
    private final int value;
    private final String name;

    private TaskStatus(int value, String name) {
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

    public static TaskStatus valueOf(int statusId) {
        TaskStatus status = resolve(statusId);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusId + "]");
        } else {
            return status;
        }
    }

    @Nullable
    public static TaskStatus resolve(int statusId) {
        TaskStatus[] var1 = VALUES;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TaskStatus status = var1[var3];
            if (status.value == statusId) {
                return status;
            }
        }

        return null;
    }
}
