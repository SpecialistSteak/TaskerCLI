package org.specialistSteak.utils;

public class ErrorStringifer extends Exception {
    /**
     * A switch to turn on and off 'Dev mode'.
     */
    public static boolean devSwitch = false;

    /**
     * Converts the exception to a string and prints it in a user-friendly format.
     * If the devSwitch is true, it will print the full stack trace instead.
     * @param e the exception to convert.
     */
    public static void errorMessager(final Exception e) {
        if (devSwitch) {
            e.printStackTrace();
        } else {
            switch (e.getClass().getSimpleName()) {
                case "FileNotFoundException" -> System.out.println("File not found.");
                case "DirectoryNotFoundException" -> System.out.println("Directory not found.");
                case "IOException" -> {
                    String errorMessage = e.getMessage();
                    if (errorMessage.contains("being used by another process")) {
                        System.out.println("The file is being used by another process.");
                    } else {
                        System.out.println("An error has occurred: " + e.getClass().getSimpleName());
                    }
                }
                case "PermissionDeniedException" ->
                        System.out.println("You may need to use sudo privileges to edit the file.");
                default -> System.out.println("An error has occurred: " + e.getClass().getSimpleName());
            }
        }
    }

    /**
     * Prints the full stack. For dev testing.
     */
    public static void getFullStackTrace() {
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(ste + "\n");
        }
    }

    /**
     * A custom error message for more specific errors.
     * @param s the error message you would like to print.
     */
    public ErrorStringifer(final String s) {
        super(s);
    }
}
