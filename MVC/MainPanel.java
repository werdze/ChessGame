package ChessGame.MVC;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.util.Vector;


import ChessGame.Board;
import ChessGame.Pieces.Piece;

public class MainPanel extends JPanel {
    Board myBoard;
    private final int BOARDSPACEDIMENSION;
    private final int FRAMEWIDTH;
    private Vector possibleMoves;

    /**
     * Constructor for the MainPanel
     * Takes in possibleMoves for later display on the board
     */
    MainPanel(Board board, int BOARDSPACEDIMENSION, int FRAMEWIDTH, Vector possibleMoves){
        myBoard = board;
        this.BOARDSPACEDIMENSION = BOARDSPACEDIMENSION;
        this.FRAMEWIDTH = FRAMEWIDTH;
        this.possibleMoves = possibleMoves;
    }

    /**
     * Paints everything on the board
     * Paints the background rows first, then the pieces
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // my graphics:
        for(int yLoc = 0; yLoc < myBoard.getBoardHeight(); yLoc++){
            colorRow(g2, (yLoc + 1) % 2, yLoc * BOARDSPACEDIMENSION, Math.abs(myBoard.getBoardHeight() - 1 - yLoc));
        }
        addPiecesToPanel(g2);
    }

    /**
     * Adds all of the pieces to the panel
     */
    private void addPiecesToPanel(Graphics2D g2) {
        for(int xLoc = 0; xLoc < myBoard.getBoardWidth(); xLoc++){
            for(int yLoc = 0; yLoc < myBoard.getBoardHeight(); yLoc++){
                Piece currPiece = myBoard.getPieceAtSpace(xLoc, yLoc);
                if(currPiece != null){
                    g2.drawImage(loadImage(currPiece.getSide(), currPiece.getPieceType()), (xLoc * BOARDSPACEDIMENSION) + 0, FRAMEWIDTH - (yLoc * BOARDSPACEDIMENSION + 51), BOARDSPACEDIMENSION, BOARDSPACEDIMENSION, null);
                }
            }
        }
    }

    /**
     * Loads the correct image for each piece, depending on piece name and side color
     */
    private Image loadImage(int side, String pieceName){
        String color = null;
        if(side == 0)
            color = "white";
        else
            color = "black";

        BufferedImage image = null;
        try {
//            image = ImageIO.read(new File("src/ChessGame/Images/" + color + pieceName + ".svg"));
            image = ImageIO.read(new File("src/ChessGame/Images/" + color + pieceName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Colors each row in an alternating pattern
     * If a piece is selected, draws a red square on each possibleMoves location and then the original tile color, to give a red boarder appearance
     */
    private void colorRow(Graphics g2, int startingColorNum, int rowHeight, int yLoc){
        for(int xLoc = 0; xLoc < myBoard.getBoardWidth(); xLoc++){
            boolean validMove = Controller.isValidMove(possibleMoves, xLoc, yLoc);

            int offset = 0;

            if(validMove){
                g2.setColor(new Color(255, 0, 0));
                g2.fillRect(xLoc * BOARDSPACEDIMENSION, rowHeight, BOARDSPACEDIMENSION, BOARDSPACEDIMENSION);
                offset = 5;
            }
            if(xLoc % 2 == startingColorNum) {
                g2.setColor(new Color(103, 103, 103));
            }
            else{
                g2.setColor(new Color(255, 255, 255));
            }
            g2.fillRect(xLoc * BOARDSPACEDIMENSION + offset, rowHeight + offset, BOARDSPACEDIMENSION - 2 * offset, BOARDSPACEDIMENSION - 2 * offset);
        }
    }


}
