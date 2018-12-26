package ChessGame;

import ChessGame.MVC.Controller;

public class Main {

//    FOR TESTING PURPOSES ONLY

    /**
     * Determines which character to display on the TUI chess board
     */
    public static String charForDisplay(Board myBoard, int x, int y) {
        String type = myBoard.getStringOfPieceAtSpace(x, y);
        if (type == null)
            return " ";
        else
            return type.equals("Pawn") == true ? "P" : type.equals("Rook") == true ? "R" : type.equals("Knight") == true ? "N" : type.equals("Bishop") == true ? "B" : type.equals("King") == true ? "K" : type.equals("Queen") == true ? "Q" : type.equals("Bulldozer") == true ? "D" : type.equals("Jester") == true ? "J" : "X";

    }

    /**
     * Prints a TUI display of the chess board in the console
     */
    public static void printDisplay(Board myBoard) {
        System.out.println();
        for (int y = myBoard.getBoardHeight() - 1; y >= 0; y--) {
            for (int i = 0; i < myBoard.getBoardWidth() * 5; i++) {
                System.out.print("_");
            }
            System.out.println("_");
            System.out.print("|");
            for (int x = 0; x < myBoard.getBoardWidth(); x++) {
                if (myBoard.getStringOfPieceAtSpace(x, y) == null)
                    System.out.print("   " + " |");
                else
                    System.out.print(" " + charForDisplay(myBoard, x, y) + myBoard.getSideOfPieceAtSpace(x, y) + " |");
            }
            System.out.println();
        }
        for (int i = 0; i < myBoard.getBoardWidth() * 5; i++) {
            System.out.print("_");
        }
        System.out.println("_");
    }

    public static void main(String[] args) {
        Controller myController = new Controller();

    }
}
