package ChessGame;

import ChessGame.Pieces.Piece;
import ChessGame.Pieces.Pawn;
import ChessGame.Pieces.Rook;
import ChessGame.Pieces.Knight;
import ChessGame.Pieces.Bishop;
import ChessGame.Pieces.King;
import ChessGame.Pieces.Queen;
import ChessGame.Pieces.Bulldozer;
import ChessGame.Pieces.Jester;

import java.util.Stack;
import java.util.Vector;

public class Board {

    private int boardWidth;
    private int boardHeight;
    private Piece[][] boardArr;
    public Vector side0Pieces = new Vector();
    public Vector side1Pieces = new Vector();

    /**
     * Keeps a stack of StoreMoves objects, which contain all the info necessary for undo-ing a move
     */
    private Stack undoStack = new Stack();

    private King side0King = null;
    private King side1King = null;

    private boolean isPlayer0KingInCheck = false;
    private boolean isPlayer1KingInCheck = false;
    private boolean hasCheckStatusChangedToTrue = false;
    private boolean isPlayer0Checkmated = false;
    private boolean isPlayer1Checkmated = false;
    private boolean isStalemate = false;

//    CONSTRUCTOR
//      Creates a Board of Pieces, in which the pieces know which player they are assigned to
    /**
     * Constructor for a new board.
     * boardArr[0][0] is at the bottom left of the board
     * All ChessGame.Pieces.Piece spots in the array should automatically be set to null
     */
    public Board(int x, int y){
        if(x < 3 || y < 3){
            System.out.println("Smaller than a 3x3 board won't work well");
            this.boardWidth = 3;
            this.boardHeight = 3;
            this.boardArr = new Piece[3][3];
            return;
        }
        this.boardWidth = x;
        this.boardHeight = y;
        this.boardArr = new Piece[x][y];
    }

//    FOR DEFAULT BOARD

    public static Board defaultBoardSetup(){
        Board myBoard = new Board(8, 8);
        myBoard.defaultSetSide( 0);
        myBoard.defaultSetSide( 1);
        return myBoard;
    }

    public static Board fairyBoardSetup() {
        Board fairyBoard = new Board(10, 8);
        fairyBoard.fairySetSide(0);
        fairyBoard.fairySetSide(1);
        return fairyBoard;
    }

    /**
     * Sets up the board as a default chess board with the standard chess layout
     */
    public void defaultSetSide(int playerSide){
        if(!(playerSide == 0 || playerSide == 1)){
            System.out.println("side int is not 0 or 1");
            return;
        }
        if(boardHeight != 8 || boardWidth != 8){
            System.out.println("can only set default side if board is 8x8");
            return;
        }

        int yLoc = -1;
        yLoc = (playerSide == 0) ? 0 : 7;

        for(int currentPawnLoc = 0; currentPawnLoc < 8; currentPawnLoc++){
            createAndPlacePiece("Pawn", currentPawnLoc, Math.abs(yLoc - 1), playerSide);
        }
        createAndPlacePiece("Rook", 0, yLoc, playerSide);
        createAndPlacePiece("Knight", 1, yLoc, playerSide);
        createAndPlacePiece("Bishop", 2, yLoc, playerSide);
        createAndPlacePiece("Queen", 3, yLoc, playerSide);
        createAndPlacePiece("King", 4, yLoc, playerSide);
        createAndPlacePiece("Bishop", 5, yLoc, playerSide);
        createAndPlacePiece("Knight", 6, yLoc, playerSide);
        createAndPlacePiece("Rook", 7, yLoc, playerSide);
    }

