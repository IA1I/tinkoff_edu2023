package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task5Test {
    @ParameterizedTest
    @CsvSource({
        "A123BE777",
        "O777OO177",
        "K145TO48"
    })
    void shouldReturnTrueForCorrectPassword(final String input) {
        boolean actual = Task5.isRegexMatches(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "abcabs",
        "123АВЕ777",
        "А123ВГ77",
        "А123ВЕ7777"
    })
    void shouldReturnFalseForIncorrectPassword(final String input) {
        boolean actual = Task5.isRegexMatches(input);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnFalseForNull() {
        boolean actual = Task5.isRegexMatches(null);

        assertThat(actual).isFalse();
    }
}
