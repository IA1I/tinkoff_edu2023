package edu.project1.gamesession;

import edu.project1.guessresults.GuessResult;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Session {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;
    private final Set<Character> guessedLetters = new HashSet<>();
    private static final int NO_LETTER_IN_WORD = -1;
    private static final char HIDDEN_LETTER = '*';

    public Session(String answer, int maxAttempts) {
        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.userAnswer = new char[answer.length()];
        Arrays.fill(this.userAnswer, HIDDEN_LETTER);
    }

    public @NotNull GuessResult guess(char guess) {
        GuessResult guessResult;
        if (guessedLetters.contains(guess)) {
            guessResult = new GuessResult.RepeatedGuess(userAnswer, attempts, maxAttempts);
        } else if (answer.indexOf(guess) == NO_LETTER_IN_WORD) {
            guessResult = failedGuess();
        } else {
            guessResult = successfulGuess(guess);
        }
        guessedLetters.add(guess);
        return guessResult;
    }

    public @NotNull GuessResult guess(String input) {
        if (!isInputCorrect(input)) {
            return new GuessResult.WrongInputFormatGuess(userAnswer, attempts, maxAttempts);
        }
        char guessLetter = input.charAt(0);

        return guess(guessLetter);
    }

    public @NotNull GuessResult giveUp() {
        return new GuessResult.Defeat(userAnswer, attempts, maxAttempts);
    }

    private GuessResult successfulGuess(char guess) {
        setSuccessfulGuessLetters(guess);
        if (isWordGuessed()) {
            return new GuessResult.Win(userAnswer, attempts, maxAttempts);
        } else {
            return new GuessResult.SuccessfulGuess(userAnswer, attempts, maxAttempts);
        }
    }

    private void setSuccessfulGuessLetters(char guess) {
        int index = answer.indexOf(guess);
        while (index != NO_LETTER_IN_WORD) {
            userAnswer[index] = guess;
            index = answer.indexOf(guess, index + 1);
        }
    }

    private boolean isWordGuessed() {
        for (var letter : userAnswer) {
            if (letter == HIDDEN_LETTER) {
                return false;
            }
        }

        return true;
    }

    private GuessResult failedGuess() {
        attempts++;
        if (attempts == maxAttempts) {
            return new GuessResult.Defeat(userAnswer, attempts, maxAttempts);
        } else {
            return new GuessResult.FailedGuess(userAnswer, attempts, maxAttempts);
        }
    }

    private boolean isInputCorrect(String input) {
        if (input.length() != 1) {
            return false;
        }
        char letter = input.charAt(0);
        return letter >= 'a' && letter <= 'z';
    }
}
