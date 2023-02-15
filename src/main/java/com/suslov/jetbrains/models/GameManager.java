package com.suslov.jetbrains.models;

import java.util.Arrays;
import java.util.Scanner;

import static com.suslov.jetbrains.BullsAndCowsGame.MAX_LENGTH_OF_CODE;
import static com.suslov.jetbrains.models.SecretCode.SYMBOLS;

/**
 * @author Mikhail Suslov
 */
public class GameManager {
    private static final String GRADE_TEMPLATE = "Grade: %s\n";
    private static final String ERROR_INPUT_DATA = "Error: \"%s\" isn't a valid number.\n";
    private static final String INCORRECT_INPUT_DATA =
            "Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n";
    private static final String ERROR_POSSIBLE_SYMBOLS =
            "Error: maximum number of possible symbols in the code is %d (0-9, a-z)\n";

    private Scanner console;
    private SecretCode secretCode;
    private boolean gameOver;

    public void initialize() {
        console = new Scanner(System.in);

        int lengthOfCode = enterInputNumber(MAX_LENGTH_OF_CODE, "the secret code's length");
        int numberOfSymbols = enterInputNumber(SYMBOLS.length(), "the number of possible symbols in the code");

        if (lengthOfCode > numberOfSymbols) {
            System.out.printf(INCORRECT_INPUT_DATA, lengthOfCode, numberOfSymbols);
            return;
        }

        generateSecretCode(lengthOfCode, numberOfSymbols);
    }

    private int enterInputNumber(int maxValue, String valueRepresentation) {
        int value = -1;
        do {
            System.out.printf("Input %s in range [1-%d]:\n", valueRepresentation, maxValue);
            value = checkInputNumber();
        } while (value <= 0 || value > maxValue);

        return value;
    }

    private int checkInputNumber() {
        String nextInt = console.nextLine();
        if (nextInt.matches("^[1-9]\\d*$")) {
            int inputInt = Integer.parseInt(nextInt);
            if (inputInt > SYMBOLS.length()) {
                System.out.printf(ERROR_POSSIBLE_SYMBOLS, SYMBOLS.length());
                return -1;
            }
            return inputInt;
        } else {
            System.out.printf(ERROR_INPUT_DATA, nextInt);
            return -1;
        }
    }

    private void generateSecretCode(int lengthOfCode, int numberOfSymbols) {
        secretCode = new SecretCode(lengthOfCode, numberOfSymbols);
        System.out.printf("The secret is prepared: %s.\n", secretCode.represent());
        System.out.println("Okay, let's start a game!");
    }

    public void start() {
        while (!gameOver) {
            evaluateAnswer(console.next(), secretCode);
        }
        closeGame();
    }

    private void evaluateAnswer(String attempt, SecretCode secretCode) {
        StringBuilder regEx = new StringBuilder();
        regEx.append(secretCode.representPossibleSymbols('[', ']', ""));
        regEx.append('{').append(secretCode.getLengthOfCode()).append('}');
        if (!attempt.matches(regEx.toString())) {
            System.out.printf("You must input valid number. Its length is %d, it must contain only possible symbols %s.\n",
                    secretCode.getLengthOfCode(), secretCode.representPossibleSymbols('(', ')', ", "));
            return;
        }

        char[] cAttempt = attempt.toCharArray();
        char[] cSecretCode = secretCode.getValue().toCharArray();

        if (Arrays.equals(cAttempt, cSecretCode)) {
            System.out.printf(GRADE_TEMPLATE, cSecretCode.length + " bull(s)");
            gameOver = true;
            return;
        }

        checkInputData(cAttempt, cSecretCode);
    }

    private void checkInputData(char[] cAttempt, char[] cSecretCode) {
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < cAttempt.length; i++) {
            for (int j = 0; j < cSecretCode.length; j++) {
                if (cAttempt[i] == cSecretCode[j]) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }

        System.out.printf(GRADE_TEMPLATE, representGrade(cows, bulls));
    }

    private String representGrade(int cows, int bulls) {
        String grade = "";
        if (cows == 0 && bulls == 0) {
            grade = "None";
        } else if (cows == 0) {
            grade = bulls + " bull(s)";
        } else if (bulls == 0) {
            grade = cows + " cow(s)";
        } else {
            grade = bulls + " bull(s) and " + cows + " cow(s)";
        }

        return grade;
    }

    private void closeGame() {
        System.out.println("Congratulations!");
        console.close();
    }
}