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
}
