package edu.project2.solvers;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;

import static edu.project2.maze.Maze.*;

public class WallFollowerSolver implements Solver {
    public static final int TOTAL_DIRECTIONS = 4;
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    private int height;
    private int width;
    private byte[][] grid;
    private static final byte[][] ALL_DIRECTIONS = new byte[][] {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private boolean isClockwise = false;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        height = maze.getHeight();
        width = maze.getWidth();
        grid = maze.getGrid();
        Coordinate current = new Coordinate(start.getRow(), start.getColumn());
        Coordinate previous;
        List<Coordinate> path = new ArrayList<>();
        path.add(current);
        while (!current.equals(end)) {
            List<Coordinate> possibleCoordinates = getPossibleCoordinates(current);
            if(possibleCoordinates.size() == 1){
                current = possibleCoordinates.get(0);

                path.add(possibleCoordinates.get(0));
            } else {
                if(isClockwise){

                }
            }
        }
        return null;
    }

    private List<Coordinate> getPossibleCoordinates(Coordinate coordinate) {
        List<Coordinate> possibleCoordinates = new ArrayList<>();
        int row = coordinate.getRow();
        int column = coordinate.getColumn();
        byte wall = 1;
        for (int direction = 0; direction < TOTAL_DIRECTIONS; direction++) {
            if ((grid[row][column] & wall) == NO_WALLS) {
                possibleCoordinates.add(new Coordinate(
                    row + ALL_DIRECTIONS[direction][VERTICAL],
                    column + ALL_DIRECTIONS[direction][HORIZONTAL]
                ));
            }
            wall <<= 1;
        }

        return possibleCoordinates;
    }

    private Coordinate getFirstStep(Coordinate current){
        if()
    }
}
