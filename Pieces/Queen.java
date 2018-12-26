package ChessGame.Pieces;

import ChessGame.Board;

import java.util.Vector;

public class Queen extends Piece {

    public static int [][] offsets = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};

    public Queen(int xLoc, int yLoc, int side){
        super("Queen", xLoc, yLoc, side);
    }

    @Override
    public Vector findAvailableMoves(Board myBoard) {
        return entireDirectionMoveCheck(myBoard, offsets);
    }
}