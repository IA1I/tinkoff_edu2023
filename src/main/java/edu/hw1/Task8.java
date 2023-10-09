package edu.hw1;

public class Task8 {
    public static final int COLUMN_LENGTH = 8;
    public static final int ROW_LENGTH = 8;
    public static final byte CELL_WITH_KNIGHT = 1;
    public static final byte[][] KnightsMoves = new byte[][]{
        {2, 1},
        {2, -1},
        {1, 2},
        {-1, 2},
        {-2, 1},
        {-2, -1},
        {-1, -2},
        {1, -2},
    };
    public static boolean knightBoardCapture(byte[][] chessboard){
        checkInput(chessboard);

        for(int row = 0; row < ROW_LENGTH; row++){
            for(int column = 0; column < COLUMN_LENGTH; column++){
                if(chessboard[row][column] == CELL_WITH_KNIGHT){
                    if(checkKnightsMoves(chessboard, row, column)){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static void checkInput(byte[][] chessboard){
        if(chessboard == null || chessboard.length != COLUMN_LENGTH){
            throw new IllegalArgumentException();
        }
        for(var row : chessboard){
            if(row == null || row.length != ROW_LENGTH){
                throw new IllegalArgumentException();
            }
        }
    }

    private static boolean checkKnightsMoves(byte[][] chessboard, int row, int column){
        for(var move : KnightsMoves){
            try {
                int verticalMove = row + move[0];
                int horizontalMove = column + move[1];
                if(chessboard[verticalMove][horizontalMove] == CELL_WITH_KNIGHT){
                    return true;
                }
            } catch (IndexOutOfBoundsException ignored){

            }
        }

        return false;
    }
}
