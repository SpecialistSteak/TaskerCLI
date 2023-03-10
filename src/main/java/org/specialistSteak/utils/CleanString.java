package org.specialistSteak.utils;

import picocli.CommandLine;

public class CleanString {
    /**
     * Clean a string (remove all escape characters).
     * @param input the input to clean.
     * @return the cleaned string.
     */
    public static String cleanString(String input) {
        String original = input;
        input = input.replaceAll("\\\\","");
        if(!(original.matches(input))){
            System.out.println(CommandLine.Help.Ansi.AUTO.string(
                    "@|red Warning:|@ The string you entered contained a backslash. The string has been cleaned."));
            System.out.println("New string: " + input);
        }
        return input;
    }
}