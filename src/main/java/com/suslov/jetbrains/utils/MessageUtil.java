package com.suslov.jetbrains.utils;

/**
 * @author Mikhail Suslov
 */
public class MessageUtil {
    public static final String GRADE_TEMPLATE = "Grade: %s\n";
    public static final String ERROR_INPUT_DATA = "Error: \"%s\" isn't a valid number.\n";
    public static final String INCORRECT_INPUT_DATA =
            "Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n";
    public static final String ERROR_POSSIBLE_SYMBOLS =
            "Error: maximum number of possible symbols in the code is %d (0-9, a-z)\n";

    private MessageUtil() {
    }

    public static void toConsole(String message) {
        System.out.println(message);
    }

    public static void toConsole(String formatMessage, String... param) {
        System.out.printf(formatMessage, param);
    }

    public static void toConsole(String formatMessage, Integer... param) {
        System.out.printf(formatMessage, param);
    }

    public static void toConsole(String formatMessage, String param1, int param2) {
        System.out.printf(formatMessage, param1, param2);
    }
}
