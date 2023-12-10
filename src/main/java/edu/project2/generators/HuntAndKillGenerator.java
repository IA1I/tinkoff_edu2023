package edu.project2.generators;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static edu.project2.maze.Maze.BOTTOM_WALL;
import static edu.project2.maze.Maze.LEFT_WALL;
import static edu.project2.maze.Maze.RIGHT_WALL;
import static edu.project2.maze.Maze.UPPER_WALL;

public class HuntAndKillGenerator implements Generator {
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private static final boolean VISITED = true;
    private Random random;
    private Maze maze;
    private boolean[][] visitedGrid;
    private int height;
    private int width;
    private static final byte[][] ALL_DIRECTIONS = new byte[][] {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public HuntAndKillGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Maze generate(int height, int width) {
        checkInput(height, width);
        initializeMazeParameters(height, width);

        int row = random.nextInt(height);
        int column = random.nextInt(width);
        Coordinate currentCoordinate = new Coordinate(row, column);
        visitedGrid[row][column] = VISITED;
        int visitedCellsCount = height * width - 1;
        while (visitedCellsCount > 0) {
            List<Coordinate> possibleDirections = getPossibleCoordinates(currentCoordinate);
            Coordinate newCoordinate;
            if (possibleDirections.isEmpty()) {
                currentCoordinate = hunt();
            } else {
                newCoordinate = possibleDirections.get(random.nextInt(possibleDirections.size()));
                makePass(currentCoordinate, newCoordinate);
                currentCoordinate = newCoordinate;
                visitedGrid[currentCoordinate.getRow()][currentCoordinate.getColumn()] = VISITED;
                visitedCellsCount--;
            }

        }

        return maze;
    }

    private void initializeMazeParameters(int height, int width) {
        this.maze = new Maze(height, width);
        visitedGrid = new boolean[height][width];
        this.height = height;
        this.width = width;
    }

    private static void checkInput(int height, int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException();
        }
    }

    private void makePass(Coordinate currentCoordinate, Coordinate newCoordinate) {
        int direction = getDirection(currentCoordinate, newCoordinate);
        int currentRow = currentCoordinate.getRow();
        int currentColumn = currentCoordinate.getColumn();
        int newRow = newCoordinate.getRow();
        int newColumn = newCoordinate.getColumn();
        switch (direction) {
            case NORTH:
                maze.breakWall(currentRow, currentColumn, UPPER_WALL);
                maze.breakWall(newRow, newColumn, BOTTOM_WALL);
                break;
            case EAST:
                maze.breakWall(currentRow, currentColumn, RIGHT_WALL);
                maze.breakWall(newRow, newColumn, LEFT_WALL);
                break;
            case SOUTH:
                maze.breakWall(currentRow, currentColumn, BOTTOM_WALL);
                maze.breakWall(newRow, newColumn, UPPER_WALL);
                break;
            case WEST:
                maze.breakWall(currentRow, currentColumn, LEFT_WALL);
                maze.breakWall(newRow, newColumn, RIGHT_WALL);
                break;
            default:
                break;
        }
    }

    private int getDirection(Coordinate currentCoordinate, Coordinate newCoordinate) {
        int row = newCoordinate.getRow() - currentCoordinate.getRow();
        int column = newCoordinate.getColumn() - currentCoordinate.getColumn();
        for (int direction = 0; direction < ALL_DIRECTIONS.length; direction++) {
            if (row == ALL_DIRECTIONS[direction][0] && column == ALL_DIRECTIONS[direction][1]) {
                return direction;
            }
        }

        return 0;
    }

    private Coordinate hunt() {
        Coordinate coordinate = new Coordinate(0, 0);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (visitedGrid[row][column]) {
                    continue;
                }
                coordinate.setRow(row);
                coordinate.setColumn(column);
                List<Coordinate> possibleCoordinates = getAdjacentVisited(coordinate);
                if (possibleCoordinates.isEmpty()) {
                    continue;
                }
                return possibleCoordinates.get(random.nextInt(possibleCoordinates.size()));
            }
        }

        return null;
    }

    private List<Coordinate> getAdjacentVisited(Coordinate coordinate) {
        List<Coordinate> possibleDirections = new ArrayList<>();

        for (var direction : ALL_DIRECTIONS) {
            Coordinate currentCoordinate =
                new Coordinate(coordinate.getRow() + direction[0], coordinate.getColumn() + direction[1]);
            if (isCoordinateCorrect(currentCoordinate)
                && visitedGrid[currentCoordinate.getRow()][currentCoordinate.getColumn()]) {
                possibleDirections.add(currentCoordinate);
            }
        }

        return possibleDirections;
    }

    private List<Coordinate> getPossibleCoordinates(Coordinate coordinate) {
        List<Coordinate> possibleDirections = new ArrayList<>();

        for (var direction : ALL_DIRECTIONS) {
            Coordinate currentCoordinate =
                new Coordinate(coordinate.getRow() + direction[0], coordinate.getColumn() + direction[1]);
            if (isCoordinateCorrect(currentCoordinate)
                && !visitedGrid[currentCoordinate.getRow()][currentCoordinate.getColumn()]) {
                possibleDirections.add(currentCoordinate);
            }
        }

        return possibleDirections;
    }

    private boolean isCoordinateCorrect(Coordinate coordinate) {
        int row = coordinate.getRow();
        int column = coordinate.getColumn();

        return row >= 0 && row < height && column >= 0 && column < width;
    }
}
