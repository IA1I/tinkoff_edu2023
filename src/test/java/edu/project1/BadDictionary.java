package edu.project1;

import edu.project1.dictionaries.Dictionary;
import org.jetbrains.annotations.NotNull;

public class BadDictionary implements Dictionary {
    private static final String WORD_PATTERN = "[a-z]{2,}";
    private final String word;

    public BadDictionary(String word) {
        this.word = word;
    }

    @Override
    public @NotNull String randomWord() {
        if (word == null || !word.matches(WORD_PATTERN)) {
            throw new IllegalArgumentException();
        }
        return word;
    }
}
