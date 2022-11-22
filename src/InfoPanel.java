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

    /**
     * InfoPanel creates the layout and location for thr buttons and labels.
     * @param scrabbleModel The Game of Scrabble to update
     * @author Alexander Hum 101180821
     */
    public InfoPanel(ScrabbleModel scrabbleModel) {
        super();
        this.setLayout(new GridLayout(1, 3));
        this.setSize(900,100);

        // creates the labels
        playerName = new JLabel("", SwingConstants.CENTER);
        playerScore = new JLabel();
        JLabel currentNameLabel = new JLabel("Current Player Name:", SwingConstants.RIGHT);
        currentNameLabel.setForeground(Color.WHITE);
        JLabel currentScoreLabel = new JLabel("Score: ", SwingConstants.RIGHT);
        currentScoreLabel.setForeground(Color.WHITE);
        JButton endTurn = new JButton("End Turn");
        // action listener for what happens when the user clicks on the end turn button
        endTurn.addActionListener(e -> {
            scrabbleModel.play(scrabbleModel.getCurrentMove());
        });

        //adds labels and buttons to the panel
        this.add(currentNameLabel);
        this.add(playerName);
        playerName.setForeground(Color.WHITE);
        this.add(currentScoreLabel);
        this.add(playerScore);
        playerScore.setForeground(Color.WHITE);
        this.add(endTurn);
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
    }
}
