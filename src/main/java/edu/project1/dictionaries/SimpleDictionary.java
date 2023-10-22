package edu.project1.dictionaries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class SimpleDictionary implements Dictionary {
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

        return dictionary.get(random.nextInt(dictionary.size()));
    }
}
