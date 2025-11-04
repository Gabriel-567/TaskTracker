package model;

/**
 * Represents the current state of a Task.
 * Each status value corresponds to a specific stage in the task lifecycle.
 */
public enum Status {
    TODO("Waiting for the start"),
    IN_PROGRESS("The task is in progress."),
    COMPLETED("The task has been completed."),
    ARCHIVED("The task has been archived.");

    private final String description;

    private Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
