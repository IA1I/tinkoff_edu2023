package edu.project2;

import edu.project2.generators.Generator;
import edu.project2.generators.HuntAndKillGenerator;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.solvers.RecursiveBacktrackerSolver;
import edu.project2.solvers.Solver;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecursiveBacktrackerSolverTest {

    @ParameterizedTest
    @MethodSource("testCasesWithCorrectInput")
    void shouldReturnPathForGrid5x5(List<Coordinate> expectedPath, Coordinate start, Coordinate end) {
        int height = 5;
        int width = 5;
        Generator generator = new HuntAndKillGenerator(new Random(1L));
        Maze maze = generator.generate(height, width);
        Solver solver = new RecursiveBacktrackerSolver();
        List<Coordinate> actualPath = solver.solve(maze, start, end);
        assertThat(actualPath.size()).isEqualTo(expectedPath.size());
        assertThat(actualPath).isEqualTo(expectedPath);
    }

    static Stream<Arguments> testCasesWithCorrectInput() {
        return Stream.of(
            Arguments.of(List.of(
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(0, 3),
                new Coordinate(1, 3),
                new Coordinate(1, 4),
                new Coordinate(2, 4),
                new Coordinate(3, 4),
                new Coordinate(4, 4)
            ), new Coordinate(0, 0), new Coordinate(4, 4)),
            Arguments.of(List.of(
                new Coordinate(0, 4),
                new Coordinate(1, 4),
                new Coordinate(1, 3),
                new Coordinate(0, 3),
                new Coordinate(0, 2),
                new Coordinate(0, 1),
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(2, 3),
                new Coordinate(3, 3),
                new Coordinate(4, 3),
                new Coordinate(4, 2),
                new Coordinate(4, 1),
                new Coordinate(4, 0),
                new Coordinate(3, 0),
                new Coordinate(2, 0),
                new Coordinate(2, 1),
                new Coordinate(3, 1),
                new Coordinate(3, 2)
            ), new Coordinate(0, 4), new Coordinate(3, 2))
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesWithIncorrectInput")
    void shouldThrowIllegalArgumentException(Coordinate start, Coordinate end) {
        int height = 5;
        int width = 5;
        Generator generator = new HuntAndKillGenerator(new Random(1L));
        Maze maze = generator.generate(height, width);
        Solver solver = new RecursiveBacktrackerSolver();
        assertThrows(IllegalArgumentException.class, () -> solver.solve(maze, start, end));
    }

    static Stream<Arguments> testCasesWithIncorrectInput() {
        return Stream.of(
            Arguments.of(new Coordinate(0, 0), new Coordinate(5, 5)),
            Arguments.of(new Coordinate(-1, -3), new Coordinate(5, 5)),
            Arguments.of(new Coordinate(-1, -3), null),
            Arguments.of(null, new Coordinate(5, 5)),
            Arguments.of(null, null)
        );
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNullMaze() {
        Solver solver = new RecursiveBacktrackerSolver();
        assertThrows(
            IllegalArgumentException.class,
            () -> solver.solve(null, new Coordinate(0, 0), new Coordinate(0, 1))
        );
    }
}
