package edu.project1.guessresults;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public sealed interface GuessResult {

    @NotNull String message();

    record Defeat(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            if (attempt == maxAttempts) {
                return "\nMissed, mistake " + attempt + " out of " + maxAttempts + "\nYou lost!";
            } else {
                return "\nYou gave up!\nYou lost!";
            }
        }
    }

    record Win(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            return "\nHit!\n" +
                "The word: " + getUserAnswer() +
                "\nYou win!";
        }

        private String getUserAnswer() {
            StringBuilder stringBuilder = new StringBuilder();
            for (var letter : state) {
                stringBuilder.append(letter);
            }

            return stringBuilder.toString();
        }
    }

    record SuccessfulGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            return "\nHit!\n" +
                "The word: " + getUserAnswer();
        }

        private String getUserAnswer() {
            StringBuilder stringBuilder = new StringBuilder();
            for (var letter : state) {
                stringBuilder.append(letter);
            }

            return stringBuilder.toString();
        }
    }

    record FailedGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            return "\nMissed, mistake " + attempt + " out of " + maxAttempts;
        }
    }

    record RepeatedGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            return "\nThis letter has already been!";
        }
    }

    record WrongInputFormatGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            return "\nWrong format!";
        }
    }
}
