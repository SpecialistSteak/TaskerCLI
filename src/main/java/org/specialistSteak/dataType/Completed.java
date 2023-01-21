package org.specialistSteak.dataType;

public enum Completed {
    completed,
    notStarted,
    inProgress,
    waitingOnOthers,
    deferred;

    /**
     * Get the completion status as a string.
     * @return the completion status as a string
     */
    public String toString() {
        return switch (this) {
            case completed -> "Completed";
            case inProgress -> "In Progress";
            case waitingOnOthers -> "Waiting On Others";
            case deferred -> "Deferred";
            default -> "Not Started";
        };
    }

    /**
     * @param input the input to check.
     * @return if the input is completed or incomplete.
     */
    public static Boolean returnCompletedStatus(Completed input) {
        return input == completed;
    }
}
