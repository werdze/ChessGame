package ChessGame.MVC;

import ChessGame.Board;
import ChessGame.Main;
import ChessGame.Pieces.Piece;
import ChessGame.StoreMoves;

public class Model {

    private Board myBoard;
    private int player0Score;
    private int player1Score;
    private boolean player0Turn;
    /**
     * firstMove is used to prevent undo-ing past the first move
     */
    private boolean firstMove = true;

    /**
     * Constructor for the Model
     */
    public Model(){
        myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);
        player0Score = 0;
        player1Score = 0;
        player0Turn = true;
    }

    /**
     * Creates and returns a default board
     */
    public Board newDefaultBoard(){
        setFirstMove(true);
        this.myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);
        return myBoard;
    }

    /**
     * Creates and returns a fairy board
     */
    public Board newFairyBoard(){
        setFirstMove(true);
        myBoard = Board.fairyBoardSetup();
        Main.printDisplay(myBoard);
        return myBoard;
    }

    /**
     * Stores all of the current move info in a StoreMoves object and pushes it onto the undoStack
     */
    public void storeMove(Piece pieceToMove, int xDest, int yDest){
        StoreMoves lastMoveInfo = new StoreMoves(myBoard.isPlayer0KingInCheck(), myBoard.isPlayer1KingInCheck(), myBoard.hasCheckStatusChangedToTrue(), myBoard.isPlayer0Checkmated(), myBoard.isPlayer1Checkmated(), myBoard.isStalemate(), pieceToMove, pieceToMove.getxLoc(), pieceToMove.getyLoc(), xDest, yDest, myBoard.getPieceAtSpace(xDest, yDest));
        myBoard.getUndoStack().push(lastMoveInfo);
    }

    /**
     * Pops a StoreMoves object off of the undoStack and sets all necessary board info back to that state
     */
    public void undoLastMove() {
        if(myBoard.getUndoStack().size() > 0){
            StoreMoves lastMoveInfo = (StoreMoves) myBoard.getUndoStack().pop();

            boolean isPlayer0KingInCheck = lastMoveInfo.isPlayer0KingInCheck();
            boolean isPlayer1KingInCheck = lastMoveInfo.isPlayer1KingInCheck();
            boolean hasCheckStatusChangedToTrue = lastMoveInfo.HasCheckStatusChangedToTrue();
            boolean isPlayer0Checkmated = lastMoveInfo.isPlayer0Checkmated();
            boolean isPlayer1Checkmated = lastMoveInfo.isPlayer1Checkmated();
            boolean isStalemate = lastMoveInfo.isStalemated();

            myBoard.setPlayer0KingInCheck(isPlayer0KingInCheck);
            myBoard.setPlayer1KingInCheck(isPlayer1KingInCheck);
            myBoard.setHasCheckStatusChangedToTrue(hasCheckStatusChangedToTrue);
            myBoard.setPlayer0Checkmated(isPlayer0Checkmated);
            myBoard.setPlayer1Checkmated(isPlayer1Checkmated);
            myBoard.setStalemate(isStalemate);

            Piece capturedPiece = (Piece) lastMoveInfo.getPieceToReplace();
            int capturedXDest = lastMoveInfo.getxDest();
            int capturedYDest = lastMoveInfo.getyDest();
            Piece movedPiece = (Piece) lastMoveInfo.getPieceToMove();
            int movedPieceXOrigin = lastMoveInfo.getPieceToMoveXOrigin();
            int movedPieceYOrigin = lastMoveInfo.getPieceToMoveYOrigin();

            //revert board
            myBoard.setPieceToSpace(capturedPiece, capturedXDest, capturedYDest);
            myBoard.setPieceToSpace(movedPiece, movedPieceXOrigin, movedPieceYOrigin);
            movedPiece.setxLoc(movedPieceXOrigin);
            movedPiece.setyLoc(movedPieceYOrigin);
            if(myBoard.getUndoStack().size() == 0)
                setFirstMove(true);
        }
    }

    /**
     * Changes the player0Turn flag to the other player
     */
    public void changeTurns() {
        if(player0Turn)
            player0Turn = false;
        else
            player0Turn = true;
    }

//    GETTERS AND SETTERS

    public Board getMyBoard() {
        return myBoard;
    }

    public int getPlayer0Score() {
        return player0Score;
    }

    public void setPlayer0Score(int player0Score) {
        this.player0Score = player0Score;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public boolean isPlayer0Turn() {
        return player0Turn;
    }

    public void setPlayer0Turn(boolean player0Turn) {
        this.player0Turn = player0Turn;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
