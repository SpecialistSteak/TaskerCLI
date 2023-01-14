package org.specialistSteak.dataType;

public enum Completed {
    completed,
    notStarted,
    inProgress,
    waitingOnOthers,
    deferred;

    public static Completed getCompleted(String input) {
        return switch (input) {
            case "completed" -> completed;
            case "notStarted" -> notStarted;
            case "inProgress" -> inProgress;
            case "waitingOnOthers" -> waitingOnOthers;
            case "deferred" -> deferred;
            default -> null;
        };
    }

    public String toString() {
        return switch (this) {
            case completed -> "Completed";
            case notStarted -> "Not Started";
            case inProgress -> "In Progress";
            case waitingOnOthers -> "Waiting On Others";
            case deferred -> "Deferred";
        };
    }

    public static Boolean returnCompletedStatus(Completed input) {
        return input == completed;
    }
}
