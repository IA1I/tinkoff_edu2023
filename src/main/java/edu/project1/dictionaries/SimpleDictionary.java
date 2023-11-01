package edu.project1.dictionaries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class SimpleDictionary implements Dictionary {
    private static final String WORD_PATTERN = "[a-z]{2,}";
    private final List<String> dictionary = new ArrayList<>();

    {
        dictionary.add("child");
        dictionary.add("botcher");
        dictionary.add("diameter");
        dictionary.add("correlative");
        dictionary.add("codex");
        dictionary.add("crow");
        dictionary.add("dominant");
        dictionary.add("disdain");
        dictionary.add("flower");
    }

    @Override
    public @NotNull String randomWord() {
        Random random = new Random();
        String word = dictionary.get(random.nextInt(dictionary.size()));
        checkHiddenWord(word);
        return word;
    }

    private void checkHiddenWord(String word) {
        if (word == null || !word.matches(WORD_PATTERN)) {
            throw new IllegalArgumentException();
        }
    }
}
