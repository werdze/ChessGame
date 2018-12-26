package ChessGame.Pieces;

import ChessGame.Board;

import java.util.Vector;

public class Knight extends Piece {

    public static int [][] offsets = {{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1} ,{-2, -1}};

    public Knight(int xLoc, int yLoc, int side) {
        super("Knight", xLoc, yLoc, side);
    }

    @Override
    public Vector findAvailableMoves(Board myBoard) {
        return singleSpaceMoveCheck(myBoard, offsets);
    }

}