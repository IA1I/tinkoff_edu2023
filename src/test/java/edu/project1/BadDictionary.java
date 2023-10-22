package edu.project1;

import edu.project1.dictionaries.Dictionary;
import org.jetbrains.annotations.NotNull;

public class BadDictionary implements Dictionary {
    private final String word;

    public BadDictionary(String word) {
        this.word = word;
    }

    @Override
    public @NotNull String randomWord() {
        return word;
    }
}
