package com.example.projekt.enums;

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
}
