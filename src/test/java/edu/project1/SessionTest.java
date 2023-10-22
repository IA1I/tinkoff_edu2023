package edu.project1;

import edu.project1.gamesession.Session;
import edu.project1.guessresults.GuessResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SessionTest {

    @ParameterizedTest
    @CsvSource({
        "1",
        "12",
        "ba",
        "/",
        ":",
        "A"
    })
    void testWrongLetterGuessFormat(final String input) {
        Session session = new Session("word", 5);
        GuessResult guessResult = session.guess(input);
        String expectedMessage = "\nWrong format!";

        assertThat(guessResult.getClass()).isEqualTo(GuessResult.WrongInputFormatGuess.class);
        assertThat(guessResult.message()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource({
        "w, w***",
        "o, *o**",
        "r, **r*",
        "d, ***d"
    })
    void testSuccessfulGuess(final String input, final String expectedArray) {
        Session session = new Session("word", 5);
        GuessResult guessResult = session.guess(input);
        String expectedMessage = "\nHit!\nThe word: " + expectedArray;

        assertThat(guessResult.getClass()).isEqualTo(GuessResult.SuccessfulGuess.class);
        assertThat(guessResult.message()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource({
        "q",
        "t",
        "y"
    })
    void testFailedGuess(final String input) {
        Session session = new Session("word", 5);
        GuessResult guessResult = session.guess(input);
        String expectedMessage = "\nMissed, mistake 1 out of 5";

        assertThat(guessResult.getClass()).isEqualTo(GuessResult.FailedGuess.class);
        assertThat(guessResult.message()).isEqualTo(expectedMessage);
    }

    @Test
    void testWin() {
        Session session = new Session("word", 5);
        session.guess("w");
        session.guess("o");
        session.guess("r");
        GuessResult guessResult = session.guess("d");
        String expectedMessage = "\nHit!\nThe word: word\nYou win!";

        assertThat(guessResult.getClass()).isEqualTo(GuessResult.Win.class);
        assertThat(guessResult.message()).isEqualTo(expectedMessage);
    }

    @Test
    void testDefeat() {
        Session session = new Session("word", 5);
        session.guess("a");
        session.guess("b");
        session.guess("c");
        session.guess("e");
        GuessResult guessResult = session.guess("f");
        String expectedMessage = "\nMissed, mistake 5 out of 5\nYou lost!";

        assertThat(guessResult.getClass()).isEqualTo(GuessResult.Defeat.class);
        assertThat(guessResult.message()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource({
        "a",
        "b",
        "o"
    })
    void TestRepeatedGuess(final String input) {
        Session session = new Session("word", 5);
        session.guess(input);
        GuessResult guessResult = session.guess(input);
        String expectedMessage = "\nThis letter has already been!";

        assertThat(guessResult.getClass()).isEqualTo(GuessResult.RepeatedGuess.class);
        assertThat(guessResult.message()).isEqualTo(expectedMessage);
    }

    @Test
    void testSurrender() {
        Session session = new Session("word", 5);
        GuessResult guessResult = session.giveUp();
        String expectedMessage = "\nYou gave up!\nYou lost!";

        assertThat(guessResult.getClass()).isEqualTo(GuessResult.Defeat.class);
        assertThat(guessResult.message()).isEqualTo(expectedMessage);
    }
}
