package com.suslov.jetbrains.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mikhail Suslov
 */
public class SecretCodeTest {
    private SecretCode secretCode;

    @Before
    public void setUp() {
        secretCode = new SecretCode(3, 12);
    }

    @Test
    public void generateCodeWithCorrectLength() {
        secretCode.generate();
        Assert.assertEquals(3, secretCode.getValue().length());
    }

    @Test
    public void representOnlyDigits() {
        secretCode = new SecretCode(4, 8);
        Assert.assertEquals("**** (0-7)", secretCode.represent());
    }

    @Test
    public void representWithCharacters() {
        Assert.assertEquals("*** (0-9, a-b)", secretCode.represent());
    }

    @Test
    public void RepresentPossibleSymbolsOnlyDigits() {
        secretCode = new SecretCode(3, 8);

        char parentheseOpen = '(';
        char parentheseClose = ')';
        String separator = "; ";

        String expected = "(0-7)";

        Assert.assertEquals(expected, secretCode.representPossibleSymbols(parentheseOpen, parentheseClose, separator));
    }

    @Test
    public void RepresentPossibleSymbolsWithoutStartText() {
        char parentheseOpen = '(';
        char parentheseClose = ')';
        String separator = "; ";

        String expected = "(0-9; a-b)";

        Assert.assertEquals(expected, secretCode.representPossibleSymbols(parentheseOpen, parentheseClose, separator));
    }

    @Test
    public void RepresentPossibleSymbolsWithStartText() {
        StringBuilder str = new StringBuilder("Test ");
        char parentheseOpen = '{';
        char parentheseClose = '}';
        String separator = ", ";

        String expected = "Test {0-9, a-b}";

        Assert.assertEquals(expected, secretCode.representPossibleSymbols(str, parentheseOpen, parentheseClose, separator));
    }
}