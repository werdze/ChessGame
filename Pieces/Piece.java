package ChessGame.Pieces;

import ChessGame.Board;

import java.util.Vector;

public abstract class Piece {

    private String pieceType;
    private int xLoc;
    private int yLoc;
    private int side;               //denotes to which player the piece is assigned

//    CONSTRUCTORS

    public Piece(String pieceType, int xLoc, int yLoc, int side){
        this.pieceType = pieceType;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.side = side;
    }

//    HELPER FUNCTIONS FOR INDIVIDUAL PIECES

    /**
     * Finds every valid move for a piece
     */
    public abstract Vector findAvailableMoves(Board myBoard);

    /**
     * Function is called on a piece. Takes that piece's x and y coords and uses them along with the xOffset & yOffset parameters to determine if a space is within bounds
     */
    public boolean isWithinBoundsAfterMove(Board myBoard, int xOffset, int yOffset){
        boolean isInBoundsX = getxLoc() + xOffset >= 0 && getxLoc() + xOffset < myBoard.getBoardWidth();
        boolean isInBoundsY = getyLoc() + yOffset >= 0 && getyLoc() + yOffset < myBoard.getBoardHeight();
        return isInBoundsX && isInBoundsY;
    }

    /**
     * Called on a piece and uses offsets from the piece's location to find if a destination space is a valid move
     */
    protected void addSpaceToVecIfValidMove(Board myBoard, Vector possibleMoves, int xOffset, int yOffset) {
        if (isWithinBoundsAfterMove(myBoard, xOffset, yOffset) && !myBoard.isPlayer0Checkmated() && !myBoard.isPlayer1Checkmated()) {
            String pieceToCheck = myBoard.getStringOfPieceAtSpace(getxLoc() + xOffset, getyLoc() + yOffset);
            int teamToCheck = myBoard.getSideOfPieceAtSpace(getxLoc() + xOffset, getyLoc() + yOffset);

            if (teamToCheck != getSide()) {
                boolean myKingWillBeInCheck = myBoard.willPlaceKingInCheck(this, this.getxLoc() + xOffset, this.getyLoc() + yOffset, getSide());
                if (!myKingWillBeInCheck){
                    //case: move to space with nothing there
                    if (pieceToCheck == null) {
                        possibleMoves.add(new int[]{getxLoc() + xOffset, getyLoc() + yOffset});
                    }
                    //case: move to space with something there, but enemy king is not a valid move
                    if (pieceToCheck != null && pieceToCheck != "King") {
                        possibleMoves.add(new int[]{getxLoc() + xOffset, getyLoc() + yOffset});
                    }
                }
            }
        }
        //case: can't move or capture
        else {
            return;
        }
    }

    /**
     * Checks a whole direction - i.e. rook moving from middle of row to the end of row
     */
    protected void checkEntireDirectionAndAddToVec(Board myBoard, Vector possibleMoves, int xOffset, int yOffset){
        int xDirection = xOffset;
        int yDirection = yOffset;
        while (isWithinBoundsAfterMove(myBoard, xOffset, yOffset)) {
            addSpaceToVecIfValidMove(myBoard, possibleMoves, xOffset, yOffset);
            if(myBoard.getStringOfPieceAtSpace(getxLoc() + xOffset, getyLoc() + yOffset) == null){
                xOffset += xDirection;
                yOffset += yDirection;
            }
            else{
                break;
            }
        }
    }

    /**
     * Checks every offset from a piece to a destination space, for a piece that can move a single space per turn
     * Returns a vector of these possible moves
     */
    protected Vector singleSpaceMoveCheck(Board myBoard, int[][] offsets) {
        Vector possibleMoves = new Vector();

        for(int toCheckInd = 0; toCheckInd < offsets.length; toCheckInd++){
            addSpaceToVecIfValidMove(myBoard, possibleMoves, offsets[toCheckInd][0], offsets[toCheckInd][1]);
        }
        return possibleMoves;
    }

    /**
     * Checks every offset from a piece to destination spaces, for a piece that can move in one direction per turn
     * Returns a vector of these possible moves
     */
    protected Vector entireDirectionMoveCheck(Board myBoard, int[][] offsets) {
        Vector possibleMoves = new Vector();

        for(int toCheckInd = 0; toCheckInd < offsets.length; toCheckInd++){
            checkEntireDirectionAndAddToVec(myBoard, possibleMoves, offsets[toCheckInd][0], offsets[toCheckInd][1]);
        }
        return possibleMoves;
    }

//      GETTERS & SETTERS

    public int getSide() {
        return side;
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public String getPieceType() {
        return pieceType;
    }

}
