package edu.hw5;

public class Task7 {
    private Task7() {
    }

    public static boolean isContainsAtLeast3CharactersWithThird0(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^([01][01]0)([01]*)$");
    }

    public static boolean isStartsAndEndsWithSameCharacter(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^([01])[01]*\\1$|^[01]$");
    }

    public static boolean isLengthBetween1And3(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^[01]{1,3}$");
    }

    private static boolean isInputNull(String input) {
        return input == null;
    }
}
