package edu.hw5;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {
    @ParameterizedTest
    @MethodSource("testCases")
    void shouldReturnListOfLocalDatesOfFridays13th(final int input, final List<LocalDate> expected) {
        List<LocalDate> actual = Task2.getAllFriday13th(input);
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of(
                1925,
                List.of(
                    LocalDate.of(1925, 2, 13),
                    LocalDate.of(1925, 3, 13),
                    LocalDate.of(1925, 11, 13)
                )
            ),
            Arguments.of(
                2024,
                List.of(
                    LocalDate.of(2024, 9, 13),
                    LocalDate.of(2024, 12, 13)
                )
            )
        );
    }

    @ParameterizedTest
    @CsvSource({
        "0",
        "-15",
        "-346"
    })
    void shouldThrowIllegalArgumentException(final int input) {
        assertThrows(IllegalArgumentException.class, () -> Task2.getAllFriday13th(input));
    }

    @ParameterizedTest
    @MethodSource("testCasesForNextFriday13th")
    void shouldReturnNextFriday13th(final LocalDate input, final LocalDate expected) {
        LocalDate actual = Task2.getNextFriday13th(input);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> testCasesForNextFriday13th() {
        return Stream.of(
            Arguments.of(LocalDate.of(2023, 11, 9), LocalDate.of(2024, 9, 13))
        );
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNextFriday13th() {
        assertThrows(IllegalArgumentException.class, () -> Task2.getNextFriday13th(null));
    }
}
