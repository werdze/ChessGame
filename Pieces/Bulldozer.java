package ChessGame.Pieces;

import ChessGame.Board;

import java.util.Vector;

public class Bulldozer extends Piece {

    public Bulldozer(int xLoc, int yLoc, int side) {
        super("Bulldozer", xLoc, yLoc, side);
    }

    @Override
    public Vector findAvailableMoves(Board myBoard) {
        Vector possibleMoves = new Vector();
        int yMoveForwardOffset = getSide() == 0 ? 1 : -1;      //+1 or -1 y movement depending on which side the pawn belongs to

        addSpaceToVecIfValidMove(myBoard, possibleMoves, 0, yMoveForwardOffset);

        return possibleMoves;
    }
}
