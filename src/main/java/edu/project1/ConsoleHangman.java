package edu.project1;

import edu.project1.dictionaries.Dictionary;
import edu.project1.gamesession.Session;
import edu.project1.guessresults.GuessResult;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleHangman {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String WORD_PATTERN = "[a-z]{2,}";
    private static final String GUESS_A_LETTER = "\n> Guess a letter:";
    public static final int DEFAULT_MAX_ATTEMPTS = 5;
    private final int maxAttempts;
    private final Dictionary dictionary;

    public ConsoleHangman(Dictionary dictionary) {
        this(DEFAULT_MAX_ATTEMPTS, dictionary);
    }

    public ConsoleHangman(int maxAttempts, Dictionary dictionary) {
        this.maxAttempts = maxAttempts;
        this.dictionary = dictionary;
    }

    public void run() {
        String word = dictionary.randomWord();
        checkHiddenWord(word);
        Session session = new Session(word, this.maxAttempts);
        GuessResult guessResult = null;
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver(guessResult)) {
            LOGGER.info(GUESS_A_LETTER);
            try {
                String input = scanner.nextLine();
                guessResult = tryGuess(session, input);
            } catch (NoSuchElementException noSuchElementException) {
                guessResult = session.giveUp();
            }
            printState(guessResult);
        }

        scanner.close();
    }

    private GuessResult tryGuess(Session session, String input) {

        return session.guess(input);
    }

    private void printState(GuessResult guess) {
        LOGGER.info(guess.message());
    }

    private boolean isGameOver(GuessResult guessResult) {
        return guessResult instanceof GuessResult.Win || guessResult instanceof GuessResult.Defeat;
    }

    private void checkHiddenWord(String word) {
        if (word == null || !word.matches(WORD_PATTERN)) {
            throw new IllegalArgumentException();
        }
    }
}
