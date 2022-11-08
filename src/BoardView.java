/*
    Francisco De Grano
    101147447

    BoardView - The following class is a GUI implementation of the Scrabble.xyz board

        NOTE: For the time being the board will not be functional as the logic will be dealt with soon.
 */
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

/*
    @author Francisco De Grano
 */
public class BoardView extends javax.swing.JFrame {

    public static  java.awt.Color DEFAULT_BUTTON_COLOR;


    public BoardView(){
        // Add initialize parts after initial window prompt Emily Tang init Frame?
    }

    @SuppressWarnings("unchecked") // Added this line for now cause things got messy :)
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
        endTurnButton = new javax.swing.JButton();

        player1Label = new javax.swing.JLabel();
        player1ScoreLabel = new javax.swing.JLabel();

        player2Label = new javax.swing.JLabel();
        player2ScoreLabel = new javax.swing.JLabel();

        directionalLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(800, 800));

        setResizable(false);

        controlPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        controlPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        controlPanel.setPreferredSize(new java.awt.Dimension(616, 151));

        startGameButton.setText("Start New Game");
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameButtonActionPerformed(evt);
            }
        });

        letterRack1.setPreferredSize(new java.awt.Dimension(80, 62));
        letterRack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterRack1ActionPerformed(evt);
            }
        });

        letterRack2.setPreferredSize(new java.awt.Dimension(80, 62));
        letterRack2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterRack2ActionPerformed(evt);
            }
        });

        letterRack3.setPreferredSize(new java.awt.Dimension(80, 62));
        letterRack3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterRack3ActionPerformed(evt);
            }
        });

        letterRack4.setPreferredSize(new java.awt.Dimension(80, 62));
        letterRack4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterRack4ActionPerformed(evt);
            }
        });

        letterRack5.setPreferredSize(new java.awt.Dimension(80, 62));
        letterRack5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterRack5ActionPerformed(evt);
            }
        });

        letterRack6.setPreferredSize(new java.awt.Dimension(80, 62));
        letterRack6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterRack6ActionPerformed(evt);
            }
        });

        letterRack7.setPreferredSize(new java.awt.Dimension(80, 62));
        letterRack7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterRack7ActionPerformed(evt);
            }
        });

        player1Label.setText("Player 1 Score: ");
        player1ScoreLabel.setText("0");

        player2Label.setText("Player 2 Score: ");
        player2ScoreLabel.setText("0");

        directionalLabel.setText("Select where you want to place your letter");

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
                controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(controlPanelLayout.createSequentialGroup()
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(startGameButton)
                                                        .addComponent(endTurnButton))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(player1Label)
                                                        .addComponent(player2Label))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(player2ScoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(player1ScoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(letterRack1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(letterRack2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(letterRack3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(letterRack4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(letterRack5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(letterRack6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(letterRack7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                                .addGap(12, 12, 12)
                                                                .addComponent(directionalLabel)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        controlPanelLayout.setVerticalGroup(
                controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(controlPanelLayout.createSequentialGroup()
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(startGameButton)
                                                        .addComponent(player1Label)
                                                        .addComponent(player1ScoreLabel)))
                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(player2Label)
                                        .addComponent(player2ScoreLabel)
                                        .addComponent(endTurnButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(directionalLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(letterRack4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(letterRack1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(letterRack2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(letterRack3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(letterRack4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(letterRack5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(letterRack7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
        );
    }

    private void letterRack7ActionPerformed(ActionEvent evt) {
    }

    private void letterRack6ActionPerformed(ActionEvent evt){

    }

    private void letterRack5ActionPerformed(ActionEvent evt) {
    }

    private void letterRack4ActionPerformed(ActionEvent evt) {
    }

    private void letterRack3ActionPerformed(ActionEvent evt) {
    }

    private void letterRack2ActionPerformed(ActionEvent evt) {
    }

    private void letterRack1ActionPerformed(ActionEvent evt) {
    }

    private void startGameButtonActionPerformed(ActionEvent evt) {
    }

    public void initBoardComponents(Board gameBoard){

        // Board Components
        JPanel boardPanel = new JPanel();
        JPanel controlPanel = new JPanel();

        JButton startGameButton = new JButton();
        DEFAULT_BUTTON_COLOR = startGameButton.getBackground();
        JButton letterRack1 = new JButton();
        JButton letterRack2 = new JButton();
        JButton letterRack3 = new JButton();
        JButton letterRack4 = new JButton();
        JButton letterRack5 = new JButton();
        JButton letterRack6 = new JButton();
        JButton letterRack7 = new JButton();
        JButton endTurnButton = new JButton();
        buttonsOnRack = new ArrayList<>();

        JLabel directionalLabel = new JLabel();

        JLabel player1Label = new JLabel();
        JLabel player1ScoreLabel = new JLabel();

        JLabel player2Label = new JLabel();
        JLabel player2LabelScore = new JLabel();

        /*
         JFrame Setup
         */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 800));
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(null);
        setResizable(false);

        /*
        Setting up user directions
         */
        directionalLabel.setText("Select where you want to place your character");
        directionalLabel.setVisible(false);

        /*
        Setting up Player 1 Score Label
         */







    }


}


