package edu.hw5;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {

    @ParameterizedTest
    @MethodSource("correctTestCases")
    void shouldReturnAverageSessionTime(
        final String[] input,
        String expected
    ) {
        String actual = Task1.getAverageSessionTime(input);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> correctTestCases() {
        return Stream.of(
            Arguments.of(new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 - 2022-04-02, 01:20"}, "3ч 40м"),
            Arguments.of(new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50"}, "3ч 30м"),
            Arguments.of(new String[] {"2022-03-12, 20:20 - 2022-03-12, 20:50"}, "30м")
        );
    }

    @Test
    void shouldThrowIAEForWrongDateTimeInputFormat() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {"2022-03, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 - 2022-04-02, 01:20"})
        );
    }

    @Test
    void shouldThrowIAEForWrongDateTimeInput() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {"2022-03-34, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 - 2022-04-02, 01:20"})
        );
    }

    @Test
    void shouldThrowIAEFor1DateTimeInSession() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {"2022-03-12, 20:20"})
        );
    }

    @Test
    void shouldThrowIAEFor3DateTimeInSession() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {
                "2022-03-12, 20:20 - 2022-03-12, 23:50 - 2022-03-12, 23:50"})
        );
    }

    @Test
    void shouldThrowIAEForNullArrayInput() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(null)
        );
    }

    @Test
    void shouldThrowIAEForNullArrayElementsInput() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {"2022-03-12, 20:20 - 2022-03-12, 20:50", null})
        );
    }

    @Test
    void shouldThrowIAEForEmptyArray() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {})
        );
    }

    @Test
    void shouldThrowIAEForArrayWithEmptyString() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {""})
        );
    }

    @Test
    void shouldThrowIAEForNegativeSessionInterval() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Task1.getAverageSessionTime(new String[] {"2022-03-12, 20:50 - 2022-03-12, 20:20"})
        );
    }
}
