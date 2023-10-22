package edu.project1.guessresults;

import org.jetbrains.annotations.NotNull;

public sealed interface GuessResult {

    String MISSED_MISTAKE = "\nMissed, mistake ";
    String OUT_OF = " out of ";
    String HIT = "\nHit!\n";
    String THE_WORD = "The word: ";

    @NotNull String message();

    record Defeat(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            if (attempt == maxAttempts) {
                return MISSED_MISTAKE + attempt + OUT_OF + maxAttempts + "\nYou lost!";
            } else {
                return "\nYou gave up!\nYou lost!";
            }
        }
    }

    record Win(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            return HIT + THE_WORD + getUserAnswer() + "\nYou win!";
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
            return HIT + THE_WORD + getUserAnswer();
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
            return MISSED_MISTAKE + attempt + OUT_OF + maxAttempts;
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
