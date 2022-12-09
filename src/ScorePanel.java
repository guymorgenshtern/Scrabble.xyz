import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates a JPanel to display all the Players Scores in the game.
 * @author Francisco De Grano 101147447 Blah
 */
public class ScorePanel extends JPanel implements ScrabbleView {

    /** Component for current players scores */
    private final JLabel allPlayersScores;


    /**
     * ScorePanel Creates the layout and location for the Player Scores
     * @param scrabbleModel The Game of Scrabble to update
     *
     */
    public ScorePanel(ScrabbleModel scrabbleModel) {
        super();
        this.setLayout(new GridLayout(1, 1));
        this.setSize(500, 250);

        // Player Score Label
        allPlayersScores = new JLabel(" ", JLabel.LEFT);
        JLabel scoreViewLabel = new JLabel("LEADERBOARD", JLabel.CENTER);
        scoreViewLabel.setForeground(Color.BLACK);

        this.add(scoreViewLabel);
        this.add(allPlayersScores);
        allPlayersScores.setForeground(Color.BLACK);

        this.setBackground(new Color(253, 239, 117));
        this.setVisible(true);
    }


    @Override
    public void update(ScrabbleEvent event) {
        ArrayList<Player> allPlayers = event.getScrabbleModel().getPlayerList();

        for (int i = allPlayers.size() - 1; i >= 0; i--) {
        allPlayersScores.setText(allPlayers.get(i).getName() + ": " + allPlayers.get(i).getScore() + "\n" +
                allPlayers.get(allPlayers.size()-1).getName() + ": " + allPlayers.get(allPlayers.size()-1).getScore());
        }

    }
}
