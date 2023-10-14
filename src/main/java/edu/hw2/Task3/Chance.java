package edu.hw2.Task3;

import java.util.Random;

public class Chance {
    private static final int MAXIMUM_VALUE = 100;
    private static final int THRESHOLD_VALUE = 50;

    public static boolean getChance() {
        Random random = new Random();
        int currentValue = random.nextInt(MAXIMUM_VALUE);
        return currentValue >= THRESHOLD_VALUE;
    }
}
