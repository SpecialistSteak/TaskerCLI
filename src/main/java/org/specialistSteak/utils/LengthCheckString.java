package org.specialistSteak.utils;

public class LengthCheckString {
    /**
     * Check if a string is too long.
     * @param input the input to check.
     * @return the input if it is not too long or the trimmed input if it is.
     */
    public static String checkLength(String input) {
        if (input.length() > 20) {
            System.out.println("Input too long. Truncating to 20 characters.");
            input = input.substring(0, 20);
            System.out.println("New string: " + input);
        }
        return input;
    }

    /**
     * Check if a string is for the API key is too long.
     * @param input the input to check.
     * @return the input if it is not too long or the trimmed input if it is.
     */
    public static String checkAPILength(String input) {
        if (input.length() > 65) {
            input = input.substring(0, 65);
            System.out.println("Input too long. Truncating to 65 characters.");
            System.out.println("New string: " + input);
            return input;
        }
        return input;
    }
}
