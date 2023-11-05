package edu.project2;

import edu.project2.generators.Generator;
import edu.project2.generators.HuntAndKillGenerator;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.renderers.Renderer;
import edu.project2.renderers.SimpleConsoleRenderer;
import edu.project2.solvers.RecursiveBacktrackerSolver;
import edu.project2.solvers.Solver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleConsoleRendererTest {
    String expectedMaze = "_____________________\n" +
        "|    ________   |   |\n" +
        "|________   |____   |\n" +
        "|       |____   |   |\n" +
        "|   |_______|   |   |\n" +
        "|_______________|___|\n";
    String expectedMazeWithPathFrom0x0 = "_____________________\n" +
        "| ▪  _▪___▪__ ▪ |   |\n" +
        "|________   |_▪__ ▪ |\n" +
        "|       |____   | ▪ |\n" +
        "|   |_______|   | ▪ |\n" +
        "|_______________|_▪_|\n";

    String expectedMazeWithPathFrom0x4 = "_____________________\n" +
        "| ▪  _▪___▪__ ▪ |   |\n" +
        "|_▪___▪__ ▪ |____   |\n" +
        "| ▪   ▪ |_▪__ ▪ |   |\n" +
        "| ▪ |_▪___▪_| ▪ |   |\n" +
        "|_▪___▪___▪___▪_|___|\n";

    @Test
    void shouldReturnRenderedMaze() {
        int height = 5;
        int width = 5;
        Generator generator = new HuntAndKillGenerator(new Random(1L));
        Maze maze = generator.generate(height, width);
        Renderer renderer = new SimpleConsoleRenderer();
        String actualMaze = renderer.render(maze);
        assertThat(actualMaze).isEqualTo(expectedMaze);
    }

    @Test
    void shouldReturnRenderedMazeWithPathFrom0x0() {
        int height = 5;
        int width = 5;
        Generator generator = new HuntAndKillGenerator(new Random(1L));
        Maze maze = generator.generate(height, width);
        Renderer renderer = new SimpleConsoleRenderer();
        Solver solver = new RecursiveBacktrackerSolver();
        List<Coordinate> actualPath = solver.solve(maze, new Coordinate(0, 0), new Coordinate(4, 4));
        String actualMaze = renderer.render(maze, actualPath);
        assertThat(actualMaze).isEqualTo(expectedMazeWithPathFrom0x0);
    }

    @Test
    void shouldReturnRenderedMazeWithPathFrom0x4() {
        int height = 5;
        int width = 5;
        Generator generator = new HuntAndKillGenerator(new Random(1L));
        Maze maze = generator.generate(height, width);
        Renderer renderer = new SimpleConsoleRenderer();
        Solver solver = new RecursiveBacktrackerSolver();
        List<Coordinate> actualPath = solver.solve(maze, new Coordinate(0, 3), new Coordinate(3, 2));
        String actualMaze = renderer.render(maze, actualPath);
        assertThat(actualMaze).isEqualTo(expectedMazeWithPathFrom0x4);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNullMaze() {
        Renderer renderer = new SimpleConsoleRenderer();
        assertThrows(IllegalArgumentException.class, () -> renderer.render(null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNulAndEmptyPath() {
        int height = 5;
        int width = 5;
        Generator generator = new HuntAndKillGenerator(new Random(1L));
        Maze maze = generator.generate(height, width);
        Renderer renderer = new SimpleConsoleRenderer();
        assertThrows(IllegalArgumentException.class, () -> renderer.render(maze, null));
        assertThrows(IllegalArgumentException.class, () -> renderer.render(maze, new ArrayList<>()));
    }
}
