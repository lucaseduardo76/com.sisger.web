package com.sisger.demo.task.domain;


import lombok.Getter;

@Getter
public enum StatusRole {
    NOT_INITIALIZED(0, "Not Initialized", "The task has not started yet."),
    IN_PROGRESS(1, "In Progress", "The task is currently being worked on."),
    PAUSED(2, "Paused", "The task is temporarily on hold."),
    LATE(3, "Late", "The task has missed its deadline."),
    FINISHED(4, "Finished", "The task has been completed.");

    private final int index;
    private final String displayName;
    private final String description;

    StatusRole(int index, String displayName, String description) {
        this.index = index;
        this.displayName = displayName;
        this.description = description;
    }

    public static StatusRole fromIndex(int index) {
        for (StatusRole status : values()) {
            if (status.getIndex() == index) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid index: " + index);
    }

    public static StatusRole fromDisplayName(String displayName) {
        for (StatusRole status : values()) {
            if (status.getDisplayName().equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid display name: " + displayName);
    }
}