    /**
     * Sets up the board with the fairy chess layout
     */
    public void fairySetSide(int playerSide){
        if(!(playerSide == 0 || playerSide == 1)){
            System.out.println("side int is not 0 or 1");
            return;
        }
        int yLoc = -1;
        yLoc = (playerSide == 0) ? 0 : 7;

        createAndPlacePiece("Bulldozer", 0, Math.abs(yLoc - 1), playerSide);
        for(int currentPawnLoc = 1; currentPawnLoc < getBoardWidth(); currentPawnLoc++){
            createAndPlacePiece("Pawn", currentPawnLoc, Math.abs(yLoc - 1), playerSide);
        }
        createAndPlacePiece("Bulldozer", 9, Math.abs(yLoc - 1), playerSide);

        createAndPlacePiece("Jester", 0, yLoc, playerSide);
        createAndPlacePiece("Rook", 1, yLoc, playerSide);
        createAndPlacePiece("Knight", 2, yLoc, playerSide);
        createAndPlacePiece("Bishop", 3, yLoc, playerSide);
        createAndPlacePiece("Queen", 4, yLoc, playerSide);
        createAndPlacePiece("King", 5, yLoc, playerSide);
        createAndPlacePiece("Bishop", 6, yLoc, playerSide);
        createAndPlacePiece("Knight", 7, yLoc, playerSide);
        createAndPlacePiece("Rook", 8, yLoc, playerSide);
        createAndPlacePiece("Jester", 9, yLoc, playerSide);

    }

//    FOR NON DEFAULT BOARD

    /**
     * Creates an empty board of boardWidth and boardHeight, for extensibility purposes
     */
    public static Board newEmptyBoard(int boardWidth, int boardHeight) {
        return new Board(boardWidth, boardHeight);
    }

    /**
     * SetPieceToSpace is for testing purposes only. Does not do the normal bookkeeping present in createAndPlacePiece()
     * Also, this overwrites a piece if a piece is placed at the same space as another piece during setup.
     */
    public void setPieceToSpace(Piece pieceToPlace, int xDest, int yDest){
        boardArr[xDest][yDest] = pieceToPlace;
    }

//    HELPER FUNCTIONS FOR EITHER BOARD TYPE

    /**
     * Creates a piece, sets it to a space at xLoc and yLoc on the board and adds the piece to the vector of all pieces on a given side.
     * Also sets side0King or side1King, if a king is created, depending on the side
     * If the pieceType is not recognized, will print to the console that the piece is not yet implemented and will do nothing else
     */
    public void createAndPlacePiece(String pieceType, int xLoc, int yLoc, int side){
        if(!isWithinBounds(xLoc, yLoc)){
            System.out.println("Newly created piece is not within bounds");
            return;
        }
        if(!(side == 0 || side == 1)){
            System.out.println("Newly created piece side is not valid");
            return;
        }

        Piece newPiece = null;
        if(pieceType.equals("Pawn")){
            newPiece = new Pawn(xLoc, yLoc, side);
        }
        else if(pieceType.equals("Rook")){
            newPiece = new Rook(xLoc, yLoc, side);
        }
        else if(pieceType.equals("Knight")){
            newPiece = new Knight(xLoc, yLoc, side);
        }
        else if(pieceType.equals("Bishop")){
            newPiece = new Bishop(xLoc, yLoc, side);
        }
        else if(pieceType.equals("King")){
            newPiece = new King(xLoc, yLoc, side);
            if(side == 0)
                side0King = (King) newPiece;
            else
                side1King = (King) newPiece;
        }
        else if(pieceType.equals("Queen")){
            newPiece = new Queen(xLoc, yLoc, side);
        }
        else if(pieceType.equals("Bulldozer")){
            newPiece = new Bulldozer(xLoc, yLoc, side);
        }
        else if(pieceType.equals("Jester")){
            newPiece = new Jester(xLoc, yLoc, side);
        }
        else{
            System.out.println("Cannot create new " + pieceType + " piece yet - not yet implemented");
            return;
        }
        boardArr[xLoc][yLoc] = newPiece;
        addToSidePiecesVec(newPiece);
    }

    /**
     * Adds a piece to the correct side vector
     */
    private void addToSidePiecesVec(Piece pieceToAdd){
        int side = pieceToAdd.getSide();
        if(side == 0)
            side0Pieces.add(pieceToAdd);
        else
            side1Pieces.add(pieceToAdd);
    }

