package model;

public enum Priority {
    LOW("Low Priority"),
    NORMAL("Normal Priority"),
    HIGH("High Priority"),
    URGENT("Urgent Task");

    private final String label;

    private Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
