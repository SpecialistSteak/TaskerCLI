package org.specialistSteak.dataType;

public enum AnsiColor {
    //supported colors as enum values
    WHITE("white"),
    YELLOW("yellow"),
    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    BLACK("black"),
    DEFAULT("default");

    private final String color;

    //constructor
    AnsiColor(String color) {
        this.color = color;
    }

    //getter
    public String getColor() {
        return color;
    }

    //method to use the enum val to color text
    public static String ansiEscape(Task task, String str) {
        if(task.getAnsiColor() == AnsiColor.DEFAULT) {
            return str;
        }
        return "@|" + task.getAnsiColor().getColor() + " " + str + " |@";
    }

    //method to use the enum val to color Integer.toString() text
    public static String ansiEscape(Task task, int value) {
        if(task.getAnsiColor() == AnsiColor.DEFAULT) {
            return String.valueOf(value);
        }
        return "@|" + task.getAnsiColor().getColor() + " " + Integer.toString(value) + " |@";
    }

    //method to check if the user input is a valid color
    @Deprecated
    public static boolean isValidAnsiColor(String color) {
        for (AnsiColor c : AnsiColor.values()) {
            if (c.name().equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }
}