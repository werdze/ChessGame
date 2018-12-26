package ChessGame.MVC;

import ChessGame.Board;
import ChessGame.Pieces.Piece;

import java.util.Vector;

import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;


public class Controller{
    private Board myBoard;
    private boolean isDefaultBoard;
    public Model model;
    private View view;
    private Piece currPiece = null;
    private Vector possibleMoves = null;

    private boolean onePlayerConfirmedRestart = false;
    private String player0Name;
    private String player1Name;

    /**
     * Constructor for the Controller
     * Creates a model and view as well
     * Also calls setPlayerNames to give the user popup dialogs to enter their names
     */
    public Controller(){
        this.model = new Model();
        isDefaultBoard = true;
        myBoard = model.getMyBoard();
        setPlayerNames();
        this.view = new View(this);
        view.createGUI(myBoard);
    }

    /**
     * Called when the Default Board button is clicked
     */
    public void doNewDefaultBoard() {
        model.setPlayer0Turn(true);
        setMyBoard(model.newDefaultBoard());
        isDefaultBoard = true;
        redrawBoard();
        redrawScoreSection();
    }

    /**
     * Called when the Fairy Board button is clicked
     */
    public void doNewFairyBoard() {
        model.setPlayer0Turn(true);
        setMyBoard(model.newFairyBoard());
        isDefaultBoard = false;
        redrawBoard();
        redrawScoreSection();
    }

    /**
     * Called when the Restart button is clicked
     * Only restarts the game if both players click this button
     */
    public void doRestart(){
        if(!onePlayerConfirmedRestart){
            onePlayerConfirmedRestart = true;
            model.changeTurns();
            redrawScoreSection();
        }
        else {
            if (isDefaultBoard) {
                myBoard = model.newDefaultBoard();
            } else {
                myBoard = model.newFairyBoard();
            }
            model.setPlayer0Turn(true);
            redrawBoard();
            redrawScoreSection();
        }
    }

    /**
     * Called when the Forfeit button is clicked
     * Adds 1 to the other player's score and restarts the game
     */
    public void doForfeit() {
        boolean addToPlayer0 = !model.isPlayer0Turn();
        addOneToPlayerScore(addToPlayer0);

        redrawScoreSection();
        doNewDefaultBoard();
        redrawBoard();
    }

    /**
     * Called when the Undo button is clicked
     * If the game did not end, and it is not the first move, undoes the last move made
     */
    public void doUndo() {
        if(!(myBoard.isPlayer0Checkmated() || myBoard.isPlayer1Checkmated() || myBoard.isStalemate())){
            if(!model.isFirstMove()){
                model.undoLastMove();
                model.changeTurns();
                redrawScoreSection();
                redrawBoard();
            }
        }
    }

    /**
     * Called when the Change Names button is clicked
     * Brings the dialog popup back to allow users to re-enter their names
     */
    public void doChangeNames() {
        setPlayerNames();
        redrawScoreSection();
    }

    /**
     * Called when the Board panel is clicked on
     * Converts the click position on the panel to x and y locations of the board
     * If a valid move is detected, moves the piece, otherwise just selects or deselects a piece
     */
    public void boardClicked(int x, int y) {
        int boardSpaceX = x / view.BOARDSPACEDIMENSION;
        int boardSpaceY = Math.abs((myBoard.getBoardHeight() - 1) - (y / view.BOARDSPACEDIMENSION));
        if(boardSpaceX >= myBoard.getBoardWidth() || boardSpaceY >= myBoard.getBoardHeight())
            return;
        int currSide = model.isPlayer0Turn() ? 0 : 1;

        //if nothing is currently selected
        if(currPiece == null){
            if(myBoard.getStringOfPieceAtSpace(boardSpaceX, boardSpaceY) != null && myBoard.getSideOfPieceAtSpace(boardSpaceX, boardSpaceY) == currSide){
                currPiece = myBoard.getPieceAtSpace(boardSpaceX, boardSpaceY);
                possibleMoves = currPiece.findAvailableMoves(myBoard);
                redrawBoard();
            }
        }
        //if a piece is already selected
        else{
            if(isValidMove(possibleMoves, boardSpaceX, boardSpaceY)){
                model.storeMove(currPiece, boardSpaceX, boardSpaceY);
                myBoard.finalizeMovePiece(myBoard, currPiece, boardSpaceX, boardSpaceY);
                model.setFirstMove(false);
                if(myBoard.isPlayer0Checkmated()){
                    addOneToPlayerScore(true);
                }
                else if(myBoard.isPlayer1Checkmated()){
                    addOneToPlayerScore(false);
                }
                model.changeTurns();
                redrawScoreSection();
            }
            currPiece = null;
            possibleMoves = null;
            redrawBoard();
        }
    }

//    HELPERS & GETTERS & SETTERS BELOW

    private void setPlayerNames() {
        this.player0Name = JOptionPane.showInputDialog("Enter Player 1 Name");
        this.player1Name = JOptionPane.showInputDialog("Enter Player 2 Name");
        while(player0Name.equals(player1Name)){
            this.player1Name = JOptionPane.showInputDialog("Please Enter a Unique Name for Player 2");
        }
    }

    private void addOneToPlayerScore(boolean addToPlayer0) {
        if(addToPlayer0){
            model.setPlayer0Score(model.getPlayer0Score() + 1);
        }
        else{
            model.setPlayer1Score(model.getPlayer1Score() + 1);
        }

    }

    public static boolean isValidMove(Vector possibleMoves, int boardSpaceX, int boardSpaceY) {
        if(possibleMoves != null){
            for (int possibleMovesCount = 0; possibleMovesCount < possibleMoves.size(); possibleMovesCount++) {
                int[] movePair = (int[]) possibleMoves.elementAt(possibleMovesCount);
                if(boardSpaceX == movePair[0] && boardSpaceY == movePair[1]){
                    return true;
                }
            }
        }
        return false;
    }

    public Vector getPossibleMoves(){
        return possibleMoves;
    }

    private void redrawBoard(){
        view.createMainPanel(view.frame.getContentPane(), myBoard);
    }

    private void redrawScoreSection(){
        view.createScoreSection(view.frame.getContentPane());
    }


    public String getPlayer0Name() {
        return player0Name;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public void setMyBoard(Board myBoard) {
        this.myBoard = myBoard;
    }
}
