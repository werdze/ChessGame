package ChessGame.Tests;

import ChessGame.Board;
import ChessGame.Main;
import ChessGame.Pieces.Pawn;
import ChessGame.Pieces.Piece;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class OtherTests {
    /**
     * Test to check: make sure charForDisplay() method in Main works as intended
     */
    @Test
    public void charForDisplayWorksTest() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);

        Assert.assertEquals("R", Main.charForDisplay(myBoard, 0,0));
        Assert.assertEquals("P", Main.charForDisplay(myBoard, 0,1));
        Assert.assertEquals("N", Main.charForDisplay(myBoard, 1,0));
        Assert.assertEquals("B", Main.charForDisplay(myBoard, 2,7));
    }

    /**
     * Test to check: make sure edge cases don't break createAndPlacePiece()
     */
    @Test
    public void createPieceEdgeCasesTest() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);

        myBoard.createAndPlacePiece("Nonexistant", 1, 1, 1);
        myBoard.createAndPlacePiece("King", 100, 1, 1);
        myBoard.createAndPlacePiece("King", 1, -11, 1);
        myBoard.createAndPlacePiece("King", 1, 1, -5);

        Main.printDisplay(myBoard);
    }

    /**
     * Test to check: make sure edge cases don't break setStalemateStatus()
     */
    @Test
    public void setStalemateStatusEdgeCasesTest() {
        Board oddBoard = Board.newEmptyBoard(5, 5);

        Pawn pawn2 = new Pawn(1, 4, 1);
        oddBoard.setPieceToSpace(pawn2, pawn2.getxLoc(), pawn2.getyLoc());

        Main.printDisplay(oddBoard);

        oddBoard.setStalemateStatus(oddBoard, 0);
    }

    /**
     * Test to check: king is in check. Make sure none of my pieces are allowed to move, unless they can get the king out of check
     */
    @Test
    public void cannotMovePiecesWhileInCheckTest() {
        Board myBoard = Board.defaultBoardSetup();
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(5, 1), 5, 2);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(4, 6), 4, 4);
        myBoard.finalizeMovePiece(myBoard, myBoard.getPieceAtSpace(3, 7), 7, 3);
        Main.printDisplay(myBoard);

        Piece pawn0 = myBoard.getPieceAtSpace(0,1);
        Vector possibleMoves0 = pawn0.findAvailableMoves(myBoard);
        Assert.assertEquals(0, possibleMoves0.size());

        Piece pawn1 = myBoard.getPieceAtSpace(6,1);
        Vector possibleMoves1 = pawn1.findAvailableMoves(myBoard);
        Assert.assertEquals(1, possibleMoves1.size());

        Piece knight0 = myBoard.getPieceAtSpace(1,0);
        Vector possibleMoves2 = knight0.findAvailableMoves(myBoard);
        Assert.assertEquals(0, possibleMoves2.size());

        Piece king0 = myBoard.getPieceAtSpace(4,0);
        Vector possibleMoves3 = king0.findAvailableMoves(myBoard);
        Assert.assertEquals(0, possibleMoves3.size());

        Assert.assertEquals(false, myBoard.isPlayer0Checkmated());
    }

}
