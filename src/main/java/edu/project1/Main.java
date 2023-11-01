package edu.project1;

import edu.project1.dictionaries.SimpleDictionary;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ConsoleHangman game = new ConsoleHangman(new SimpleDictionary());
        game.run();
    }
}
