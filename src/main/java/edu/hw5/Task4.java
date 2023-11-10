package edu.hw5;

public class Task4 {

    private static final String REGEX = "^[a-zA-Z0-9]*[~!@#$%^&*|][a-zA-Z0-9]*$";
    private static final String REGEX2 = "[~!@#$%^&*|]";

    private Task4() {
    }

    public static boolean isRegexMatches(String password) {
        if (password == null) {
            return false;
        }
        return password.matches(REGEX);
    }
}
