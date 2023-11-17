package edu.hw5;

import edu.hw5.task3.Task3;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    @ParameterizedTest
    @MethodSource("correctTestCases")
    void shouldReturnParsedDate(final String input, final LocalDate expected) {
        Optional<LocalDate> actual = Task3.parseDate(input);

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    static Stream<Arguments> correctTestCases() {
        return Stream.of(
            Arguments.of("2020-10-10", LocalDate.of(2020, 10, 10)),
            Arguments.of("2020-12-2", LocalDate.of(2020, 12, 2)),
            Arguments.of("1/3/1976", LocalDate.of(1976, 3, 1)),
            Arguments.of("1/3/20", LocalDate.of(2020, 3, 1)),
            Arguments.of("tomorrow", LocalDate.now().plusDays(1L)),
            Arguments.of("today", LocalDate.now()),
            Arguments.of("yesterday", LocalDate.now().minusDays(1L)),
            Arguments.of("1 day ago", LocalDate.now().minusDays(1L)),
            Arguments.of("2 days ago", LocalDate.now().minusDays(2L)),
            Arguments.of("2234 days ago", LocalDate.now().minusDays(2234L))
        );
    }

    @Test
    void shouldReturnEmptyOptionalForNull() {
        Optional<LocalDate> actual = Task3.parseDate(null);

        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("incorrectTestCases")
    void shouldReturnEmptyOptional(final String input) {
        Optional<LocalDate> actual = Task3.parseDate(input);

        assertThat(actual).isEmpty();
    }

    static Stream<Arguments> incorrectTestCases() {
        return Stream.of(
            Arguments.of("asd"),
            Arguments.of("2022-10"),
            Arguments.of("null"),
            Arguments.of("1/1"),
            Arguments.of("2022/35/40"),
            Arguments.of("")
        );
    }
}
