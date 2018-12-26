package ChessGame.Pieces;

import ChessGame.Board;
import java.util.*;

public class Pawn extends Piece {

    public Pawn(int xLoc, int yLoc, int side){
        super("Pawn", xLoc, yLoc, side);
    }

    @Override
    public Vector findAvailableMoves(Board myBoard) {
        Vector possibleMoves = new Vector();
        int yMoveForwardOffset = getSide() == 0 ? 1 : -1;      //+1 or -1 y movement depending on which side the pawn belongs to

        //case: can move forward 1
        if(isWithinBoundsAfterMove(myBoard, 0, yMoveForwardOffset)){
            if(myBoard.getStringOfPieceAtSpace(getxLoc(), getyLoc() + yMoveForwardOffset) == null){
                addSpaceToVecIfValidMove(myBoard, possibleMoves, 0, yMoveForwardOffset);
            }
        }

        //case: can move forward 2
        if(isWithinBoundsAfterMove(myBoard, 0, yMoveForwardOffset * 2) && (getyLoc() == 1 || getyLoc() == myBoard.getBoardHeight() - 2)){
            if(myBoard.getStringOfPieceAtSpace(getxLoc(), getyLoc() + yMoveForwardOffset) == null){
                if(myBoard.getStringOfPieceAtSpace(getxLoc(), getyLoc() + yMoveForwardOffset * 2) == null){
                    addSpaceToVecIfValidMove(myBoard, possibleMoves, 0, yMoveForwardOffset * 2);
                }
            }
        }

        //case: can capture on left diagonal
        if(isWithinBoundsAfterMove(myBoard, -1, yMoveForwardOffset)){
            int pieceToCheckSide = myBoard.getSideOfPieceAtSpace(getxLoc() - 1, getyLoc() + yMoveForwardOffset);
            if(pieceToCheckSide != getSide() && pieceToCheckSide != -1)
                addSpaceToVecIfValidMove(myBoard, possibleMoves, -1, yMoveForwardOffset);
        }

        //case: can capture on right diagonal
        if(isWithinBoundsAfterMove(myBoard, 1, yMoveForwardOffset)){
            int pieceToCheckSide = myBoard.getSideOfPieceAtSpace(getxLoc() + 1, getyLoc() + yMoveForwardOffset);
            if(pieceToCheckSide != getSide() && pieceToCheckSide != -1)
                addSpaceToVecIfValidMove(myBoard, possibleMoves, 1, yMoveForwardOffset);
        }

        return possibleMoves;
    }
}
