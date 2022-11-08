/*
    Francisco De Grano
    101147447

    BoardView - The following class is a GUI implementation of the Scrabble.xyz board

        NOTE: For the time being the board will not be functional as the logic will be dealt with soon.
 */

import javax.swing.*;
import java.awt.*;
import java.swing.JPanel;

/*
    @author Francisco De Grano
 */
public class BoardView extends javax.swing.JFrame {

    public static  java.awt.Color DEFAULT_BUTTON_COLOR;


    public BoardView(){
        // Add initialize parts after initial window prompt Emily Tang init Frame?
    }

    private void initComponents(){

        controlPanel = new javax.swing.JPanel();
        startGameButton = new javax.swing.JButton();
        letterRack1 = new javax.swing.JButton();
        letterRack2 = new javax.swing.JButton();
        letterRack3 = new javax.swing.JButton();
        letterRack4 = new javax.swing.JButton();
        letterRack5 = new javax.swing.JButton();
        letterRack6 = new javax.swing.JButton();
        letterRack7 = new javax.swing.JButton();

        player1Label = new javax.swing.JLabel();
        player1ScoreLabel = new javax.swing.JLabel();

        player2Label = new javax.swing.JLabel();
        player2ScoreLabel = new javax.swing.JLabel();

        endTurnButton = new javax.swing.JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(650, 800));

        setResizable(false);

        controlPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        controlPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        controlPanel.setPreferredSize(new java.awt.Dimension(616, 151));

        startGameButton.setText("Start New Game");
        startGameButton.addActionListener


    }
}


