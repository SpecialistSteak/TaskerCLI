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

    /**
     * Constructor for AnsiColor enum.
     * @param color The color to set.
     */
    AnsiColor(String color) {
        this.color = color;
    }

    /**
     * Get the color as a string.
     * @return the color as a string
     */
    public String getColor() {
        return color;
    }

    /**
     * Applies ANSI escape sequence to the given string according to the ANSI color of the task
     * @param task The task to get the ANSI color from
     * @param str The string to be colored
     * @return The input string with ANSI escape sequence added to it if task color is not default, otherwise returns the input string as is.
     */
    public static String ansiEscape(Task task, String str) {
        if(task.getAnsiColor() == AnsiColor.DEFAULT) {
            return str;
        }
        return "@|" + task.getAnsiColor().getColor() + " " + str + " |@";
    }

    /**
     * Applies ANSI escape sequence to the given integer according to the ANSI color of the task
     * @param task the task to get the ANSI color from
     * @param value the integer value to be colored
     * @return The input integer value with ANSI escape sequence added to it if task color is not default, otherwise returns the input integer as string.
     */
    public static String ansiEscape(Task task, int value) {
        if(task.getAnsiColor() == AnsiColor.DEFAULT) {
            return String.valueOf(value);
        }
        return "@|" + task.getAnsiColor().getColor() + " " + value + " |@";
    }

    /**
     * @param color the color to get the color of.
     * @return a boolean if the color is a valid color.
     */
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