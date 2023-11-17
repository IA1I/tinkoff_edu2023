package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task4Test {
    @ParameterizedTest
    @CsvSource({
        "abc~abs",
        "123ewr!abs",
        "@",
        "asd#",
        "$yrey",
        "sxzvzx123%safr324g",
        "sda^sad",
        "sda&sad",
        "sda*sad",
        "sd|asad",
    })
    void shouldReturnTrueForCorrectPassword(final String input) {
        boolean actual = Task4.isRegexMatches(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "abcabs",
        "123324",
        "sflsfls34234sdda",
        "asd##",
        "$yrey!",
        "sxzvzx123%safr324g$",
        "!@#$%^&*"
    })
    void shouldReturnFalseForIncorrectPassword(final String input) {
        boolean actual = Task4.isRegexMatches(input);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnFalseForNull() {
        boolean actual = Task4.isRegexMatches(null);

        assertThat(actual).isFalse();
    }
}
