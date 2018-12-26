package ChessGame.Tests;

import ChessGame.Board;
import ChessGame.Main;
import ChessGame.Pieces.*;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class MovingPiecesTests {
    /**
     * Test to check if selecting a Pawn finds the possible moves for that Pawn
     */
    @Test
    public void findPawnMoves() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);
        Piece pawn0 = myBoard.getPieceAtSpace(1, 1);
        Assert.assertEquals(pawn0.getPieceType(), "Pawn");
        Vector possibleMoves0 = pawn0.findAvailableMoves(myBoard);
        int[] testMove = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove[0] == 1);
        Assert.assertEquals(true, testMove[1] == 2);
        testMove = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove[0] == 1);
        Assert.assertEquals(true, testMove[1] == 3);
        Assert.assertEquals(2, possibleMoves0.size());

        Piece pawn1 = myBoard.getPieceAtSpace(0, 1);
        Assert.assertEquals(pawn1.getPieceType(), "Pawn");
        Vector possibleMoves1 = pawn1.findAvailableMoves(myBoard);

        int[] testMove0 = (int[]) possibleMoves1.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 2);
        testMove0 = (int[]) possibleMoves1.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 3);
        Assert.assertEquals(2, possibleMoves0.size());

//        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves1.size());
//        for(int possibleMovesCount = 0; possibleMovesCount < possibleMoves1.size(); possibleMovesCount++){
//            int[] testMove = (int[]) possibleMoves1.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

    }

    /**
     * Test to check: if when given a list of possible moves, can the piece actually move
     */
    @Test
    public void movePiecesForward() {
        Board myBoard = Board.defaultBoardSetup();
        Main.printDisplay(myBoard);

        for(int i = 0; i < 7; i++){
            Piece pawn0 = myBoard.getPieceAtSpace(i, 1);
            Assert.assertEquals(pawn0.getPieceType(), "Pawn");
            Vector possibleMoves0 = pawn0.findAvailableMoves(myBoard);
            Assert.assertEquals(2, possibleMoves0.size());
            myBoard.finalizeMovePiece(myBoard, pawn0, i, 3);
            Assert.assertEquals(pawn0, myBoard.getPieceAtSpace(i, 3));
            Assert.assertEquals(null, myBoard.getPieceAtSpace(i, 1));
            Assert.assertEquals(i, pawn0.getxLoc());
            Assert.assertEquals(3, pawn0.getyLoc());
        }

        Piece pawn0 = myBoard.getPieceAtSpace(7, 1);
        Assert.assertEquals(pawn0.getPieceType(), "Pawn");
        Vector possibleMoves0 = pawn0.findAvailableMoves(myBoard);
        Assert.assertEquals(2, possibleMoves0.size());
        myBoard.finalizeMovePiece(myBoard, pawn0, 7, 2);
        Assert.assertEquals(pawn0, myBoard.getPieceAtSpace(7, 2));
        Assert.assertEquals(null, myBoard.getPieceAtSpace(7, 1));
        Assert.assertEquals(7, pawn0.getxLoc());
        Assert.assertEquals(2, pawn0.getyLoc());

        Main.printDisplay(myBoard);

        Piece king0 = myBoard.getPieceAtSpace(4, 0);
        Assert.assertEquals(king0.getPieceType(), "King");
        Vector possibleMoves2 = king0.findAvailableMoves(myBoard);
        Assert.assertEquals(3, possibleMoves2.size());
        myBoard.finalizeMovePiece(myBoard, king0, 4, 1);
        Assert.assertEquals(null, myBoard.getPieceAtSpace(4, 0));
        Assert.assertEquals(4, king0.getxLoc());
        Assert.assertEquals(1, king0.getyLoc());
        Main.printDisplay(myBoard);

        Piece knight0 = myBoard.getPieceAtSpace(6, 0);
        Assert.assertEquals(knight0.getPieceType(), "Knight");
        System.out.println("Got here10");
        Vector possibleMoves3 = knight0.findAvailableMoves(myBoard);
        System.out.println("Got here11");

        Assert.assertEquals(1, possibleMoves3.size());
        myBoard.finalizeMovePiece(myBoard, knight0, 5, 2);
        Assert.assertEquals(null, myBoard.getPieceAtSpace(6, 0));
        Assert.assertEquals(5, knight0.getxLoc());
        Assert.assertEquals(2, knight0.getyLoc());
        Main.printDisplay(myBoard);
    }

    /**
     * Test to check: can a rook calculate all of it's moves correctly
     */
    @Test
    public void rookCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(5, 5);
        Rook rook0 = new Rook(1, 1, 0);
        oddBoard.setPieceToSpace(rook0, rook0.getxLoc(), rook0.getyLoc());

        Pawn pawn0 = new Pawn(1, 4, 0);
        oddBoard.setPieceToSpace(pawn0, pawn0.getxLoc(), pawn0.getyLoc());

        Pawn pawn1 = new Pawn(3, 1, 1);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());

        Bishop bishop0 = new Bishop(0, 1, 1);
        oddBoard.setPieceToSpace(bishop0, bishop0.getxLoc(), bishop0.getyLoc());

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = rook0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "Rook" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(6, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 3);

        testMove0 = (int[]) possibleMoves0.elementAt(3);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(4);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(5);
        Assert.assertEquals(true, testMove0[0] == 3);
        Assert.assertEquals(true, testMove0[1] == 1);
    }

    /**
     * Test to check: can a bishop calculate all of it's moves correctly
     */
    @Test
    public void bishopCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(5, 5);
        Bishop bishop0 = new Bishop(1, 1, 0);
        oddBoard.setPieceToSpace(bishop0, bishop0.getxLoc(), bishop0.getyLoc());

        Pawn pawn0 = new Pawn(0, 2, 0);
        oddBoard.setPieceToSpace(pawn0, pawn0.getxLoc(), pawn0.getyLoc());

        Pawn pawn1 = new Pawn(4, 4, 1);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = bishop0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "Bishop" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(5, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 3);
        Assert.assertEquals(true, testMove0[1] == 3);

        testMove0 = (int[]) possibleMoves0.elementAt(3);
        Assert.assertEquals(true, testMove0[0] == 4);
        Assert.assertEquals(true, testMove0[1] == 4);

        testMove0 = (int[]) possibleMoves0.elementAt(4);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 0);

    }

    /**
     * Test to check: can a queen calculate all of it's moves correctly
     */
    @Test
    public void queenCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(4, 4);
        Queen queen0 = new Queen(1, 1, 0);
        oddBoard.setPieceToSpace(queen0, queen0.getxLoc(), queen0.getyLoc());

        Pawn pawn0 = new Pawn(0, 2, 0);
        oddBoard.setPieceToSpace(pawn0, pawn0.getxLoc(), pawn0.getyLoc());

        Pawn pawn1 = new Pawn(3, 3, 1);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());

        Bishop bishop0 = new Bishop(1, 3, 0);
        oddBoard.setPieceToSpace(bishop0, bishop0.getxLoc(), bishop0.getyLoc());

        Bishop bishop1 = new Bishop(2, 0, 1);
        oddBoard.setPieceToSpace(bishop1, bishop1.getxLoc(), bishop1.getyLoc());

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = queen0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(9, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(3);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(4);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(5);
        Assert.assertEquals(true, testMove0[0] == 3);
        Assert.assertEquals(true, testMove0[1] == 3);

        testMove0 = (int[]) possibleMoves0.elementAt(6);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(7);
        Assert.assertEquals(true, testMove0[0] == 3);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(8);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 0);

    }

    /**
     * Test to check: can a king calculate all of it's moves correctly
     */
    @Test
    public void kingCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(4, 4);
        King king0 = new King(1, 1, 0);
        oddBoard.setPieceToSpace(king0, king0.getxLoc(), king0.getyLoc());

        Pawn pawn0 = new Pawn(1, 2, 0);
        oddBoard.setPieceToSpace(pawn0, pawn0.getxLoc(), pawn0.getyLoc());

        Pawn pawn1 = new Pawn(2, 1, 1);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = king0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "King" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(7, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(3);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(4);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(5);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(6);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 0);

    }

    /**
     * Test to check: can a knight calculate all of it's moves correctly
     */
    @Test
    public void knightCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(4, 4);
        Knight knight0 = new Knight(2, 2, 0);
        oddBoard.setPieceToSpace(knight0, knight0.getxLoc(), knight0.getyLoc());

        Pawn pawn0 = new Pawn(0, 3, 0);
        oddBoard.setPieceToSpace(pawn0, pawn0.getxLoc(), pawn0.getyLoc());

        Pawn pawn1 = new Pawn(3, 0, 1);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = knight0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "Knight" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(3, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 3);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 1);

    }

    /**
     * Test to check: can a pawn calculate all of it's moves correctly
     */
    @Test
    public void pawnCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(6, 6);
        Pawn pawn0 = new Pawn(1, 0, 0);
        oddBoard.setPieceToSpace(pawn0, pawn0.getxLoc(), pawn0.getyLoc());

        Pawn pawn1 = new Pawn(3, 1, 0);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());

        Pawn pawn2 = new Pawn(5, 1, 0);
        oddBoard.setPieceToSpace(pawn2, pawn2.getxLoc(), pawn2.getyLoc());

        Bishop bishop0 = new Bishop(0, 1, 1);
        oddBoard.setPieceToSpace(bishop0, bishop0.getxLoc(), bishop0.getyLoc());

        Pawn pawn3 = new Pawn(1, 4, 1);
        oddBoard.setPieceToSpace(pawn3, pawn3.getxLoc(), pawn3.getyLoc());

        Pawn pawn4 = new Pawn(2, 5, 1);
        oddBoard.setPieceToSpace(pawn4, pawn4.getxLoc(), pawn4.getyLoc());

        Queen queen0 = new Queen(2, 3, 0);
        oddBoard.setPieceToSpace(queen0, queen0.getxLoc(), queen0.getyLoc());

        Queen queen1 = new Queen(3, 2, 1);
        oddBoard.setPieceToSpace(queen1, queen1.getxLoc(), queen1.getyLoc());

        Pawn pawn5 = new Pawn(5, 4, 1);
        oddBoard.setPieceToSpace(pawn5, pawn5.getxLoc(), pawn5.getyLoc());

        Queen queen2 = new Queen(4, 3, 0);
        oddBoard.setPieceToSpace(queen2, queen2.getxLoc(), queen2.getyLoc());

        Pawn pawn6 = new Pawn(3, 5, 1);
        oddBoard.setPieceToSpace(pawn6, pawn6.getxLoc(), pawn6.getyLoc());

        Main.printDisplay(oddBoard);


        Vector possibleMoves0 = pawn0.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + pawn0.getxLoc() + ", " + pawn0.getyLoc() + ")" + ": " + possibleMoves0.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(2, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 1);


        Vector possibleMoves1 = pawn1.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + pawn1.getxLoc() + ", " + pawn1.getyLoc() + ")" + ": " + possibleMoves1.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves1.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves1.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(0, possibleMoves1.size());


        Vector possibleMoves2 = pawn2.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + pawn2.getxLoc() + ", " + pawn2.getyLoc() + ")" + ": " + possibleMoves2.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves2.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves2.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(2, possibleMoves2.size());

        testMove0 = (int[]) possibleMoves2.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 5);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves2.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 5);
        Assert.assertEquals(true, testMove0[1] == 3);


        Vector possibleMoves3 = pawn3.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + pawn3.getxLoc() + ", " + pawn3.getyLoc() + ")" + ": " + possibleMoves3.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves3.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves3.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(3, possibleMoves3.size());

        testMove0 = (int[]) possibleMoves3.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 3);

        testMove0 = (int[]) possibleMoves3.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves3.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 3);


        Vector possibleMoves4 = pawn4.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + pawn4.getxLoc() + ", " + pawn4.getyLoc() + ")" + ": " + possibleMoves4.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves4.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves4.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(1, possibleMoves4.size());

        testMove0 = (int[]) possibleMoves4.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 4);


        Vector possibleMoves5 = pawn5.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + pawn5.getxLoc() + ", " + pawn5.getyLoc() + ")" + ": " + possibleMoves5.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves5.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves5.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(3, possibleMoves5.size());

        testMove0 = (int[]) possibleMoves5.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 5);
        Assert.assertEquals(true, testMove0[1] == 3);

        testMove0 = (int[]) possibleMoves5.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 5);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves5.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 4);
        Assert.assertEquals(true, testMove0[1] == 3);


        Vector possibleMoves6 = pawn6.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "Pawn" + " at " + "(" + pawn6.getxLoc() + ", " + pawn6.getyLoc() + ")" + ": " + possibleMoves6.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves6.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves6.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(1, possibleMoves6.size());

        testMove0 = (int[]) possibleMoves6.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 3);
        Assert.assertEquals(true, testMove0[1] == 4);
    }

    /**
     * Test to check: can a piece capture another piece
     */
    @Test
    public void pawnAgainstPawnOnSmallBoard() {
        Board oddBoard = Board.newEmptyBoard(4, 4);
        Pawn pawn0 = new Pawn(1, 1, 0);
        oddBoard.setPieceToSpace(pawn0, pawn0.getxLoc(), pawn0.getyLoc());
        Pawn pawn1 = new Pawn(1, 2, 1);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());
        Pawn pawn2 = new Pawn(2, 2, 1);
        oddBoard.setPieceToSpace(pawn2, pawn2.getxLoc(), pawn2.getyLoc());
        Main.printDisplay(oddBoard);

