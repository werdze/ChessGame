package ChessGame.Tests;

import ChessGame.Board;
import ChessGame.Main;
import ChessGame.Pieces.Piece;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class CheckmateAndStalemateTests {
    /**
     * Test to check: Fool's Mate
     */
    @Test
    public void checkmateTest1() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(5, 1), 5, 2);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(4, 6), 4, 4);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(6, 1), 6, 3);
        Main.printDisplay(myBoard);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(3, 7), 7, 3);
        Main.printDisplay(myBoard);

        Assert.assertEquals(true, myBoard.isPlayer0Checkmated());
    }

    /**
     * Test to check: Checkmate with a rook
     */
    @Test
    public void checkmateTest2() {
        Board oddBoard = Board.newEmptyBoard(8, 8);
        oddBoard.createAndPlacePiece("King", 5, 4, 0);
        oddBoard.createAndPlacePiece("Rook", 0, 0, 0);
        oddBoard.createAndPlacePiece("King", 7, 4, 1);
        Main.printDisplay(oddBoard);
        oddBoard.finalizeMovePiece(oddBoard, oddBoard.getPieceAtSpace(0, 0), 7, 0);
        Main.printDisplay(oddBoard);

        Assert.assertEquals(true, oddBoard.isPlayer1Checkmated());
    }

    /**
     * Test to check: king is in check, but not checkmate. Another piece can move to block the check
     */
    @Test
    public void checkmateTest3() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(5, 1), 5, 2);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(4, 6), 4, 4);
//        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(6, 1), 6, 3);
        Main.printDisplay(myBoard);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(3, 7), 7, 3);
        Main.printDisplay(myBoard);

        Assert.assertEquals(false, myBoard.isPlayer0Checkmated());
    }

    /**
     * Test to check: king is in check, but not checkmate. King can move itself out of check
     */
    @Test
    public void checkmateTest4() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(5, 1), 5, 2);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(4, 6), 4, 4);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(4, 1), 4, 2);

//        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(6, 1), 6, 3);
        Main.printDisplay(myBoard);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(3, 7), 7, 3);
        Main.printDisplay(myBoard);

        Assert.assertEquals(false, myBoard.isPlayer0Checkmated());
    }


    /**
     * Test to check: stalemate when only two kings left
     */
    @Test
    public void stalemateTest1() {
        Board oddBoard = Board.newEmptyBoard(8, 8);
        oddBoard.createAndPlacePiece("King", 0, 0, 0);
        oddBoard.createAndPlacePiece("Bishop", 0, 1, 1);
        oddBoard.createAndPlacePiece("King", 6, 6, 1);
        Main.printDisplay(oddBoard);
        Assert.assertEquals(1, oddBoard.side0Pieces.size());
        Assert.assertEquals(2, oddBoard.side1Pieces.size());
        oddBoard.finalizeMovePiece(oddBoard, oddBoard.getPieceAtSpace(0, 0), 0, 1);
        Main.printDisplay(oddBoard);

        Assert.assertEquals(1, oddBoard.side0Pieces.size());
        Assert.assertEquals(1, oddBoard.side1Pieces.size());

        Assert.assertEquals(true, oddBoard.isStalemate());
    }

    /**
     * Test to check: stalemate when king is only piece left on side, it is not in check, it can't move
     */
    @Test
    public void stalemateTest2() {
        Board oddBoard = Board.newEmptyBoard(8, 8);
        oddBoard.createAndPlacePiece("King", 5, 7, 0);
        oddBoard.createAndPlacePiece("Bishop", 3, 4, 1);
        oddBoard.createAndPlacePiece("King", 5, 5, 1);
        Main.printDisplay(oddBoard);

        Piece king0 = oddBoard.getPieceAtSpace(5, 7);
        Vector possibleMoves0 = king0.findAvailableMoves(oddBoard);
        Assert.assertEquals(1, possibleMoves0.size());

        oddBoard.finalizeMovePiece(oddBoard, oddBoard.getPieceAtSpace(3, 4), 5, 6);
        Main.printDisplay(oddBoard);

        Vector possibleMoves1 = king0.findAvailableMoves(oddBoard);
        Assert.assertEquals(0, possibleMoves1.size());

        Assert.assertEquals(true, oddBoard.isStalemate());
    }

    /**
     * Test to check: stalemate when king is not in check and no pieces on king side can be moved
     */
    @Test
    public void stalemateTest3() {
        Board oddBoard = Board.newEmptyBoard(8, 8);
        oddBoard.createAndPlacePiece("King", 5, 7, 0);
        oddBoard.createAndPlacePiece("Bishop", 3, 4, 1);
        oddBoard.createAndPlacePiece("King", 5, 5, 1);
        oddBoard.createAndPlacePiece("Pawn", 0, 2, 1);
        oddBoard.createAndPlacePiece("Pawn", 0, 1, 0);
        Main.printDisplay(oddBoard);
        oddBoard.finalizeMovePiece(oddBoard, oddBoard.getPieceAtSpace(3, 4), 5, 6);
        Main.printDisplay(oddBoard);

        Piece king0 = oddBoard.getPieceAtSpace(5, 7);
        Vector possibleMoves0 = king0.findAvailableMoves(oddBoard);
        Assert.assertEquals(0, possibleMoves0.size());

        Assert.assertEquals(true, oddBoard.isStalemate());
    }

    /**
     * Test to check: not stalemate when king is not in check and a pawn can be moved
     */
    @Test
    public void stalemateTest4() {
        Board oddBoard = Board.newEmptyBoard(8, 8);
        oddBoard.createAndPlacePiece("King", 5, 7, 0);
        oddBoard.createAndPlacePiece("Bishop", 3, 4, 1);
        oddBoard.createAndPlacePiece("King", 5, 5, 1);
        oddBoard.createAndPlacePiece("Pawn", 0, 3, 1);
        oddBoard.createAndPlacePiece("Pawn", 0, 1, 0);
        Main.printDisplay(oddBoard);

        oddBoard.finalizeMovePiece(oddBoard, oddBoard.getPieceAtSpace(3, 4), 5, 6);
        Main.printDisplay(oddBoard);

        Assert.assertEquals(false, oddBoard.isStalemate());
    }

}
