package edu.project2.maze;

public final class Maze {
    public static final int FOUR_WALL = 15;
    private final int height;
    private final int width;
    private final byte[][] grid;
    public static final byte NO_WALLS = 0;
    public static final byte UPPER_WALL = 1;
    public static final byte RIGHT_WALL = 2;
    public static final byte BOTTOM_WALL = 4;
    public static final byte LEFT_WALL = 8;
    public static final int RIGHT_AND_BOTTOM_WALLS = RIGHT_WALL + BOTTOM_WALL;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = createInitialGrid(height, width);
    }

    private byte[][] createInitialGrid(int height, int width) {
        byte[][] initialGrid = new byte[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                initialGrid[row][column] = FOUR_WALL;
            }
        }

        return initialGrid;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public byte[][] getGrid() {
        return grid;
    }

    public void setGridRow(byte[] row, int rowIndex) {
        grid[rowIndex] = row;
    }

    public void breakWall(int row, int column, byte wall) {
        grid[row][column] -= wall;
    }

}
