package edu.hw3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Nested
    @DisplayName("1. Шифр Атбаш")
    class TestTask1 {

        @Test
        void shouldThrowIllegalArgumentExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Task1.atbash(null));
        }

        @ParameterizedTest
        @CsvSource({
            "Hello world!, Svool dliow!",
            "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler, Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi",
            "1234567890-=.!?/'[]{}()\\;:<>*&^%$#@, 1234567890-=.!?/'[]{}()\\;:<>*&^%$#@"
        })
        void shouldCipherString(final String input, final String expected) {
            String actual = Task1.atbash(input);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("2. Кластеризация скобок")
    class TestTask2{

        @Test
        void shouldThrowIllegalArgumentExceptionForNullInput(){
            assertThrows(IllegalArgumentException.class, () -> Task2.clusterize(null));
        }

        @ParameterizedTest
        @MethodSource("testCasesForBalancedInput")
        void shouldReturnClusterForBalancedInput(final String input, final List<String> expected){
            List<String> actual = Task2.clusterize(input);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCasesForBalancedInput(){
            return Stream.of(
                Arguments.of("()()()", List.of("()", "()", "()")),
                Arguments.of("((()))", List.of("((()))")),
                Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
                Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))"))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForUnbalancedInput")
        void shouldThrowIllegalArgumentExceptionForUnbalancedInput(final String input){
            assertThrows(IllegalArgumentException.class, () -> Task2.clusterize(input));
        }

        static Stream<Arguments> testCasesForUnbalancedInput(){
            return Stream.of(
                Arguments.of("(()"),
                Arguments.of("()))")
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForBadInput")
        void shouldThrowIllegalArgumentExceptionForBadInput(final String input){
            assertThrows(IllegalArgumentException.class, () -> Task2.clusterize(input));
        }

        static Stream<Arguments> testCasesForBadInput(){
            return Stream.of(
                Arguments.of("((213)5.sfsa"),
                Arguments.of("()))kmf.2/40-(*")
            );
        }
    }

    @Nested
    @DisplayName("3. Частота слов")
    class TestTask3 {
        @Test
        void shouldThrowIllegalArgumentExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Task3.freqDict(null));
        }

        @ParameterizedTest
        @MethodSource("testCasesForListStrings")
        void shouldReturnFrequencyDictionaryForListStrings(
            final List<String> input,
            final Map<String, Integer> expected
        ) {
            Map<String, Integer> actual = Task3.freqDict(input);

            assertThat(actual).containsAllEntriesOf(expected);
        }

        static Stream<Arguments> testCasesForListStrings() {
            return Stream.of(
                Arguments.of(List.of("a", "bb", "a", "bb"), Map.of("bb", 2, "a", 2)),
                Arguments.of(List.of("this", "and", "that", "and"), Map.of("that", 1, "and", 2, "this", 1)),
                Arguments.of(List.of("код", "код", "код", "bug"), Map.of("код", 3, "bug", 1))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForListIntegers")
        void shouldReturnFrequencyDictionaryForListIntegers(
            final List<Integer> input,
            final Map<Integer, Integer> expected
        ) {
            Map<Integer, Integer> actual = Task3.freqDict(input);

            assertThat(actual).containsAllEntriesOf(expected);
        }

        static Stream<Arguments> testCasesForListIntegers() {
            return Stream.of(
                Arguments.of(List.of(1, 1, 2, 2), Map.of(1, 2, 2, 2))
            );
        }
    }

    @Nested
    @DisplayName("4. Римские цифры")
    class TestTask4{

        @ParameterizedTest
        @CsvSource({
            "0",
            "4000",
            "-1",
            "5000"
        })
        void shouldThrowIllegalArgumentExceptionForBadInput(final int input){
            assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(input));
        }

        @ParameterizedTest
        @CsvSource({
            "2, II",
            "12, XII",
            "16, XVI",
            "3999, MMMCMXCIX",
            "1988, MCMLXXXVIII",
            "283, CCLXXXIII"
        })
        void shouldConvertDecimalNumberToRoman(final int input, final String expected){
            String actual = Task4.convertToRoman(input);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
