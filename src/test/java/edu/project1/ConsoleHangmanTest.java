package edu.project1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
}
