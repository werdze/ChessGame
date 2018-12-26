package ChessGame;

import ChessGame.Pieces.Piece;

public class StoreMoves {

    private boolean isPlayer0KingInCheck;
    private boolean isPlayer1KingInCheck;
    private boolean hasCheckStatusChangedToTrue;
    private boolean isPlayer0Checkmated;
    private boolean isPlayer1Checkmated;
    private boolean isStalemate;

    private Piece pieceToMove;
    private int pieceToMoveXOrigin;
    private int pieceToMoveYOrigin;
    private int xDest;
    private int yDest;
    private Piece pieceToReplace;

    /**
     * Creates a new StoreMoves object to hold the information needed to undo a move
     */
    public StoreMoves(boolean isPlayer0KingInCheck, boolean isPlayer1KingInCheck, boolean hasCheckStatusChangedToTrue, boolean isPlayer0Checkmated, boolean isPlayer1Checkmated, boolean isStalemate, Piece pieceToMove, int pieceToMoveXOrigin, int pieceToMoveYOrigin, int xDest, int yDest, Piece pieceToReplace){
        this.isPlayer0KingInCheck = isPlayer0KingInCheck;
        this.isPlayer1KingInCheck = isPlayer1KingInCheck;
        this.hasCheckStatusChangedToTrue = hasCheckStatusChangedToTrue;
        this.isPlayer0Checkmated = isPlayer0Checkmated;
        this.isPlayer1Checkmated = isPlayer1Checkmated;
        this.isStalemate = isStalemate;
        this.pieceToMove = pieceToMove;
        this.pieceToMoveXOrigin = pieceToMoveXOrigin;
        this.pieceToMoveYOrigin = pieceToMoveYOrigin;
        this.xDest = xDest;
        this.yDest = yDest;
        this.pieceToReplace = pieceToReplace;
    }

//    GETTERS AND SETTERS

    public boolean isPlayer0KingInCheck() {
        return isPlayer0KingInCheck;
    }

    public boolean isPlayer1KingInCheck() {
        return isPlayer1KingInCheck;
    }

    public boolean HasCheckStatusChangedToTrue() {
        return hasCheckStatusChangedToTrue;
    }

    public boolean isPlayer0Checkmated() {
        return isPlayer0Checkmated;
    }

    public boolean isPlayer1Checkmated() {
        return isPlayer1Checkmated;
    }

    public boolean isStalemated() {
        return isStalemate;
    }

    public Piece getPieceToMove() {
        return pieceToMove;
    }

    public int getPieceToMoveXOrigin() {
        return pieceToMoveXOrigin;
    }

    public int getPieceToMoveYOrigin() {
        return pieceToMoveYOrigin;
    }

    public int getxDest() {
        return xDest;
    }

    public int getyDest() {
        return yDest;
    }

    public Piece getPieceToReplace() {
        return pieceToReplace;
    }
}
