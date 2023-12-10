package edu.hw3;

import java.util.ArrayList;
import java.util.List;

public class Task2 {

    private static final char OPEN_BRACKET = '(';
    private static final char CLOSE_BRACKET = ')';
    private static final String REGEX_FOR_BRACKETS = "[()]{2,}";

    private Task2() {
    }

    public static List<String> clusterize(String input) {
        if (isInputIncorrect(input)) {
            throw new IllegalArgumentException();
        }

        return clusterize(input.toCharArray());
    }

    private static List<String> clusterize(char[] input) {
        List<String> clusterizedInput = new ArrayList<>();
        int brackets = 0;
        int offset = 0;
        for (int characterIndex = 0; characterIndex < input.length; characterIndex++) {
            if (input[characterIndex] == CLOSE_BRACKET) {
                brackets--;
            } else {
                brackets++;
            }
            if (brackets == 0) {
                clusterizedInput.add(String.valueOf(input, offset, characterIndex + 1 - offset));
                offset = characterIndex + 1;
            }
            if (brackets == -1) {
                throw new IllegalArgumentException();
            }
        }
        if (brackets != 0) {
            throw new IllegalArgumentException();
        }

        return clusterizedInput;
    }

    private static boolean isInputIncorrect(String input) {
        return input == null || !input.matches(REGEX_FOR_BRACKETS);
    }
}
