import javax.swing.*;
import java.awt.*;

/**
 * Creates the JPanel for the top of the program. It includes the players name, score, and an end turn button when
 * the user wants to skip or end turn.
 *
 * @author Alexander Hum 101180821
 */
public class InfoPanel extends JPanel implements ScrabbleView {

    /** JLabel for the current players name */
    private final JLabel playerName;

    /** JLabel for the current players name */
    private final JLabel playerScore;

    /** JLabel for displaying players names and scores */
    private final JLabel playerNamesScores;

    /**
     * InfoPanel creates the layout and location for thr buttons and labels.
     * @param scrabbleModel The Game of Scrabble to update
     * @author Alexander Hum 101180821
     */
    public InfoPanel(ScrabbleModel scrabbleModel) {
        super();
        this.setLayout(new GridLayout(1, 4));
        this.setSize(1000,100);

        // creates the labels
        playerName = new JLabel("", SwingConstants.CENTER);
        playerScore = new JLabel();
        playerNamesScores = new JLabel("");
        JLabel currentNameLabel = new JLabel("Current Player Name:", SwingConstants.RIGHT);
        currentNameLabel.setForeground(Color.BLACK);
        JLabel currentScoreLabel = new JLabel("Score: ", SwingConstants.RIGHT);
        currentScoreLabel.setForeground(Color.BLACK);
        JLabel currentPlayerNamesScores = new JLabel("Player X: " + "Player Y: ", SwingConstants.RIGHT);
        currentPlayerNamesScores.setForeground(Color.BLACK);
        JButton endTurn = new JButton("End Turn");
        // action listener for what happens when the user clicks on the end turn button
        endTurn.addActionListener(e -> {
            scrabbleModel.play(scrabbleModel.getCurrentMove());
        });
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            String undoMessage = "At this time there are no plays to Undo";
            if(scrabbleModel.getNumberOfUndoStack() == 0) {
                JOptionPane.showMessageDialog(this, undoMessage);
            } else {
                scrabbleModel.undo();
            }
        });
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> {
            String redoMessage = "At this time there are no plays to Redo";
            if(scrabbleModel.getNumberOfRedoStack() == 0) {
                JOptionPane.showMessageDialog(this, redoMessage);
            } else {
                scrabbleModel.redo();
            }
        });

        //  Add labels and buttons to the panel
        this.add(currentNameLabel);
        this.add(playerName);
        playerName.setForeground(Color.BLACK);
        this.add(currentScoreLabel);
        this.add(playerScore);
        playerScore.setForeground(Color.BLACK);
        this.add(currentPlayerNamesScores);
        this.add(playerNamesScores);
        playerNamesScores.setForeground(Color.BLACK);
        this.add(endTurn);
        this.add(undoButton);
        this.add(redoButton);

        //  Set background colour
        this.setBackground(new Color(253, 239, 117));

        this.setVisible(true);
    }

    /**
     * updates the players name and score.
     * @param event for the ScrabbleEvent
     * @author Alexander Hum 101180821
     */
    @Override
    public void update(ScrabbleEvent event) {
        // sets text for players name
        playerName.setText(event.getCurrentPlayer().getName());
        // sets text for the players score
        playerScore.setText(Integer.toString(event.getCurrentPlayer().getScore()));
        // sets text for all players score
        playerNamesScores.setText(Integer.toString(event.getCurrentPlayer().getScore())); // Create Method in ScrabbleEvent
    }
}