//            Vector possibleMoves0 = pawn0.findAvailableMoves(oddBoard);
//            System.out.println("Possible moves for " + "Pawn" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//            for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//                int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//                System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//            }

        oddBoard.finalizeMovePiece(oddBoard, pawn0, 2, 2);
        Main.printDisplay(oddBoard);
    }

    /**
     * Test to check: out of bounds moves are not found to be valid
     */
    @Test
    public void kingMovingSmallBoard() {
        Board oddBoard = Board.newEmptyBoard(4, 4);
        King king0 = new King(0, 0, 0);
        oddBoard.setPieceToSpace(king0, king0.getxLoc(), king0.getyLoc());
        Main.printDisplay(oddBoard);

        Pawn pawn1 = new Pawn(1, 1, 1);
        oddBoard.setPieceToSpace(pawn1, pawn1.getxLoc(), pawn1.getyLoc());
        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = king0.findAvailableMoves(oddBoard);

        System.out.println("Possible moves for " + "King" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
        }

        Assert.assertEquals(3, possibleMoves0.size());

        int[] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(0, testMove0[0]);
        Assert.assertEquals(1, testMove0[1]);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(1, testMove0[0]);
        Assert.assertEquals(1, testMove0[1]);

        testMove0 = (int[]) possibleMoves0.elementAt(2);
        Assert.assertEquals(1, testMove0[0]);
        Assert.assertEquals(0, testMove0[1]);

    }

    /**
     * Test to check: can a bulldozer calculate all of it's moves correctly
     */
    @Test
    public void bulldozerCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(5, 5);

        oddBoard.createAndPlacePiece("Bulldozer", 1, 0, 0);
        Piece bulldozer0 = oddBoard.getPieceAtSpace(1, 0);

        oddBoard.createAndPlacePiece("Bulldozer", 4, 4, 1);
        Piece bulldozer1 = oddBoard.getPieceAtSpace(4, 4);

        oddBoard.createAndPlacePiece("Bulldozer", 4, 0, 0);
        Piece bulldozer2 = oddBoard.getPieceAtSpace(4, 0);

        oddBoard.createAndPlacePiece("Bulldozer", 1, 4, 1);
        Piece bulldozer3 = oddBoard.getPieceAtSpace(1, 4);

        oddBoard.createAndPlacePiece("Pawn", 1, 1, 1);
        oddBoard.createAndPlacePiece("Pawn", 0, 1, 1);
        oddBoard.createAndPlacePiece("Pawn", 2, 1, 1);
        oddBoard.createAndPlacePiece("Pawn", 4, 1, 1);
        oddBoard.createAndPlacePiece("Pawn", 1, 3, 0);

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = bulldozer0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "Bishop" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(1, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 1);


        Vector possibleMoves1 = bulldozer1.findAvailableMoves(oddBoard);
        Assert.assertEquals(1, possibleMoves1.size());

        int [] testMove1 = (int[]) possibleMoves1.elementAt(0);
        Assert.assertEquals(true, testMove1[0] == 4);
        Assert.assertEquals(true, testMove1[1] == 3);


        Vector possibleMoves2 = bulldozer2.findAvailableMoves(oddBoard);
        Assert.assertEquals(1, possibleMoves2.size());

        int [] testMove2 = (int[]) possibleMoves2.elementAt(0);
        Assert.assertEquals(true, testMove2[0] == 4);
        Assert.assertEquals(true, testMove2[1] == 1);


        Vector possibleMoves3 = bulldozer3.findAvailableMoves(oddBoard);
        Assert.assertEquals(1, possibleMoves3.size());

        int [] testMove3 = (int[]) possibleMoves3.elementAt(0);
        Assert.assertEquals(true, testMove3[0] == 1);
        Assert.assertEquals(true, testMove3[1] == 3);
    }

    /**
     * Test to check: can a jester calculate all of it's moves correctly
     */
    @Test
    public void jesterCalculatedMoves() {
        Board oddBoard = Board.newEmptyBoard(4, 4);

        oddBoard.createAndPlacePiece("Jester", 1, 1, 0);
        Piece jester0 = oddBoard.getPieceAtSpace(1, 1);

        oddBoard.createAndPlacePiece("Pawn", 1, 2, 0);

        oddBoard.createAndPlacePiece("Pawn", 2, 1, 1);

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = jester0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "King" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(7, possibleMoves0.size());

        int [] testMove0 = (int[]) possibleMoves0.elementAt(0);
        Assert.assertEquals(true, testMove0[0] == 1);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(1);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 0);

        testMove0 = (int[]) possibleMoves0.elementAt(2);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(3);
        Assert.assertEquals(true, testMove0[0] == 0);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(4);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 2);

        testMove0 = (int[]) possibleMoves0.elementAt(5);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 1);

        testMove0 = (int[]) possibleMoves0.elementAt(6);
        Assert.assertEquals(true, testMove0[0] == 2);
        Assert.assertEquals(true, testMove0[1] == 0);

    }


    /**
     * Test to check: Confirms that the opponent king is not perceived as an available move (cannot be taken)
     */
    @Test
    public void oppositeKingNotAnAvailableMove() {
        Board oddBoard = Board.newEmptyBoard(8, 8);
        oddBoard.createAndPlacePiece("King", 5, 4, 0);
        oddBoard.createAndPlacePiece("Rook", 7, 0, 0);
        oddBoard.createAndPlacePiece("King", 7, 4, 1);
        oddBoard.createAndPlacePiece("Pawn", 5, 0, 0);
        Main.printDisplay(oddBoard);

        Piece rook1 = oddBoard.getPieceAtSpace(7, 0);
        Vector possibleMoves0 = rook1.findAvailableMoves(oddBoard);
        Assert.assertEquals(4, possibleMoves0.size());
    }

    /**
     * Test to check: can a king move into check
     */
    @Test
    public void kingCannotPlaceItselfInCheck() {
        Board oddBoard = Board.newEmptyBoard(4, 4);

        oddBoard.createAndPlacePiece("King", 1, 1, 0);
        Piece king0 = oddBoard.getPieceAtSpace(1, 1);

        oddBoard.createAndPlacePiece("Knight", 1, 2, 1);
        Piece knight0 = oddBoard.getPieceAtSpace(1, 2);

        oddBoard.createAndPlacePiece("Pawn", 2, 1, 0);
        Piece pawn1 = oddBoard.getPieceAtSpace(2, 1);

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = king0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "King" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(5, possibleMoves0.size());
    }

    /**
     * Test to check: not allowed to move a piece which places your own king in check
     */
    @Test
    public void friendlyPieceCannotPlaceItsKingInCheck() {
        Board oddBoard = Board.newEmptyBoard(4, 4);

        oddBoard.createAndPlacePiece("King", 1, 0, 0);
        Piece king0 = oddBoard.getPieceAtSpace(1, 0);

        oddBoard.createAndPlacePiece("Rook", 1, 2, 1);
        Piece rook0 = oddBoard.getPieceAtSpace(1, 2);

        oddBoard.createAndPlacePiece("Bishop", 1, 1, 0);
        Piece bishop0 = oddBoard.getPieceAtSpace(1, 1);

        Main.printDisplay(oddBoard);

        Vector possibleMoves0 = bishop0.findAvailableMoves(oddBoard);

//        System.out.println("Possible moves for " + "King" + " at " + "(" + 0 + ", " + 1 + ")" + ": " + possibleMoves0.size());
//        for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves0.size(); possibleMovesCount++) {
//            int[] testMove = (int[]) possibleMoves0.elementAt(possibleMovesCount);
//            System.out.println("(" + testMove[0] + ", " + testMove[1] + ")");
//        }

        Assert.assertEquals(0, possibleMoves0.size());
    }
}
