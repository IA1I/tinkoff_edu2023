package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task6Test {
    @ParameterizedTest
    @CsvSource({
        "abc, achfdbaabgabcaabg",
        "abc, abc",
        "or, word",
        "or, wordwordword",
        "word123, sacsavas2312.zx][-=word123sfvcoi76",
        "0-9], abs[0-9]sfsd",
        "[0-9], abs[0-9]sfsd",
        "[, abs[0-9]sfsd",
        "*, ab*c",
        "\\, ab\\c",
        "\\d{3}, \\d{3}",
        "123, a1b2c3",
        "\\, a1b\\2c3"
    })
    void shouldReturnTrueForCorrectInput(final String inputS, final String inputT) {
        boolean actual = Task6.isSSubsequenceOfT(inputS, inputT);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "123, achfdbaabgabcaabg",
        "%, abc",
        "?, word",
        "., wordwordword",
        "+, sacsavas2312.zx][-=word123sfvcoi76",
        "[0-9], 123",
        "\n, abssfsd",
        "\\d{3}, 123",
        "*, abs[0-9]sfsd"
    })
    void shouldReturnFalseForSIsNotSubsequence(final String inputS, final String inputT) {
        boolean actual = Task6.isSSubsequenceOfT(inputS, inputT);

        assertThat(actual).isFalse();
    }

    @Test
    void shouldReturnFalseForNull() {
        boolean actual = Task6.isSSubsequenceOfT(null, null);

        assertThat(actual).isFalse();
    }
}