    /**
     * In the play loop, this function takes in a valid move coordinate and moves the piece to that space
     * If a piece is captured in the process, will call capturePiece() to capture it
     * In order to finalize the move, this also calls setKingCheckAndCheckmateStatus() and setStalemateStatus() to determine if the game is over
     */
    public void finalizeMovePiece(Board myBoard, Piece pieceToMove, int xDest, int yDest){
        boardArr[pieceToMove.getxLoc()][pieceToMove.getyLoc()] = null;

        //case: capture opponent piece
        if(boardArr[xDest][yDest] != null){
            capturePiece(pieceToMove, xDest, yDest);
        }
        //if nothing to capture, just move the piece
        boardArr[xDest][yDest] = pieceToMove;
        pieceToMove.setxLoc(xDest);
        pieceToMove.setyLoc(yDest);

//        set the check and checkmate status for the king on the opposite side
        King kingInQuestion = (pieceToMove.getSide() != 0) ? side0King : side1King;
        setKingCheckAndCheckmateStatus(myBoard, kingInQuestion);
//        set/confirm the check status of the king on the current side
        kingInQuestion = (pieceToMove.getSide() == 0) ? side0King : side1King;
        setKingCheckStatus(myBoard, kingInQuestion);

        int sideToCheck = (pieceToMove.getSide() == 0) ? 1 : 0;
        setStalemateStatus(myBoard, sideToCheck);
    }

    /**
     * Given a pieceToMove to a space and a space with an opponent piece on it, this will capture the opponent piece and add it to the captured vector
     */
    public void capturePiece(Piece pieceToMove, int xDest, int yDest) {
        if(boardArr[xDest][yDest].getSide() != pieceToMove.getSide()) {
            Piece capturedPiece = boardArr[xDest][yDest];
            removePieceFromSideVec(capturedPiece);
        }
    }

//    moves piece temporarily, checks for check, and returns board to previous state
    /**
     * Determines if moving a piece to an xDest and yDest will place that piece's king in check
     * If that piece's king is put into check by the move, the correct flag, isPlayer0KingInCheck or isPlayer1KingInCheck, will be set to true
     * Otherwise those both remain as they were
     */
    public boolean willPlaceKingInCheck(Piece pieceToMove, int xDest, int yDest, int sideOfKingToCheck){
        //save pieces and status of board to revert at end of function
        int pieceToMoveXOrigin = pieceToMove.getxLoc();
        int pieceToMoveYOrigin = pieceToMove.getyLoc();
        Piece pieceToReplace = boardArr[xDest][yDest];
        boolean isPlayer0KingInCheckStatus = isPlayer0KingInCheck();
        boolean isPlayer1KingInCheckStatus = isPlayer1KingInCheck();

        boardArr[pieceToMoveXOrigin][pieceToMoveYOrigin] = null;
        boardArr[xDest][yDest] = pieceToMove;
        pieceToMove.setxLoc(xDest);
        pieceToMove.setyLoc(yDest);

        King kingInQuestion = (sideOfKingToCheck == 0) ? side0King : side1King;

        if(sideOfKingToCheck == 0){
            isPlayer0KingInCheck = false;
        }
        else{
            isPlayer1KingInCheck = false;
        }

        setKingCheckStatus(this, kingInQuestion);

        boolean didPlaceKingInCheck = false;
        if(sideOfKingToCheck == 0){
            if(isPlayer0KingInCheck())
                didPlaceKingInCheck = true;
        }
        else{
            if(isPlayer1KingInCheck())
                didPlaceKingInCheck = true;
        }

        //revert board
        boardArr[xDest][yDest] = pieceToReplace;
        boardArr[pieceToMoveXOrigin][pieceToMoveYOrigin] = pieceToMove;
        isPlayer0KingInCheck = isPlayer0KingInCheckStatus;
        isPlayer1KingInCheck = isPlayer1KingInCheckStatus;
        pieceToMove.setxLoc(pieceToMoveXOrigin);
        pieceToMove.setyLoc(pieceToMoveYOrigin);

        return didPlaceKingInCheck;
    }

    /**
     * This removes a piece from it's corresponding sidePieces vector. Used when a piece is captured.
     */
    private void removePieceFromSideVec(Piece pieceToMove) {
        Vector sideVec = (pieceToMove.getSide() == 0) ? side0Pieces : side1Pieces;
        for(int vecInd = 0; vecInd < sideVec.size(); vecInd++){
            Piece pieceToCompare = (Piece) sideVec.elementAt(vecInd);
            if(pieceToCompare == pieceToMove){
                sideVec.removeElementAt(vecInd);
                return;
            }
        }
    }

