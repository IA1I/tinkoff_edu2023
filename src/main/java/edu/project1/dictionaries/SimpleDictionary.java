package edu.project1.dictionaries;

import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class SimpleDictionary implements Dictionary {
    private static final String WORD_PATTERN = "[a-z]{2,}";
    private final List<String> dictionary;

    public SimpleDictionary() {
        this.dictionary = List.of(
            "child",
            "botcher",
            "botcher",
            "diameter",
            "correlative",
            "codex",
            "crow",
            "dominant",
            "disdain",
            "flower"
        );
    }

    public SimpleDictionary(List<String> dictionary) {
        this.dictionary = dictionary;
        checkInputDictionary();
    }

    @Override
    public @NotNull String randomWord() {
        Random random = new Random();
        return dictionary.get(random.nextInt(dictionary.size()));
    }

    private void checkInputDictionary() {
        if (dictionary == null || dictionary.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (String word : dictionary) {
            if (word == null || !word.matches(WORD_PATTERN)) {
                throw new IllegalArgumentException();
            }
        }
    }
}
