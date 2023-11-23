package edu.hw7;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Task4Test {
    @ParameterizedTest
    @CsvSource({
        "10_000_000",
        "100_000_000",
        "1_000_000_000"
    })
    void shouldReturnPI(final int input) {
        double actual = Task4.countPI(input);
    }

    @ParameterizedTest
    @CsvSource({
        "10_000_000, 2",
        "10_000_000, 3",
        "10_000_000, 4",
        "10_000_000, 5",
        "10_000_000, 6",
        "100_000_000, 2",
        "100_000_000, 3",
        "100_000_000, 4",
        "100_000_000, 5",
        "100_000_000, 6",
        "1_000_000_000, 2",
        "1_000_000_000, 3",
        "1_000_000_000, 4",
        "1_000_000_000, 5",
        "1_000_000_000, 6"
    })
    void shouldReturnPIParallel(final int inputIterations, final int inputNumberOfThreads) {
        double actual = Task4.countPIParallel(inputIterations, inputNumberOfThreads);
    }
}
