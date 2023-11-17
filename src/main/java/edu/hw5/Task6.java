package edu.hw5;

public class Task6 {
    private Task6() {
    }

    public static boolean isSSubsequenceOfT(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        String checked = checkStringForRegex(s);
        String regex = "^.*" + checked + "$";
        return t.matches(regex);
    }

    private static String checkStringForRegex(String s) {
        StringBuilder newS = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char symbol = s.charAt(i);
            newS.append("\\Q")
                .append(symbol)
                .append("\\E")
                .append(".*");
        }

        return newS.toString();
    }
}