    /**
     * Checks if a given xLoc and yLoc are within bounds
     */
    private boolean isWithinBounds(int xLoc, int yLoc){
        boolean isInBoundsX = xLoc >= 0 && xLoc < boardWidth;
        boolean isInBoundsY = yLoc >= 0 && yLoc < boardHeight;
        return isInBoundsX && isInBoundsY;
    }

//    ENDGAME FUNCTIONS

    /**
     * Checks if the game is now a stalemate.
     * If so, this calls setStalemate() to change the stalemate status flag to true
     * This is called in finalizeMovePiece(), when a move has been made
     */
    public void setStalemateStatus(Board myBoard, int sideToCheck){
        if(side0Pieces.size() == 1 && side1Pieces.size() == 1){
            isStalemate = true;
            return;
        }

        Vector piecesVectorToCheck = (sideToCheck == 0) ? side0Pieces : side1Pieces;
        King kingInQuestion = (sideToCheck == 0) ? side0King : side1King;

        if(side0King == null || side1King == null || kingInQuestion == null || piecesVectorToCheck == null || piecesVectorToCheck.size() == 0){
            System.out.println("Something is null or zero here");
            return;
        }

        setKingCheckStatus(myBoard, kingInQuestion);

        boolean isKingOnSideInCheck = (sideToCheck == 0) ? isPlayer0KingInCheck : isPlayer1KingInCheck;

        //if king is in check, keep isStalemate as false
        if(isKingOnSideInCheck == true){
            return;
        }
        //otherwise: make sure no other pieces on the king's side have available moves
        for(int piecesLeftOnSide = piecesVectorToCheck.size(); piecesLeftOnSide > 0; piecesLeftOnSide--){
            Piece currPieceToCheck = (Piece) piecesVectorToCheck.elementAt(piecesLeftOnSide - 1);
            Vector availableMovesCount = currPieceToCheck.findAvailableMoves(myBoard);
            if(availableMovesCount.size() != 0){
                setStalemate(false);
                return;
            }
        }
        setStalemate(true);
    }

    /**
     * Checks if one of the sides, the side of kingInQuestion, has been put in checkmate
     * This is called in finalizeMovePiece(), when a move has been made
     */
    public void setKingCheckAndCheckmateStatus(Board myBoard, King kingInQuestion){
        if(side0King == null || side1King == null)
            return;

        setKingCheckStatus(myBoard, kingInQuestion);

        boolean isKingInCheck = (kingInQuestion.getSide() == 0) ? isPlayer0KingInCheck : isPlayer1KingInCheck;
        Vector sidePiecesVec = (kingInQuestion.getSide() == 0) ? side0Pieces : side1Pieces;

        //if king is in check, check if it is checkmate
        if(isKingInCheck){
            //can king or another piece move and block the check?
            for(int sideVecInd = 0; sideVecInd < sidePiecesVec.size(); sideVecInd++){
                Piece pieceToCheck = (Piece) sidePiecesVec.elementAt(sideVecInd);
                Vector possibleMoves0 = pieceToCheck.findAvailableMoves(myBoard);
                for(int possibleMovesInd = 0; possibleMovesInd < possibleMoves0.size(); possibleMovesInd++){
                    int[] moveToCheck = (int[]) possibleMoves0.elementAt(possibleMovesInd);
                    int xDest = moveToCheck[0];
                    int yDest = moveToCheck[1];
                    //if moving here will bring it out of check, we're good
                    if(willPlaceKingInCheck(pieceToCheck, xDest, yDest, pieceToCheck.getSide()) == false){
                        return;
                    }
                }
            }
            if(kingInQuestion.getSide() == 0)
                setPlayer0Checkmated(true);
            else
                setPlayer1Checkmated(true);
        }
    }

