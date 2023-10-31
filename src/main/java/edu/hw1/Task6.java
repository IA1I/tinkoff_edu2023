package edu.hw1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Task6 {
    private static final int UPPER_LIMIT = 10000;
    private static final int BOTTOM_LIMIT = 1000;
    private static final int WRONG_NUMBER_OF_DIGITS = 1;
    private static final int KAPREKAR_CONST = 6174;
    private static final int FIRST_DIGIT = 10;
    private static final int SECOND_DIGIT = 10;
    private static final int THIRD_DIGIT = 100;
    private static final int FOURTH_DIGIT = 1000;
    private static final int LAST_INDEX = 3;

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
        int[] arrayNumber =
            new int[] {number % FIRST_DIGIT, number / SECOND_DIGIT % FIRST_DIGIT, number / THIRD_DIGIT % FIRST_DIGIT,
                number / FOURTH_DIGIT};
        Arrays.sort(arrayNumber);

        return arrayNumber[LAST_INDEX] * FOURTH_DIGIT + arrayNumber[2] * THIRD_DIGIT + arrayNumber[1] * SECOND_DIGIT
            + arrayNumber[0];
    }

    private static int getMinNumber(int number) {
        int[] arrayNumber =
            new int[] {number % FIRST_DIGIT, number / SECOND_DIGIT % FIRST_DIGIT, number / THIRD_DIGIT % FIRST_DIGIT,
                number / FOURTH_DIGIT};
        Arrays.sort(arrayNumber);

        return arrayNumber[0] * FOURTH_DIGIT + arrayNumber[1] * THIRD_DIGIT
            + arrayNumber[2] * SECOND_DIGIT + arrayNumber[LAST_INDEX];
    }
}
