package ChessGame.Pieces;

import ChessGame.Board;

import java.util.Vector;

public class Bishop extends Piece {

    public static int [][] offsets = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

    public Bishop(int xLoc, int yLoc, int side){
        super("Bishop", xLoc, yLoc, side);
    }

    @Override
    public Vector findAvailableMoves(Board myBoard) {
        return entireDirectionMoveCheck(myBoard, offsets);
    }
}