package edu.project1;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsoleHangmanTest {

    @ParameterizedTest
    @CsvSource({
        "1",
        "ssd342sad",
        "a",
        "SAD"
    })
    void testIncorrectWordFormatForGuessing(final String word) {
        ConsoleHangman game = new ConsoleHangman(new BadDictionary(word));
        assertThrows(IllegalArgumentException.class, game::run);
    }

    @Test
    void testWinDefeatGiveUpConsoleHangman() throws Exception {
        String result = SystemLambda.tapSystemOutNormalized(() -> {
            SystemLambda.withTextFromSystemIn("w", "o", "r", "r", "d", "t", "e", "h", "g", "a", "t")
                .execute(() -> {
                    ConsoleHangman game = new ConsoleHangman(new BadDictionary("word"));
                    game.run();
                    game.run();
                    game.run();
                });
        });

        assertThat(result.contains("> Hint: To give up press CTRL D")).isTrue();
        assertThat(result.contains("> Guess a letter:")).isTrue();
        assertThat(result.contains("Hit!")).isTrue();
        assertThat(result.contains("The word: w***")).isTrue();
        assertThat(result.contains("The word: wo**")).isTrue();
        assertThat(result.contains("The word: wor*")).isTrue();
        assertThat(result.contains("This letter has already been!")).isTrue();
        assertThat(result.contains("The word: word")).isTrue();
        assertThat(result.contains("You win!")).isTrue();

        assertThat(result.contains("> Hint: To give up press CTRL D")).isTrue();
        assertThat(result.contains("> Guess a letter:")).isTrue();
        assertThat(result.contains("Missed, mistake 1 out of 5")).isTrue();
        assertThat(result.contains("Missed, mistake 2 out of 5")).isTrue();
        assertThat(result.contains("Missed, mistake 3 out of 5")).isTrue();
        assertThat(result.contains("Missed, mistake 4 out of 5")).isTrue();
        assertThat(result.contains("Missed, mistake 5 out of 5")).isTrue();
        assertThat(result.contains("You lost!")).isTrue();

        assertThat(result.contains("> Hint: To give up press CTRL D")).isTrue();
        assertThat(result.contains("> Guess a letter:")).isTrue();
        assertThat(result.contains("You gave up!")).isTrue();
        assertThat(result.contains("You lost!")).isTrue();
    }
}