    /**
     * This checks every possible offset from the kingInQuestion with corresponding piece names, to determine if the kingInQuestion is in check
     * If so, the corresponding isPlayer0KingInCheck or isPlayer1KingInCheck will be set to true
     */
    public void setKingCheckStatus(Board myBoard, King kingInQuestion) {
        if(kingInQuestion == null){
            System.out.println("King is null?");
            return;
        }
        int yMovePawnForwardOffset = kingInQuestion.getSide() == 0 ? 1 : -1;      //+1 or -1 y movement depending on which side the pawn belongs to

        //case: pawn on the king's diagonal is putting the king in check
        lookForPieceAndSetKingCheckStatus(myBoard, kingInQuestion, "Pawn", -1, yMovePawnForwardOffset);
        lookForPieceAndSetKingCheckStatus(myBoard, kingInQuestion, "Pawn", 1, yMovePawnForwardOffset);

        //case: knight is putting the king in check
        int[][] offsets = Knight.offsets;
        singleSpaceKingCheckStatus(myBoard, kingInQuestion, "Knight", offsets);

        //case: rook is putting the king in check
        offsets = Rook.offsets;
        entireDirectionKingCheckStatus(myBoard, kingInQuestion, "Rook", offsets);

        //case: bishop is putting the king in check
        offsets = Bishop.offsets;
        entireDirectionKingCheckStatus(myBoard, kingInQuestion, "Bishop", offsets);

        //case: queen is putting the king in check
        offsets = Queen.offsets;
        entireDirectionKingCheckStatus(myBoard, kingInQuestion, "Queen", offsets);

        //case: other king is putting the king in check
        offsets = King.offsets;
        singleSpaceKingCheckStatus(myBoard, kingInQuestion, "King", offsets);

        //case: look for bulldozer
        lookForPieceAndSetKingCheckStatus(myBoard, kingInQuestion, "Bulldozer", 0, yMovePawnForwardOffset);

        //case: look for jester
        offsets = Jester.offsets;
        singleSpaceKingCheckStatus(myBoard, kingInQuestion, "Jester", offsets);

        if(!hasCheckStatusChangedToTrue) {
            changeKingCheckStatus(kingInQuestion, false);
        }
        hasCheckStatusChangedToTrue = false;
    }

    /**
     * Checks if a piece on the other side a single space away from the king, of pieceToCheckFor type, is placing the king in check
     */
    private void singleSpaceKingCheckStatus(Board myBoard, King kingInQuestion, String pieceToCheckFor, int[][] offsets) {
        for(int toCheckInd = 0; toCheckInd < offsets.length; toCheckInd++){
            lookForPieceAndSetKingCheckStatus(myBoard, kingInQuestion, pieceToCheckFor, offsets[toCheckInd][0], offsets[toCheckInd][1]);
        }
    }

    /**
     * Checks if a piece on the other side who can target the king through an entire direction, of pieceToCheckFor type, is placing the king in check
     */
    private void entireDirectionKingCheckStatus(Board myBoard, King kingInQuestion, String pieceToCheckFor, int[][] offsets) {
        for(int toCheckInd = 0; toCheckInd < offsets.length; toCheckInd++){
            checkEntireDirectionAndSetCheckStatus(myBoard, kingInQuestion, pieceToCheckFor, offsets[toCheckInd][0], offsets[toCheckInd][1]);
        }
    }

    /**
     * Checks for a specific piece at a specific offset from the kingInQuestion
     * If that piece is found and is on the opponent team, the corresponding isPlayer0KingInCheck or isPlayer1KingInCheck for kingInQuestion will be set to true
     */
    private void lookForPieceAndSetKingCheckStatus(Board myBoard, King kingInQuestion, String pieceToCheckFor, int xOffset, int yOffset){
        int xKingLoc = kingInQuestion.getxLoc();
        int yKingLoc = kingInQuestion.getyLoc();
        int kingSide = kingInQuestion.getSide();

        if(kingInQuestion.isWithinBoundsAfterMove(myBoard, xOffset, yOffset)){
            if(getStringOfPieceAtSpace(xKingLoc + xOffset, yKingLoc + yOffset) == pieceToCheckFor && getSideOfPieceAtSpace(xKingLoc + xOffset, yKingLoc + yOffset) != kingSide){
                changeKingCheckStatus(kingInQuestion, true);
            }
        }
    }

