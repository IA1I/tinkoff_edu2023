package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task7Test {
    @ParameterizedTest
    @CsvSource({
        "010",
        "0101100101010",
        "000000000",
        "110111111",
        "000"
    })
    void isContainsAtLeast3CharactersWithThird0shouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task7.isContainsAtLeast3CharactersWithThird0(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "11",
        "111",
        "001000000",
        "111111111",
        "001100101010",
        "word"
    })
    void isContainsAtLeast3CharactersWithThird0shouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task7.isContainsAtLeast3CharactersWithThird0(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isContainsAtLeast3CharactersWithThird0shouldReturnFalseForNull() {
        boolean actual = Task7.isContainsAtLeast3CharactersWithThird0(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isContainsAtLeast3CharactersWithThird0shouldReturnFalseForEmptyString() {
        boolean actual = Task7.isContainsAtLeast3CharactersWithThird0("");

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "010",
        "0101100101010",
        "000000000",
        "110111111",
        "000",
        "11",
        "110000001",
        "101001"
    })
    void isStartsAndEndsWithSameCharacterShouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task7.isStartsAndEndsWithSameCharacter(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "10",
        "011",
        "01010000001",
        "0111111111",
        "1001100101010",
        "word"
    })
    void isStartsAndEndsWithSameCharacterShouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task7.isStartsAndEndsWithSameCharacter(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isStartsAndEndsWithSameCharacterShouldReturnFalseForNull() {
        boolean actual = Task7.isStartsAndEndsWithSameCharacter(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isStartsAndEndsWithSameCharacterShouldReturnFalseForEmptyString() {
        boolean actual = Task7.isStartsAndEndsWithSameCharacter("");

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "0",
        "1",
        "00",
        "01",
        "10",
        "11",
        "000",
        "001",
        "010",
        "011",
        "100",
        "101",
        "110",
        "111"
    })
    void isLengthBetween1And3shouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task7.isLengthBetween1And3(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "0111",
        "0110000",
        "01010000001",
        "0111111111",
        "1001100101010",
        "word",
        "123"
    })
    void isLengthBetween1And3shouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task7.isLengthBetween1And3(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isLengthBetween1And3shouldReturnFalseForNull() {
        boolean actual = Task7.isLengthBetween1And3(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isLengthBetween1And3shouldReturnFalseForEmptyString() {
        boolean actual = Task7.isLengthBetween1And3("");

        assertThat(actual).isFalse();
    }
}
