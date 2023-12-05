package edu.hw9;

import edu.hw9.task3.Coordinate;
import edu.hw9.task3.ParallelRecursiveBacktrackerSolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    byte[][] grid = {
        {11, 9, 1, 1, 7},
        {10, 10, 10, 14, 11},
        {12, 6, 10, 11, 10},
        {11, 9, 0, 2, 10},
        {12, 6, 14, 12, 6}
    };

    @ParameterizedTest
    @MethodSource("testCasesWithCorrectInput")
    void shouldReturnPathForGrid5x5(List<Coordinate> expectedPath, Coordinate start, Coordinate end) {
        ParallelRecursiveBacktrackerSolver solver =
            new ParallelRecursiveBacktrackerSolver(start, end, grid, new boolean[5][5], new ArrayList<>());
        List<Coordinate> actualPath = solver.invoke();
        assertThat(actualPath.size()).isEqualTo(expectedPath.size());
        assertThat(actualPath).isEqualTo(expectedPath);
    }

    static Stream<Arguments> testCasesWithCorrectInput() {
        return Stream.of(
            Arguments.of(List.of(
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(2, 0),
                new Coordinate(2, 1),
                new Coordinate(1, 1),
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(3, 2),
                new Coordinate(3, 3),
                new Coordinate(4, 3),
                new Coordinate(4, 4)
            ), new Coordinate(0, 0), new Coordinate(4, 4)),
            Arguments.of(List.of(
                new Coordinate(0, 4),
                new Coordinate(0, 3),
                new Coordinate(0, 2),
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(3, 2)
            ), new Coordinate(0, 4), new Coordinate(3, 2))
        );
    }
}
