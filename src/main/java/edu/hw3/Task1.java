package edu.hw3;

import java.util.HashMap;
import java.util.Map;

public class Task1 {

    private static final int ASCI_A_LOWER_CASE = 97;
    private static final int ASCI_Z_LOWER_CASE = 122;
    private static final int ASCI_A_UPPER_CASE = 65;
    private static final int ASCI_Z_UPPER_CASE = 90;
    private static final int SHIFT = 25;
    private static final Map<Character, Character> SHIFTED_LETTERS = new HashMap<>();

    static {
        for (int i = 0; i < 26; i++) {
            SHIFTED_LETTERS.put((char) (ASCI_A_UPPER_CASE + i), (char) (ASCI_Z_UPPER_CASE - i));
        }
        for (int i = 0; i < 26; i++) {
            SHIFTED_LETTERS.put((char) (ASCI_A_LOWER_CASE + i), (char) (ASCI_Z_LOWER_CASE - i));
        }
    }

    public static String atbash(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        return encryptWord(word.toCharArray());
    }

    private static String encryptWordWithMap(char[] word) {
        for (int i = 0; i < word.length; i++) {
            if (isThisALetter(word[i])) {
                word[i] = SHIFTED_LETTERS.get(word[i]);
            }
        }

        return String.valueOf(word);
    }

    private static String encryptWord(char[] word) {
        for (int i = 0; i < word.length; i++) {
            if (isThisALetter(word[i])) {
                word[i] = shiftLetter(word[i]);
            }
        }

        return String.valueOf(word);
    }

    private static boolean isThisALetter(char letter) {
        return (ASCI_A_LOWER_CASE <= letter && letter <= ASCI_Z_LOWER_CASE) || (
            ASCI_A_UPPER_CASE <= letter && letter <= ASCI_Z_UPPER_CASE);
    }

    private static char shiftLetter(char letter) {
        if (letter <= ASCI_Z_UPPER_CASE) {
            return (char) (ASCI_Z_UPPER_CASE - (letter - ASCI_A_UPPER_CASE));
        }
        return (char) (ASCI_Z_LOWER_CASE - (letter - ASCI_A_LOWER_CASE));
    }
}
