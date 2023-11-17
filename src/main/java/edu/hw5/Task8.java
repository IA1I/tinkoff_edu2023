package edu.hw5;

public class Task8 {
    private Task8() {
    }

    public static boolean isOddLength(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^[01]([01][01])*$");
    }

    public static boolean isStartsWith0AndOddOrStartsWith1And(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^0([01][01])*$|^1[01]([01][01])*$");
    }

    public static boolean isQuantity0IsAMultipleOf3(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^(1*01*01*01*)+$");
    }

    public static boolean isAnyStringOtherThan11Or111(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^1$|^10[01]*$|^110[01]*$|^111[01]+$|^0[01]*$");
    }

    public static boolean isEveryOddCharacterIs1(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^([01]1)*[01]?$");
    }

    public static boolean isContainsAtLeastTwo0AndAtMostOne1(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^0+1?0+$|^0{2,}1$|^10{2,}$");
    }

    public static boolean isNoConsecutive1(String input) {
        if (isInputNull(input)) {
            return false;
        }
        return input.matches("^[01]?(0+1?0?)*$");
    }

    private static boolean isInputNull(String input) {
        return input == null;
    }
}

