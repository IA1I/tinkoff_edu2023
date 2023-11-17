package edu.hw5;

public class Task5 {
    private static final String REGEX_FOR_NUMBER_PLATE = "^[ABEKMHOPCTYX]\\d{3}[ABEKMHOPCTYX]{2}\\d{2,3}$";

    private Task5() {
    }

    public static boolean isRegexMatches(String password) {
        if (password == null) {
            return false;
        }
        return password.matches(REGEX_FOR_NUMBER_PLATE);
    }
}
