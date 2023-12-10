package edu.project2;

import edu.project2.generators.EllerAlgorithm;
import edu.project2.generators.Generator;
import edu.project2.generators.HuntAndKillGenerator;
import edu.project2.maze.Maze;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EllerAlgorithmTest {
    byte[][] expectedGrid = {
        {11, 9, 1, 1, 7},
        {10, 10, 10, 14, 11},
        {12, 6, 10, 11, 10},
        {11, 9, 0, 2, 10},
        {12, 6, 14, 12, 6}
    };

    @Test
    void shouldReturnGeneratedGrid5x5() {
        int height = 5;
        int width = 5;
        Generator generator = new EllerAlgorithm(new Random(1L));
        Maze maze = generator.generate(height, width);
        byte[][] actualGrid = maze.getGrid();
        assertThat(actualGrid).isDeepEqualTo(expectedGrid);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0",
        "-1, 3",
        "5, -1",
        "-4, -2"
    })
    void shouldThrowIllegalArgumentExceptionForWrongSize(final int height, final int width) {
        Generator generator = new EllerAlgorithm(new Random(1L));

        assertThrows(IllegalArgumentException.class, () -> generator.generate(height, width));
    }
}
