package edu.project2.solvers;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.LinkedList;
import java.util.List;
import static edu.project2.maze.Maze.BOTTOM_WALL;
import static edu.project2.maze.Maze.LEFT_WALL;
import static edu.project2.maze.Maze.NO_WALLS;
import static edu.project2.maze.Maze.RIGHT_WALL;
import static edu.project2.maze.Maze.UPPER_WALL;

public class RecursiveBacktrackerSolver implements Solver {
    private boolean[][] visitedGrid;
    private int height;
    private int width;
    private byte[][] grid;
    private List<Coordinate> path;
    private Coordinate end;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        checkInput(maze, start, end);
        initializeParameters(maze, end);
        recursiveBackTracker(start);

        return path;
    }

    private static void checkInput(Maze maze, Coordinate start, Coordinate end) {
        if (start == null || end == null || maze == null) {
            throw new IllegalArgumentException();
        }
        if (0 > start.getRow() || start.getRow() >= maze.getHeight() || 0 > start.getColumn()
            || start.getColumn() >= maze.getWidth()) {
            throw new IllegalArgumentException();
        }
        if (0 > end.getRow() || end.getRow() >= maze.getHeight() || 0 > end.getColumn()
            || end.getColumn() >= maze.getWidth()) {
            throw new IllegalArgumentException();
        }
    }

    private void initializeParameters(Maze maze, Coordinate end) {
        height = maze.getHeight();
        width = maze.getWidth();
        grid = maze.getGrid();
        visitedGrid = new boolean[height][width];
        path = new LinkedList<>();
        this.end = end;
    }

    @SuppressWarnings("ReturnCount")
    private void recursiveBackTracker(Coordinate current) {
        if (isOver()) {
            return;
        }
        int row = current.getRow();
        int column = current.getColumn();
        if (!isCoordinateCorrect(current)) {
            return;
        }
        path.add(current);
        visitedGrid[row][column] = true;
        if (isEndOfPath(current)) {
            return;
        }
        if ((grid[row][column] & UPPER_WALL) == NO_WALLS && !visitedGrid[row - 1][column]) {
            recursiveBackTracker(new Coordinate(row - 1, column));
        }
        if ((grid[row][column] & RIGHT_WALL) == NO_WALLS && !visitedGrid[row][column + 1]) {
            recursiveBackTracker(new Coordinate(row, column + 1));
        }
        if ((grid[row][column] & BOTTOM_WALL) == NO_WALLS && !visitedGrid[row + 1][column]) {
            recursiveBackTracker(new Coordinate(row + 1, column));
        }
        if ((grid[row][column] & LEFT_WALL) == NO_WALLS && !visitedGrid[row][column - 1]) {
            recursiveBackTracker(new Coordinate(row, column - 1));
        }
        if (!isOver()) {
            path.removeLast();
        }
    }

    private boolean isCoordinateCorrect(Coordinate coordinate) {
        int row = coordinate.getRow();
        int column = coordinate.getColumn();
        if (0 > row || row >= height) {
            return false;
        }
        if (0 > column || column >= width) {
            return false;
        }

        return !visitedGrid[row][column];
    }

    private boolean isEndOfPath(Coordinate coordinate) {
        return coordinate.getRow() == end.getRow() && coordinate.getColumn() == end.getColumn();
    }

    private boolean isOver() {
        return visitedGrid[end.getRow()][end.getColumn()];
    }
}
