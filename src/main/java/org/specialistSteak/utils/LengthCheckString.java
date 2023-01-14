package org.specialistSteak.utils;

public class LengthCheckString {
    public static String checkLength(String input) {
        if (input.length() > 20) {
            System.out.println("Input too long. Truncating to 20 characters.");
            input = input.substring(0, 20);
            System.out.println("New string: " + input);
        }
        return input;
    }
    public static String checkAPILength(String input) {
        if (input.length() > 65) {
            input = input.substring(0, 65);
            System.out.println("Input too long. Truncating to 65 characters.");
            System.out.println("New string: " + input);
            return input;
        } else {
            return input;
        }
    }
}
