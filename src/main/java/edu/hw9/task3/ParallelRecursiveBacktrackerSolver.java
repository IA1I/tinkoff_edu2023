package edu.hw9.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelRecursiveBacktrackerSolver extends RecursiveTask<List<Coordinate>> {
    private static final byte NO_WALLS = 0;
    private static final byte UPPER_WALL = 1;
    private static final byte RIGHT_WALL = 2;
    private static final byte BOTTOM_WALL = 4;
    private static final byte LEFT_WALL = 8;
    private final Coordinate start;
    private final Coordinate end;
    private final byte[][] grid;
    private final boolean[][] visited;
    private final List<Coordinate> solvedPath;

    public ParallelRecursiveBacktrackerSolver(
        Coordinate start,
        Coordinate end,
        byte[][] grid,
        boolean[][] visited,
        List<Coordinate> solvedPath
    ) {
        this.start = start;
        this.end = end;
        this.grid = grid;
        this.visited = visited;
        this.solvedPath = solvedPath;
    }

    @Override
    protected List<Coordinate> compute() {
        List<Coordinate> path = new ArrayList<>();
        int row = start.getRow();
        int column = start.getColumn();
        if (isEndOfPath(start)) {
            path.addAll(solvedPath);
            path.add(end);
        }
        solvedPath.add(start);
        visited[row][column] = true;
        if ((grid[row][column] & UPPER_WALL) == NO_WALLS && !visited[row - 1][column]) {
            ParallelRecursiveBacktrackerSolver task =
                new ParallelRecursiveBacktrackerSolver(new Coordinate(row - 1, column), end, grid, visited, solvedPath);
            task.fork();
            path.addAll(task.join());
        }
        if ((grid[row][column] & RIGHT_WALL) == NO_WALLS && !visited[row][column + 1]) {
            ParallelRecursiveBacktrackerSolver task =
                new ParallelRecursiveBacktrackerSolver(new Coordinate(row, column + 1), end, grid, visited, solvedPath);
            task.fork();
            path.addAll(task.join());
        }
        if ((grid[row][column] & BOTTOM_WALL) == NO_WALLS && !visited[row + 1][column]) {
            ParallelRecursiveBacktrackerSolver task =
                new ParallelRecursiveBacktrackerSolver(new Coordinate(row + 1, column), end, grid, visited, solvedPath);
            task.fork();
            path.addAll(task.join());
        }
        if ((grid[row][column] & LEFT_WALL) == NO_WALLS && !visited[row][column - 1]) {
            ParallelRecursiveBacktrackerSolver task =
                new ParallelRecursiveBacktrackerSolver(new Coordinate(row, column - 1), end, grid, visited, solvedPath);
            task.fork();
            path.addAll(task.join());
        }
        solvedPath.remove(start);
        visited[row][column] = false;
        return path;
    }

    private boolean isEndOfPath(Coordinate coordinate) {
        if (coordinate.getRow() != end.getRow()) {
            return false;
        }
        return coordinate.getColumn() == end.getColumn();
    }
}
