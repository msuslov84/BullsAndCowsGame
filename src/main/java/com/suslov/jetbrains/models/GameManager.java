package com.suslov.jetbrains.models;

import com.suslov.jetbrains.utils.MessageUtil;

import java.util.Arrays;
import java.util.Scanner;

import static com.suslov.jetbrains.BullsAndCowsGame.MAX_LENGTH_OF_CODE;
import static com.suslov.jetbrains.utils.MessageUtil.*;
import static com.suslov.jetbrains.models.SecretCode.SYMBOLS;

/**
 * @author Mikhail Suslov
 */
public class GameManager {
    private Scanner console;
    private SecretCode secretCode;
    private boolean gameOver;

    public void initialize() {
        console = new Scanner(System.in);

        int lengthOfCode = enterInputNumber(MAX_LENGTH_OF_CODE, "the secret code's length");
        int numberOfSymbols = enterInputNumber(SYMBOLS.length(), "the number of possible symbols in the code");

        if (lengthOfCode > numberOfSymbols) {
            MessageUtil.toConsole(INCORRECT_INPUT_DATA, lengthOfCode, numberOfSymbols);
            return;
        }

        generateSecretCode(lengthOfCode, numberOfSymbols);
    }

    private int enterInputNumber(int maxValue, String valueRepresentation) {
        int value;
        do {
            MessageUtil.toConsole("Input %s in range [1-%d]:\n", valueRepresentation, maxValue);
            value = checkValidInputNumber();
        } while (!checkInputValueInRange(value, maxValue));

        return value;
    }

    private int checkValidInputNumber() {
        String nextInt = console.nextLine();
        if (nextInt.matches("^[1-9]\\d*$")) {
            int inputInt = Integer.parseInt(nextInt);
            if (inputInt > SYMBOLS.length()) {
                MessageUtil.toConsole(ERROR_POSSIBLE_SYMBOLS, SYMBOLS.length());
                return -1;
            }
            return inputInt;
        } else {
            MessageUtil.toConsole(ERROR_INPUT_DATA, nextInt);
            return -1;
        }
    }

    private boolean checkInputValueInRange(int value, int maxValue) {
        boolean isCorrect = value > 0 && value <= maxValue;
        if (!isCorrect && value != -1) {
            MessageUtil.toConsole(ERROR_INPUT_DATA, String.valueOf(value));
        }
        return isCorrect;
    }

    private void generateSecretCode(int lengthOfCode, int numberOfSymbols) {
        secretCode = new SecretCode(lengthOfCode, numberOfSymbols);
        MessageUtil.toConsole("The secret is prepared: %s.\n", secretCode.represent());
        MessageUtil.toConsole("Okay, let's start a game!");
    }

    public void start() {
        while (!gameOver) {
            evaluateAnswer(console.next(), secretCode);
        }
        closeGame();
    }

    private void evaluateAnswer(String attempt, SecretCode secretCode) {
        if (attempt.equals("exit")) {
            gameOver = true;
            MessageUtil.toConsole("Game over!");
            MessageUtil.toConsole("Secret code was: %s.\n", secretCode.getValue());
            return;
        }
        StringBuilder regEx = new StringBuilder();
        regEx.append(secretCode.representPossibleSymbols('[', ']', ""));
        regEx.append('{').append(secretCode.getLengthOfCode()).append('}');
        if (!attempt.matches(regEx.toString())) {
            MessageUtil.toConsole("You must input valid number. Its length is %2$d, it must contain only possible symbols %1$s.\n",
                    secretCode.representPossibleSymbols('(', ')', ", "), secretCode.getLengthOfCode());
            return;
        }

        char[] cAttempt = attempt.toCharArray();
        char[] cSecretCode = secretCode.getValue().toCharArray();

        if (Arrays.equals(cAttempt, cSecretCode)) {
            MessageUtil.toConsole(GRADE_TEMPLATE, cSecretCode.length + " bull(s)");
            gameOver = true;
            MessageUtil.toConsole("Congratulations!");
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

        MessageUtil.toConsole(GRADE_TEMPLATE, representGrade(cows, bulls));
    }

    private String representGrade(int cows, int bulls) {
        String grade;
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
        console.close();
    }
}