package ChessGame.Tests;

import ChessGame.Board;
import ChessGame.MVC.Controller;
import ChessGame.MVC.Model;
import ChessGame.MVC.View;
import ChessGame.Main;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MVCTests {

    /**
     * Test to check: Make sure controller constructs without any problems
     */
    @Test
    public void controllerTest1() {
        Controller myController = new Controller();

//        Assert.assertEquals(true, myBoard.isPlayer0Checkmated());
    }

    /**
     * Test to check: Make sure controller can call its functions without error
     */
    @Test
    public void controllerTest2() {
        Controller control = new Controller();
        control.doUndo();
        control.boardClicked(10,10);
        control.getPlayer0Name();
        control.getPlayer1Name();
        control.getPossibleMoves();

//        Assert.assertEquals(true, myBoard.isPlayer0Checkmated());
    }

    /**
     * Test to check: Make sure model constructs without any problems
     */
    @Test
    public void modelTest1() {
        Model model = new Model();
    }

    /**
     * Test to check: Make sure model can call its functions without error
     */
    @Test
    public void modelTest2() {
        Model model = new Model();
        model.setFirstMove(false);
        model.undoLastMove();
        model.newDefaultBoard();
        model.newFairyBoard();
        model.changeTurns();
        model.setPlayer1Score(0);
        model.setPlayer0Score(1);
        model.isFirstMove();
        model.setPlayer0Turn(false);

    }

    /**
     * Test to check: Make sure view constructs without any problems
     */
    @Test
    public void viewTest1() {
        Controller myController = new Controller();
        View view = new View(myController);
    }

}
