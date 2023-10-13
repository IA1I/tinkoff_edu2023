package edu.hw1;

public class Task7 {
    private static final int ZERO = 0;
    private static final int RADIX = 2;

    private Task7() {
    }

    public static int rotateLeft(int number, int shift) {
        checkInput(number, shift);
        char[] binaryNumber = convertToBinary(number);
        final int NUMBER_LENGTH = binaryNumber.length;
        char[] shiftedBinaryNumber = new char[NUMBER_LENGTH];
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            shiftedBinaryNumber[i] = binaryNumber[(i + shift) % NUMBER_LENGTH];
        }

        return Integer.parseInt(String.valueOf(shiftedBinaryNumber), RADIX);
    }

    public static int rotateRight(int number, int shift) {
        checkInput(number, shift);
        char[] binaryNumber = convertToBinary(number);
        final int NUMBER_LENGTH = binaryNumber.length;
        char[] shiftedBinaryNumber = new char[NUMBER_LENGTH];
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            shiftedBinaryNumber[(i + shift) % NUMBER_LENGTH] = binaryNumber[i];
        }

        return Integer.parseInt(String.valueOf(shiftedBinaryNumber), RADIX);
    }

    private static char[] convertToBinary(int number) {
        return Integer.toBinaryString(number).toCharArray();
    }

    private static void checkInput(int number, int shift) {
        if (number < ZERO || shift < ZERO) {
            throw new IllegalArgumentException();
        }
    }
}
