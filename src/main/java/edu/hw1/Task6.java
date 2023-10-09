package edu.hw1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Task6 {
    public static final int UPPER_LIMIT = 10000;
    public static final int BOTTOM_LIMIT = 1000;
    public static final int WRONG_NUMBER_OF_DIGITS = 1;
    public static final int KAPREKAR_CONST = 6174;

    private Task6() {
    }

    public static int countK(int number) {
        checkInput(number);
        int countK = recursiveCountK(number, 0);

        return countK;
    }

    private static void checkInput(int number) {
        if (number <= BOTTOM_LIMIT || number >= UPPER_LIMIT) {
            throw new IllegalArgumentException();
        }
        char[] stringNumber = String.valueOf(number).toCharArray();
        Set<Character> digits = new HashSet<>();
        for (char c : stringNumber) {
            digits.add(c);
        }
        if (digits.size() == WRONG_NUMBER_OF_DIGITS) {
            throw new IllegalArgumentException();
        }
    }

    private static int recursiveCountK(int number, int countK) {
        if (number == KAPREKAR_CONST) {
            return countK;
        }
        int maxNumber = getMaxNumber(number);
        int minNumber = getMinNumber(number);

        return recursiveCountK(maxNumber - minNumber, countK + 1);
    }

    private static int getMaxNumber(int number) {
        int[] arrayNumber = new int[] {number % 10, number / 10 % 10, number / 100 % 10, number / 1000};
        Arrays.sort(arrayNumber);
        int maxNumber = arrayNumber[3] * 1000 + arrayNumber[2] * 100 + arrayNumber[1] * 10 + arrayNumber[0];

        return maxNumber;
    }

    private static int getMinNumber(int number) {
        int[] arrayNumber = new int[] {number % 10, number / 10 % 10, number / 100 % 10, number / 1000};
        Arrays.sort(arrayNumber);
        int minNumber = arrayNumber[0] * 1000 + arrayNumber[1] * 100 + arrayNumber[2] * 10 + arrayNumber[3];

        return minNumber;
    }
}
