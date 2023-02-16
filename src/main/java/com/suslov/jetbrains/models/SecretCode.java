package com.suslov.jetbrains.models;

import java.util.Random;

/**
 * @author Mikhail Suslov
 */
public class SecretCode {
    protected static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private final int lengthOfCode;
    private final int numberOfSymbols;
    private String value;

    public SecretCode(int lengthOfCode, int numberOfSymbols) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfSymbols = numberOfSymbols;

        generate();
    }

    public String getValue() {
        return value;
    }

    public int getLengthOfCode() {
        return lengthOfCode;
    }

    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

    protected void generate() {
        StringBuilder str = new StringBuilder();
        while (str.length() < lengthOfCode) {
            int nextInt = new Random().nextInt(numberOfSymbols);
            char symbol = SYMBOLS.charAt(nextInt);
            if (str.indexOf(String.valueOf(symbol)) < 0) {
                str.append(symbol);
            }
        }

        value = str.toString();
    }

    public String represent() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < lengthOfCode; i++) {
            str.append("*");
        }
        str.append(' ');
        return representPossibleSymbols(str, '(', ')', ", ");
    }

    public String representPossibleSymbols(char parenthesesOpen, char parenthesesClose, String separator) {
        return representPossibleSymbols(new StringBuilder(), parenthesesOpen, parenthesesClose, separator);
    }

    public String representPossibleSymbols(StringBuilder str, char parenthesesOpen, char parenthesesClose, String separator) {
        str.append(parenthesesOpen);
        str.append(SYMBOLS.charAt(0));
        if (numberOfSymbols > 10) {
            str.append('-').append(SYMBOLS.charAt(9)).append(separator).append(SYMBOLS.charAt(10));
        }
        str.append('-').append(SYMBOLS.charAt(numberOfSymbols - 1));
        str.append(parenthesesClose);

        return str.toString();
    }
}
