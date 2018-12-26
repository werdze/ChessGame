package ChessGame.MVC;

import ChessGame.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class View implements ActionListener, MouseListener {

    JFrame frame;
    JPanel mainPanel;
    Controller control;
    final int BOARDSPACEDIMENSION = 50;
    private int FRAMEWIDTH = 500;
    private int FRAMEHEIGHT = 462;

    /**
     * Constructor for the view
     * Takes in the controller, to obtain access to it
     */
    public View(Controller control){
        this.control = control;
    }

    /**
     * Runs createAndDisplayGUI()
     */
    public void createGUI(Board myBoard){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndDisplayGUI(myBoard);
            }
        });
    }

    /**
     * Makes and displays a frame of a certain size and calls addComponentsToPane()
     *
     */
    public void createAndDisplayGUI(Board myBoard){
        frame = new JFrame("chessBoard");
        frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane(), myBoard);
        frame.setVisible(true);
    }

    /**
     * Uses BorderLayout to add menu bar, main panel, and score section to the frame
     */
    public void addComponentsToPane(Container pane, Board myBoard) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
        pane.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        createMenuBar(pane);
        createMainPanel(pane, myBoard);
        createScoreSection(pane);


    }

    /**
     * Creates each item present in the menu bar
     */
    private void createMenuBar(Container pane) {
        JMenuBar menuBar = new JMenuBar();
        pane.add(menuBar, BorderLayout.PAGE_START);

        JMenu newGameMenu = new JMenu("New Game");
        newGameMenu.addActionListener(this);
        menuBar.add(newGameMenu);

        JMenuItem defaultBoardMenuItem = new JMenuItem("Default Board");
        defaultBoardMenuItem.addActionListener(this);
        newGameMenu.add(defaultBoardMenuItem);

        JMenuItem fairyBoardMenuItem = new JMenuItem("Fairy Board");
        fairyBoardMenuItem.addActionListener(this);
        newGameMenu.add(fairyBoardMenuItem);

        JMenu gameoptionsMenu = new JMenu("Game Options");
        gameoptionsMenu.addActionListener(this);
        menuBar.add(gameoptionsMenu);

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.addActionListener(this);
        gameoptionsMenu.add(undoMenuItem);

        gameoptionsMenu.addSeparator();

        JMenuItem restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.addActionListener(this);
        gameoptionsMenu.add(restartMenuItem);

        gameoptionsMenu.addSeparator();

        JMenuItem forfeitMenuItem = new JMenuItem("Forfeit");
        forfeitMenuItem.addActionListener(this);
        gameoptionsMenu.add(forfeitMenuItem);

        gameoptionsMenu.addSeparator();

        JMenuItem changeNamesMenuItem = new JMenuItem("Change Names");
        changeNamesMenuItem.addActionListener(this);
        gameoptionsMenu.add(changeNamesMenuItem);
    }

    /**
     * Makes a new MainPanel(), adds a mouse listener, and adds it to the pane container of the frame
     */
    protected void createMainPanel(Container pane, Board myBoard) {
        FRAMEWIDTH = this.control.getMyBoard().getBoardHeight() * 50;
        FRAMEHEIGHT = this.control.getMyBoard().getBoardWidth() * 50 + 62;
        mainPanel = new MainPanel(myBoard, BOARDSPACEDIMENSION, FRAMEWIDTH, control.getPossibleMoves());
        mainPanel.addMouseListener(this);
        pane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    /**
     * Creates the score section
     */
    protected void createScoreSection(Container pane) {
        JSplitPane bottomScorePanes = new JSplitPane();
        pane.add(bottomScorePanes, BorderLayout.PAGE_END);

        JLabel label0, label1;
        Font boldFont = new Font("LucidaGrande", Font.BOLD, 13);
        String label0Text, label1Text;
        String [] strs = setLabelText();

        label0 = new JLabel(strs[0]);
        label1 = new JLabel(strs[1]);
        if(control.model.isPlayer0Turn()){
            label0.setFont(boldFont);
        }
        else{
            label1.setFont(boldFont);
        }

        bottomScorePanes.setLeftComponent(label0);
        bottomScorePanes.setRightComponent(label1);
        pane.revalidate();
    }

    /**
     * Creates the correct labels for the score section
     */
    private String [] setLabelText() {
        String [] strs = new String [2];
        String label0Text = control.getPlayer0Name() + "- Score: " + control.model.getPlayer0Score() + "                        ";
        String label1Text = control.getPlayer1Name() + "- Score: " + control.model.getPlayer1Score() + "                        ";
        if(control.getMyBoard().isPlayer0Checkmated()){
            label0Text = "(CHECKMATED)" + control.getPlayer0Name() + " - Score: " + control.model.getPlayer0Score() + "                         ";
        }
        else if(control.getMyBoard().isPlayer1Checkmated()){
            label1Text = "(CHECKMATED)" + control.getPlayer1Name() + " - Score: " + control.model.getPlayer1Score() + "                         ";
        }
        else if(control.getMyBoard().isPlayer0KingInCheck()){
            label0Text = "(IN CHECK)" + control.getPlayer0Name() + " - Score: " + control.model.getPlayer0Score() + "                       ";
        }
        else if(control.getMyBoard().isPlayer1KingInCheck()){
            label1Text = "(IN CHECK)" + control.getPlayer1Name() + " - Score: " + control.model.getPlayer1Score() + "                       ";
        }
        else if(control.getMyBoard().isStalemate()){
            label0Text = "(STALEMATE)" + control.getPlayer0Name() + " - Score: " + control.model.getPlayer0Score() + "                          ";
            label1Text = "(STALEMATE)" + control.getPlayer1Name() + " - Score: " + control.model.getPlayer1Score() + "                          ";
        }
        strs[0] = label0Text;
        strs[1] = label1Text;
        return strs;
    }

    /**
     * Detects a button click on the menu bar and calls the corresponding function in the controller
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JMenuItem){
            if(((JMenuItem) e.getSource()).getText() == "Default Board"){
                control.doNewDefaultBoard();
            }
            else if(((JMenuItem) e.getSource()).getText() == "Fairy Board"){
                control.doNewFairyBoard();
            }
            else if(((JMenuItem) e.getSource()).getText() == "Undo"){
                control.doUndo();
            }
            else if(((JMenuItem) e.getSource()).getText() == "Restart"){
                control.doRestart();
            }
            else if(((JMenuItem) e.getSource()).getText() == "Forfeit"){
                control.doForfeit();
            }
            else if(((JMenuItem) e.getSource()).getText() == "Change Names"){
                control.doChangeNames();
            }
        }
    }

    /**
     * Detects a MouseEvent and passes the x and y coordinates on the panel to the controller
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        control.boardClicked(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
