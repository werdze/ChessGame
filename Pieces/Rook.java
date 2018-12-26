package ChessGame.Pieces;

import ChessGame.Board;

import java.util.Vector;

public class Rook extends Piece {

    public static int [][] offsets = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public Rook(int xLoc, int yLoc, int side) {
        super("Rook", xLoc, yLoc, side);
    }

    @Override
    public Vector findAvailableMoves(Board myBoard) {
        return entireDirectionMoveCheck(myBoard, offsets);
    }
}