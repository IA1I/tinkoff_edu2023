package edu.project2.renderers;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.LinkedList;
import java.util.List;
import static edu.project2.maze.Maze.BOTTOM_WALL;
import static edu.project2.maze.Maze.RIGHT_AND_BOTTOM_WALLS;
import static edu.project2.maze.Maze.RIGHT_WALL;

public class SimpleConsoleRenderer implements Renderer {
    private static final char LINE_BREAK = '\n';
    public static final char LEFT_WALL_SYMBOL = '|';
    public static final char BOTTOM_WALL_SYMBOL = '_';
    public static final char PASSAGE = ' ';
    public static final char RIGHT_WALL_SYMBOL = '|';
    public static final String UPPER_WALL_SYMBOL = "____";
    public static final char POINT = '▪';
    private byte[][] grid;
    private int height;
    private int width;
    private List<Coordinate> path;

    @Override
    public String render(Maze maze) {
        checkInput(maze);
        initializeMazeParameters(maze);

        return renderMaze();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        checkInput(maze, path);
        initializeMazeParameters(maze);
        this.path = new LinkedList<>(path);

        return renderMazeWithSolution();
    }

    private static void checkInput(Maze maze) {
        if (maze == null) {
            throw new IllegalArgumentException();
        }
    }

    private static void checkInput(Maze maze, List<Coordinate> path) {
        if (maze == null) {
            throw new IllegalArgumentException();
        }
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void initializeMazeParameters(Maze maze) {
        grid = maze.getGrid();
        height = maze.getHeight();
        width = maze.getWidth();
    }

    private String renderMaze() {
        StringBuilder renderedMaze = new StringBuilder();
        renderUpperWall(renderedMaze);
        for (int row = 0; row < height; row++) {
            renderLine(renderedMaze, row);
        }

        return renderedMaze.toString();
    }

    private String renderMazeWithSolution() {
        StringBuilder renderedMaze = new StringBuilder();
        renderUpperWall(renderedMaze);
        for (int row = 0; row < height; row++) {
            renderLineWithPath(renderedMaze, row);
        }
        return renderedMaze.toString();

    }

    private void renderLine(StringBuilder renderedMaze, int row) {
        renderedMaze.append(LEFT_WALL_SYMBOL);
        for (int column = 0; column < width; column++) {
            renderCell(renderedMaze, row, column, BOTTOM_WALL_SYMBOL, PASSAGE);
        }
        renderedMaze.append(LINE_BREAK);
    }

    private void renderLineWithPath(StringBuilder renderedMaze, int row) {
        renderedMaze.append(LEFT_WALL_SYMBOL);
        for (int column = 0; column < width; column++) {
            if (getCoordinate(row, column)) {
                renderCell(renderedMaze, row, column, POINT, POINT);
            } else {
                renderCell(renderedMaze, row, column, BOTTOM_WALL_SYMBOL, PASSAGE);
            }
        }
        renderedMaze.append(LINE_BREAK);
    }

    private void renderCell(StringBuilder renderedMaze, int row, int column, char middleSymbol1, char middleSymbol2) {
        if (isCellWithRightAndBottomWall(row, column)) {
            renderedMaze.append(BOTTOM_WALL_SYMBOL).append(middleSymbol1).append(BOTTOM_WALL_SYMBOL).append(
                RIGHT_WALL_SYMBOL);
        } else if (isCellWithBottomWall(row, column)) {
            renderedMaze.append(BOTTOM_WALL_SYMBOL).append(middleSymbol1).append(BOTTOM_WALL_SYMBOL)
                .append(BOTTOM_WALL_SYMBOL);
        } else if (isCellWithRightWall(row, column)) {
            renderedMaze.append(PASSAGE).append(middleSymbol2).append(PASSAGE).append(RIGHT_WALL_SYMBOL);
        } else {
            renderedMaze.append(PASSAGE).append(middleSymbol2).append(PASSAGE).append(PASSAGE);
        }
    }

    private boolean isCellWithRightWall(int row, int column) {
        return (grid[row][column] & RIGHT_WALL) == RIGHT_WALL;
    }

    private boolean isCellWithBottomWall(int row, int column) {
        return (grid[row][column] & BOTTOM_WALL) == BOTTOM_WALL;
    }

    private boolean isCellWithRightAndBottomWall(int row, int column) {
        return (grid[row][column] & RIGHT_AND_BOTTOM_WALLS) == RIGHT_AND_BOTTOM_WALLS;
    }

    private void renderUpperWall(StringBuilder renderedMaze) {
        for (int column = 0; column < width; column++) {
            renderedMaze.append(UPPER_WALL_SYMBOL);
        }
        renderedMaze.append(BOTTOM_WALL_SYMBOL).append(LINE_BREAK);
    }

    private boolean getCoordinate(int row, int column) {
        for (int i = 0; i < path.size(); i++) {
            if (row == path.get(i).getRow() && column == path.get(i).getColumn()) {
                path.remove(i);
                return true;
            }
        }

        return false;
    }
}
