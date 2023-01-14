package org.specialistSteak.dataType;

public enum Importance {
    low,
    normal,
    high;

    //"getters" and "setters"
    public static Importance getImportance(String importance) {
        return switch (importance) {
            case "low" -> low;
            case "normal" -> normal;
            case "high" -> high;
            default -> null;
        };
    }

    public static Integer importanceToInteger(Importance importance) {
        return switch (importance) {
            case low -> 1;
            case normal -> 2;
            case high -> 3;
            default -> null;
        };
    }

    public static String importanceToString(Importance importance) {
        return switch (importance) {
            case low -> "low";
            case normal -> "normal";
            case high -> "high";
            default -> null;
        };
    }
    public static void setImportance(Importance importance) {
        switch (importance) {
            case low -> System.out.println("low");
            case normal -> System.out.println("normal");
            case high -> System.out.println("high");
        }
    }
}
