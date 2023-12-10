package edu.project1;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
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

    @ParameterizedTest
    @MethodSource("testCases")
    void testWinDefeatGiveUpConsoleHangman(String[] input, List<String> expected) throws Exception {
        String actual = runGame(input);
        for(String line : expected){
            assertThat(actual).contains(line);
        }
    }

    private String runGame(String[] input) throws Exception {
        String result = SystemLambda.tapSystemOutNormalized(() -> {
            SystemLambda.withTextFromSystemIn(input)
                .execute(() -> {
                    ConsoleHangman game = new ConsoleHangman(new BadDictionary("word"));
                    game.run();
                });
        });

        return result;
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of(
                new String[] {"w", "o", "r", "r", "d"},
                List.of(
                    "> Hint: To give up press CTRL D",
                    "> Guess a letter:",
                    "Hit!",
                    "The word: w***",
                    "The word: wo**",
                    "The word: wor*",
                    "This letter has already been!",
                    "The word: word",
                    "You win!"
                )
            ),
            Arguments.of(
                new String[] {"t", "e", "h", "g", "a"},
                List.of(
                    "> Hint: To give up press CTRL D",
                    "> Guess a letter:",
                    "Missed, mistake 1 out of 5",
                    "Missed, mistake 2 out of 5",
                    "Missed, mistake 3 out of 5",
                    "Missed, mistake 4 out of 5",
                    "Missed, mistake 5 out of 5",
                    "You lost!"
                )
            ),
            Arguments.of(
                new String[] {"t"},
                List.of(
                    "> Hint: To give up press CTRL D",
                    "> Guess a letter:",
                    "You gave up!",
                    "You lost!"
                )
            )
        );

    }
}
