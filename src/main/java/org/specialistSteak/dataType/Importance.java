package org.specialistSteak.dataType;

public enum Importance {
    low,
    normal,
    high;

    /**
     * Get the importance as a string.
     * @param importance the importance to get the number of.
     * @return the importance as a number.
     */
    public static Integer importanceToInteger(Importance importance) {
        return switch (importance) {
            case low -> 1;
            case normal -> 2;
            case high -> 3;
            default -> null;
        };
    }

    /**
     * Get the importance as a string.
     * @param importance the importance to get the String value of.
     * @return the importance as a String.
     */
    public static String importanceToString(Importance importance) {
        return switch (importance) {
            case low -> "low";
            case normal -> "normal";
            case high -> "high";
            default -> null;
        };
    }

    /**
     * Print the importance as a string.
     * @param importance the importance to print.
     */
    public static void printImportance(Importance importance) {
        switch (importance) {
            case low -> System.out.println("low");
            case normal -> System.out.println("normal");
            case high -> System.out.println("high");
        }
    }
}
