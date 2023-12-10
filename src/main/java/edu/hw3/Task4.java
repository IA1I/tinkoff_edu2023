package edu.hw3;

import java.util.NavigableMap;
import java.util.TreeMap;

@SuppressWarnings("MagicNumber")
public class Task4 {

    private static final int LOWER_BOUNDARY = 0;
    private static final int UPPER_BOUNDARY = 4000;

    private static final NavigableMap<Integer, String> CONSTANTS = new TreeMap<>();

    static {
        CONSTANTS.put(1, "I");
        CONSTANTS.put(4, "IV");
        CONSTANTS.put(5, "V");
        CONSTANTS.put(9, "IX");
        CONSTANTS.put(10, "X");
        CONSTANTS.put(40, "XL");
        CONSTANTS.put(50, "L");
        CONSTANTS.put(90, "XC");
        CONSTANTS.put(100, "C");
        CONSTANTS.put(400, "CD");
        CONSTANTS.put(500, "D");
        CONSTANTS.put(900, "CM");
        CONSTANTS.put(1000, "M");
    }

    private Task4() {
    }

    public static String convertToRoman(int number) {
        if (!isNumberInCorrectRange(number)) {
            throw new IllegalArgumentException();
        }

        return getStringBuilder(number).toString();
    }

    private static StringBuilder getStringBuilder(int number) {
        int currentNumber = number;
        StringBuilder romanNumber = new StringBuilder();
        for (var constant : CONSTANTS.descendingKeySet()) {
            while (currentNumber >= constant) {
                currentNumber -= constant;
                romanNumber.append(CONSTANTS.get(constant));
            }
        }
        return romanNumber;
    }

    private static boolean isNumberInCorrectRange(int decimalNumber) {
        return LOWER_BOUNDARY < decimalNumber && decimalNumber < UPPER_BOUNDARY;
    }
}
