package edu.hw1;

public class Task5 {
    public static final int MIN_LENGTH_NUMBER = 2;

    private Task5() {
    }

    public static boolean isPalindromeDescendant(int number) {
        StringBuilder stringNumber = new StringBuilder(String.valueOf(number));
        do {
            if (isPalindrome(stringNumber)) {
                return true;
            }
            stringNumber = composeNewNumber(stringNumber);
        } while (stringNumber.length() >= MIN_LENGTH_NUMBER);

        return false;
    }

    private static boolean isPalindrome(StringBuilder stringNumber) {
        int length = stringNumber.length();
        for (int i = 0; i < length / 2; i++) {
            if (stringNumber.charAt(i) != stringNumber.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static StringBuilder composeNewNumber(StringBuilder stringNumber) {
        StringBuilder newNumber = new StringBuilder();
        int length = stringNumber.length();
        for (int i = 0; i < length - 1; i += 2) {
            int numberToAdd = stringNumber.charAt(i) + stringNumber.charAt(i + 1) - 2 * '0';
            newNumber.append(numberToAdd);
        }
        if (stringNumber.length() % 2 == 1) {
            newNumber.append(stringNumber.charAt(length - 1));
        }

        return newNumber;
    }
}
