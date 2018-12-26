package ChessGame.Tests;

import ChessGame.Board;
import ChessGame.Main;
import ChessGame.Pieces.Bishop;
import ChessGame.Pieces.Pawn;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class SetupTests {
    /**
     * Test to check: is the default board set up correctly, correct dimensions, correct number of pieces, etc.
     */
    @Test
    public void pawnPresentAt0By1() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);
        Assert.assertEquals(myBoard.getStringOfPieceAtSpace(0, 1), myBoard.getStringOfPieceAtSpace(1, 1));
        Assert.assertEquals(myBoard.getStringOfPieceAtSpace(0, 1), "Pawn");
        Assert.assertEquals(true, myBoard.getBoardWidth() == 8);
        Assert.assertEquals(true, myBoard.getBoardHeight() == 8);
        int totalPiecesCount = 0;
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(myBoard.getStringOfPieceAtSpace(x, y) != null){
                    totalPiecesCount++;
                }
            }
        }
        Assert.assertEquals(32, totalPiecesCount);
    }

    /**
     * Test to check: placing a piece on top of another piece during setup overwrites the existing piece
     */
    @Test
    public void overwritePieceTest() {
        Board oddBoard = Board.newEmptyBoard(5, 5);

        Pawn pawn2 = new Pawn(1, 4, 1);
        oddBoard.setPieceToSpace(pawn2, pawn2.getxLoc(), pawn2.getyLoc());

        Bishop bishop0 = new Bishop(1, 4, 0);
        oddBoard.setPieceToSpace(bishop0, bishop0.getxLoc(), bishop0.getyLoc());

        Main.printDisplay(oddBoard);

        Assert.assertEquals("Bishop", oddBoard.getStringOfPieceAtSpace(1, 4));

    }

//    /**
//     * Test to check: make sure the controller sets up the board correctly and opens a window for the GUI
//     */
//    @Test
//    public void controllerSetupTest() {
//        Controller myController = new Controller();
//        myController.createAndDisplayGUI();
//
//        Assert.assertEquals(8, myController.myBoard.getBoardHeight());
//        Assert.assertEquals(8, myController.myBoard.getBoardWidth());
//
//    }

    /**
     * Test to check: when a board is made that is less than 3x3, the board will default to 3x3
     */
    @Test
    public void tinyBoardTest() {
        Board myBoard = new Board(2, 3);
        Assert.assertEquals(3, myBoard.getBoardWidth());
        Assert.assertEquals(3, myBoard.getBoardHeight());
    }

    /**
     * Test to check: testing egde cases for defaultSetSide()
     */
    @Test
    public void defaultSetSideTest() {
        Board myBoard = new Board(7, 6);
        myBoard.defaultSetSide(0);
        myBoard.defaultSetSide(-20);
    }

}
