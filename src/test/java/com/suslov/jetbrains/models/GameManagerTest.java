package com.suslov.jetbrains.models;

import com.suslov.jetbrains.utils.MessageUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static com.suslov.jetbrains.BullsAndCowsGame.MAX_LENGTH_OF_CODE;
import static com.suslov.jetbrains.models.SecretCode.SYMBOLS;
import static com.suslov.jetbrains.utils.MessageUtil.ERROR_INPUT_DATA;
import static com.suslov.jetbrains.utils.MessageUtil.ERROR_POSSIBLE_SYMBOLS;
import static org.junit.Assert.*;

/**
 * @author Mikhail Suslov
 */
public class GameManagerTest {
    private GameManager gameManager;

    @Before
    public void setUp() {
        gameManager = new GameManager();
    }

    @Test
    public void representGradeNone() {
        Assert.assertEquals("None", gameManager.representGrade(0, 0));
    }

    @Test
    public void representGradeSomeCows() {
        Assert.assertEquals("2 cow(s)", gameManager.representGrade(2, 0));
    }

    @Test
    public void representGradeSomeBulls() {
        Assert.assertEquals("3 bull(s)", gameManager.representGrade(0, 3));
    }

    @Test
    public void representGradeBullsAndCows() {
        Assert.assertEquals("2 bull(s) and 1 cow(s)", gameManager.representGrade(1, 2));
    }

    @Test
    public void checkInputValueInRange() {
        Assert.assertTrue(gameManager.checkInputValueInRange(MAX_LENGTH_OF_CODE - 1, MAX_LENGTH_OF_CODE));
        Assert.assertFalse(gameManager.checkInputValueInRange(MAX_LENGTH_OF_CODE + 2, MAX_LENGTH_OF_CODE));
    }

    @Test
    public void checkValidInputCorrectNumber() {
        gameManager = new GameManager(new Scanner("4"));
        Assert.assertEquals(4, gameManager.checkValidInputNumber());
    }

    @Test
    public void checkValidInputBigNumber() {
        gameManager = new GameManager(new Scanner("41"));
        Assert.assertEquals(-1, gameManager.checkValidInputNumber());
    }

    @Test
    public void checkValidInputNotNumber() {
        gameManager = new GameManager(new Scanner("forty"));
        Assert.assertEquals(-1, gameManager.checkValidInputNumber());
    }

    @Test
    public void enterInputCorrectNumber() {
        gameManager = new GameManager(new Scanner("4"));
        Assert.assertEquals(4, gameManager.enterInputNumber(MAX_LENGTH_OF_CODE, "testing data"));
    }

    @Test
    public void enterInputIncorrectSequenceNumber() {
        gameManager = new GameManager(new Scanner("two\n-1\n41\n5"));
        Assert.assertEquals(5, gameManager.enterInputNumber(MAX_LENGTH_OF_CODE, "testing data"));
    }

    @After
    public void tearDown() {
        gameManager.closeGame();
    }
}