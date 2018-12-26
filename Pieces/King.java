package ChessGame.Pieces;

import ChessGame.Board;

import java.util.Vector;

public class King extends Piece {

    public static int [][] offsets = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0} ,{1, -1}};

    public King(int xLoc, int yLoc, int side) {
        super("King", xLoc, yLoc, side);
    }

    @Override
    public Vector findAvailableMoves(Board myBoard) {
        return singleSpaceMoveCheck(myBoard, offsets);
    }
}
