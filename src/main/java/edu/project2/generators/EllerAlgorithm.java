package edu.project2.generators;

import edu.project2.maze.Maze;
import java.util.Random;
import static edu.project2.maze.Maze.BOTTOM_WALL;
import static edu.project2.maze.Maze.LEFT_WALL;
import static edu.project2.maze.Maze.RIGHT_WALL;
import static edu.project2.maze.Maze.UPPER_WALL;

public class EllerAlgorithm implements Generator {
    public static final int EMPTY_SET = 0;
    private Random random;
    private Maze maze;
    private int height;
    private int width;
    private int[] uniqueSets;
    private int lastSet = 1;

    public EllerAlgorithm(Random random) {
        this.random = random;
    }

    public EllerAlgorithm() {
        this.random = new Random();
    }

    @Override
    public Maze generate(int height, int width) {
        checkInput(height, width);
        initializeMazeParameters(height, width);
        generateMaze();

        return maze;
    }

    private void initializeMazeParameters(int height, int width) {
        maze = new Maze(height, width);
        this.height = height;
        this.width = width;
        uniqueSets = new int[width];
    }

    private void generateMaze() {
        byte[] row = generateFirstRow();
        for (int rowIndex = 0; rowIndex < height - 1; rowIndex++) {
            setUniqueSets();
            setRightWall(row);
            setBottomWall(row);
            maze.setGridRow(row, rowIndex);
            row = generateNextRow(row);
        }

        row = generateLastRow(row);
        maze.setGridRow(row, height - 1);
    }

    private static void checkInput(int height, int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException();
        }
    }

    private void setBottomWall(byte[] row) {
        for (int column = 0; column < width; column++) {
            if (isItPossibleToPutBottomWall(row, uniqueSets[column]) && random.nextBoolean()) {
                row[column] += BOTTOM_WALL;
            }
        }
    }

    private void setRightWall(byte[] row) {
        for (int column = 0; column < width - 1; column++) {
            if (uniqueSets[column] == uniqueSets[column + 1] || random.nextBoolean()) {
                row[column] |= RIGHT_WALL;
                row[column + 1] |= LEFT_WALL;
            } else {
                mergeSets(uniqueSets[column], uniqueSets[column + 1]);
            }
        }
    }

    private void setUniqueSets() {
        for (int column = 0; column < width; column++) {
            if (uniqueSets[column] == EMPTY_SET) {
                uniqueSets[column] = lastSet++;
            }
        }
    }

    private byte[] generateFirstRow() {
        byte[] firstRow = new byte[width];
        firstRow[0] = LEFT_WALL + UPPER_WALL;
        for (int column = 1; column < width - 1; column++) {
            firstRow[column] = UPPER_WALL;
        }
        firstRow[width - 1] = RIGHT_WALL + UPPER_WALL;

        return firstRow;
    }

    private void mergeSets(int newSet, int oldSet) {
        for (int column = 0; column < width; column++) {
            if (uniqueSets[column] == oldSet) {
                uniqueSets[column] = newSet;
            }
        }
    }

    private boolean isItPossibleToPutBottomWall(byte[] row, int currentSet) {
        int uniqueSetElements = 0;
        int bottomWalls = 0;
        for (int column = 0; column < width; column++) {
            if (uniqueSets[column] == currentSet) {
                uniqueSetElements++;
                if ((row[column] & BOTTOM_WALL) == BOTTOM_WALL) {
                    bottomWalls++;
                }
            }
        }

        return uniqueSetElements > 1 && (uniqueSetElements - bottomWalls) > 1;
    }

    private byte[] generateNextRow(byte[] row) {
        byte[] nextRow = new byte[width];
        for (int column = 0; column < width; column++) {
            if ((row[column] & BOTTOM_WALL) == BOTTOM_WALL) {
                uniqueSets[column] = 0;
                nextRow[column] += UPPER_WALL;
            }
        }
        nextRow[0] += LEFT_WALL;
        nextRow[width - 1] += RIGHT_WALL;

        return nextRow;
    }

    private byte[] generateLastRow(byte[] row) {
        setUniqueSets();
        setRightWall(row);
        for (int column = 0; column < width - 1; column++) {
            row[column] |= BOTTOM_WALL;
            if (uniqueSets[column] != uniqueSets[column + 1]) {
                row[column] &= ~RIGHT_WALL;
                row[column + 1] &= ~LEFT_WALL;
                mergeSets(uniqueSets[column], uniqueSets[column + 1]);
            }
        }
        row[width - 1] |= BOTTOM_WALL;
        return row;
    }
}
