package edu.hw1;

public class Task4 {
    private Task4() {
    }

    public static String fixString(String incorrectString) {
        if (incorrectString == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder correctString = new StringBuilder(incorrectString);
        for (int i = 0; i < correctString.length() - 1; i += 2) {
            char tmp = correctString.charAt(i);
            correctString.setCharAt(i, correctString.charAt(i + 1));
            correctString.setCharAt(i + 1, tmp);
        }

        return correctString.toString();
    }
}
