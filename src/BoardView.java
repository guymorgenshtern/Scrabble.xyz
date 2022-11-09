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
        JLabel player1LabelScore = new JLabel();

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
        player1Label.setText("Player 1 Score: ");
        player1LabelScore.setText("0");

        /*
        Setting up Player 2 Score Label
         */
        player2Label.setText("Player 2 Score: ");
        player2LabelScore.setText("0");

        /*
        Setting up Rack Buttons (Users Characters chosen from bag)
         */

        letterRack1.setPreferredSize(new Dimension(60, 40));
        letterRack1.setEnabled(false);

        letterRack2.setPreferredSize(new Dimension(60, 40));
        letterRack2.setEnabled(false);

        letterRack3.setPreferredSize(new Dimension(60, 40));
        letterRack3.setEnabled(false);

        letterRack4.setPreferredSize(new Dimension(60, 40));
        letterRack4.setEnabled(false);

        letterRack5.setPreferredSize(new Dimension(60, 40));
        letterRack5.setEnabled(false);

        letterRack6.setPreferredSize(new Dimension(60, 40));
        letterRack6.setEnabled(false);

        letterRack7.setPreferredSize(new Dimension(60, 40));
        letterRack7.setEnabled(false);

        /*

        We then want these buttons to be in a row to represent the rack so maybe implement

        arraylist <>(); -> use the .add then (letterRackX)

         */

        // Code here

        /*
        Game Board Panel Setup
        Placed Above the user controls, more intuitive design than to have the controls on the side.
         */
        boardPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        boardPanel.setPreferredSize(new Dimension(625, 600));
        boardPanel.setLayout(new GridLayout(0,15)); // 15 x 15 board

        /*
        User Control Panel
         */
        controlPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        controlPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        controlPanel.setPreferredSize(new Dimension(625, 140));

        /*
        Start New Game Button
         */

        startGameButton.setText("Start New Game");

        /*
        Continue to setup gameboard tiles here

        What I wanted to do was to visually show the tiles in the Scrabble GUI
        but i'm not too sure how i'd show the squares

         */

        /*
        Control Panel Layout

        Horizontal Layout of Components

        createParallelGroup - https://docs.oracle.com/en/java/javase/18/docs/api/java.desktop/javax/swing/GroupLayout.ParallelGroup.html
        createSequentialGroup - https://docs.oracle.com/en/java/javase/18/docs/api/java.desktop/javax/swing/GroupLayout.SequentialGroup.html
        addContainerGap - https://docs.oracle.com/en/java/javase/18/docs/api/java.desktop/javax/swing/GroupLayout.SequentialGroup.html#addContainerGap()
         */

        GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);

        controlPanelLayout.setHorizontalGroup(
                controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(controlPanelLayout.createSequentialGroup()
                                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(startGameButton)
                                                        .addComponent(endTurnButton))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(player1Label)
                                                        .addComponent(player2Label))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(player2LabelScore, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(player1LabelScore, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

                                        .addGroup(controlPanelLayout.createSequentialGroup()

                                                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                                .addContainerGap()

                                                                .addComponent(letterRack1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                                                                .addComponent(letterRack2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                                                                .addComponent(letterRack3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                                                                .addComponent(letterRack4, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                                                                .addComponent(letterRack5, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                                                                .addComponent(letterRack6, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                                                                .addComponent(letterRack7, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(controlPanelLayout.createSequentialGroup()
                                                                .addGap(12, 12, 12)
                                                                .addComponent(directionalLabel)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

    }


}


