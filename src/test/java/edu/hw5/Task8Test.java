package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task8Test {

    @ParameterizedTest
    @CsvSource({
        "010",
        "010110010101011",
        "1",
        "0",
        "110111111",
        "0001010"
    })
    void isOddLengthShouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task8.isOddLength(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "11",
        "00",
        "0010000001",
        "1111111110",
        "001100101010",
        "word"
    })
    void isOddLengthShouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task8.isOddLength(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isOddLengthShouldReturnFalseForNull() {
        boolean actual = Task8.isOddLength(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isOddLengthShouldReturnFalseForEmptyString() {
        boolean actual = Task8.isOddLength("");

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "010",
        "1010",
        "0",
        "10",
        "1111",
        "000"
    })
    void isStartsWith0AndOddOrStartsWith1AndShouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "111",
        "00",
        "100",
        "0111",
        "1",
        "word"
    })
    void isStartsWith0AndOddOrStartsWith1AndShouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isStartsWith0AndOddOrStartsWith1AndShouldReturnFalseForNull() {
        boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isStartsWith0AndOddOrStartsWith1AndShouldReturnFalseForEmptyString() {
        boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And("");

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "000",
        "1010101",
        "0100",
        "001011",
        "111011101110111",
        "111000111001110111",
        "111011101101010101",
        "00111111110111111"
    })
    void isQuantity0IsAMultipleOf3shouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task8.isQuantity0IsAMultipleOf3(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "111",
        "00",
        "1001",
        "0110100",
        "1",
        "00",
        "1000100",
        "0",
        "word"
    })
    void isQuantity0IsAMultipleOf3shouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task8.isQuantity0IsAMultipleOf3(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isQuantity0IsAMultipleOf3shouldReturnFalseForNull() {
        boolean actual = Task8.isQuantity0IsAMultipleOf3(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isQuantity0IsAMultipleOf3shouldReturnFalseForEmptyString() {
        boolean actual = Task8.isQuantity0IsAMultipleOf3("");

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "000",
        "1010101",
        "0100",
        "001011",
        "1000",
        "0",
        "1",
        "110001",
        "1110111",
        "111000"
    })
    void isAnyStringOtherThan11Or111shouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task8.isAnyStringOtherThan11Or111(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "11",
        "111",
        "word"
    })
    void isAnyStringOtherThan11Or111shouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task8.isAnyStringOtherThan11Or111(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isAnyStringOtherThan11Or111shouldReturnFalseForNull() {
        boolean actual = Task8.isAnyStringOtherThan11Or111(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isAnyStringOtherThan11Or111shouldReturnFalseForEmptyString() {
        boolean actual = Task8.isAnyStringOtherThan11Or111("");

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "010",
        "010101",
        "0101",
        "111111111111",
        "0"
    })
    void isEveryOddCharacterIs1shouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task8.isEveryOddCharacterIs1(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "0100",
        "001",
        "00",
        "word"
    })
    void isEveryOddCharacterIs1shouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task8.isEveryOddCharacterIs1(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isEveryOddCharacterIs1shouldReturnFalseForNull() {
        boolean actual = Task8.isEveryOddCharacterIs1(null);

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "010",
        "000",
        "010000000",
        "000001",
        "00"
    })
    void isContainsAtLeastTwo0AndAtMostOne1shouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "11",
        "00000010001",
        "00000011",
        "01",
        "1",
        "word"
    })
    void isContainsAtLeastTwo0AndAtMostOne1shouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isContainsAtLeastTwo0AndAtMostOne1shouldReturnFalseForNull() {
        boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1(null);

        assertThat(actual).isFalse();
    }

    @Test
    void isContainsAtLeastTwo0AndAtMostOne1shouldReturnFalseForEmptyString() {
        boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1("");

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "010",
        "1",
        "1010101010",
        "000001",
        "0100000"
    })
    void isNoConsecutive1shouldReturnTrueForCorrectInput(final String input) {
        boolean actual = Task8.isNoConsecutive1(input);

        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "11",
        "11111",
        "00000011",
        "01000010111110",
        "word"
    })
    void isNoConsecutive1shouldReturnFalseForNotMatchedString(final String input) {
        boolean actual = Task8.isNoConsecutive1(input);

        assertThat(actual).isFalse();
    }

    @Test
    void isNoConsecutive1shouldReturnFalseForNull() {
        boolean actual = Task8.isNoConsecutive1(null);

        assertThat(actual).isFalse();
    }
}
