package com.suslov.jetbrains;

import com.suslov.jetbrains.models.GameManager;

public class BullsAndCowsGame {
    public static final int MAX_LENGTH_OF_CODE = 9;

    public static void main(String[] args) {
        GameManager newGame = new GameManager();
        newGame.initialize();
        newGame.start();
    }
}