    /**
     * Checks for a specific piece at a specific direction from the kingInQuestion
     * If that piece is found on that directional line and is on the opponent team, the corresponding isPlayer0KingInCheck or isPlayer1KingInCheck for kingInQuestion will be set to true
     */
    public void checkEntireDirectionAndSetCheckStatus(Board myBoard, King kingInQuestion, String pieceToCheckFor, int xOffset, int yOffset){
        int xDirection = xOffset;
        int yDirection = yOffset;
        while (kingInQuestion.isWithinBoundsAfterMove(myBoard, xOffset, yOffset)) {
            //case: nothing in a space
            if(myBoard.getStringOfPieceAtSpace(kingInQuestion.getxLoc() + xOffset, kingInQuestion.getyLoc() + yOffset) == null){
                xOffset += xDirection;
                yOffset += yDirection;
            }
            //case found the piece we were looking for of the opposite side
            else if(myBoard.getStringOfPieceAtSpace(kingInQuestion.getxLoc() + xOffset, kingInQuestion.getyLoc() + yOffset) == pieceToCheckFor){
                if(kingInQuestion.getSide() != getSideOfPieceAtSpace(kingInQuestion.getxLoc() + xOffset, kingInQuestion.getyLoc() + yOffset)) {
                    changeKingCheckStatus(kingInQuestion, true);
                    break;
                }
                else
                    break;
            }
            //case: found one of the pieces on the king's own side
            else{
                break;
            }
        }
    }

    private void changeKingCheckStatus(King kingInQuestion, boolean isKingInCheck) {
        if(kingInQuestion.getSide() == 0){
            setPlayer0KingInCheck(isKingInCheck);
        }
        else{
            setPlayer1KingInCheck(isKingInCheck);
        }
        hasCheckStatusChangedToTrue = true;
    }

//      GETTERS & SETTERS

    /**
     * Returns a String of the name of the piece at a location or null if there is no piece there
     */
    public String getStringOfPieceAtSpace(int xLoc, int yLoc){
        if(boardArr[xLoc][yLoc] != null)
            return boardArr[xLoc][yLoc].getPieceType();
        else
            return null;
    }

    /**
     * Returns the side (0 or 1) of the piece at a location, or -1 if there is no piece there
     */
    public int getSideOfPieceAtSpace(int xLoc, int yLoc){
        if(boardArr[xLoc][yLoc] == null)
            return -1;
        else
            return boardArr[xLoc][yLoc].getSide();
    }

    /**
     * Returns a reference to the actual piece at a given space
     */
    public Piece getPieceAtSpace(int xLoc, int yLoc){
        return boardArr[xLoc][yLoc];
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public boolean isPlayer0Checkmated() {
        return isPlayer0Checkmated;
    }

    public void setPlayer0Checkmated(boolean player0Checkmated) {
        isPlayer0Checkmated = player0Checkmated;
    }

    public boolean isPlayer1Checkmated() {
        return isPlayer1Checkmated;
    }

    public void setPlayer1Checkmated(boolean player1Checkmated) {
        isPlayer1Checkmated = player1Checkmated;
    }
    
    public boolean isStalemate() {
        return isStalemate;
    }

    public void setStalemate(boolean stalemate) {
        isStalemate = stalemate;
    }

    public boolean isPlayer0KingInCheck() {
        return isPlayer0KingInCheck;
    }

    public void setPlayer0KingInCheck(boolean player0KingInCheck) {
        isPlayer0KingInCheck = player0KingInCheck;
    }

    public boolean isPlayer1KingInCheck() {
        return isPlayer1KingInCheck;
    }

    public void setPlayer1KingInCheck(boolean player1KingInCheck) {
        isPlayer1KingInCheck = player1KingInCheck;
    }

    public boolean hasCheckStatusChangedToTrue() {
        return hasCheckStatusChangedToTrue;
    }

    public void setHasCheckStatusChangedToTrue(boolean hasCheckStatusChangedToTrue) {
        this.hasCheckStatusChangedToTrue = hasCheckStatusChangedToTrue;
    }

    public Stack getUndoStack() {
        return undoStack;
    }
}