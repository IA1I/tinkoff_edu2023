package edu.hw1;

public class Task2 {
    public static final int ZERO = 0;
    public static final int INITIAL_VALUE = 0;
    public static final int BASE = 10;

    private Task2() {
    }

    public static int countDigits(int number) {
        int positiveNumber = Math.abs(number);
        int numberOfDigits = INITIAL_VALUE;
        do {
            numberOfDigits++;
            positiveNumber /= BASE;
        } while (positiveNumber > ZERO);

        return numberOfDigits;
    }
}
