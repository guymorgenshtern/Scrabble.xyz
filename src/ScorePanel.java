import javax.naming.Name;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates a JPanel to display all the Players Scores in the game.
 * @author Francisco De Grano 101147447
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
        this.setLayout(new GridLayout(1, 4));
        this.setSize(500, 250);

        // Player Score Label
        allPlayersScores = new JLabel("");
        JLabel scoreViewLabel = new JLabel("LEADERBOARD", SwingConstants.LEFT);
        scoreViewLabel.setForeground(Color.BLACK);

        this.add(allPlayersScores);
        allPlayersScores.setForeground(Color.BLACK);

        this.setBackground(new Color(122, 117, 253));
        this.setVisible(true);
    }


    @Override
    public void update(ScrabbleEvent event) {
        ArrayList<Player> allPlayers = event.getScrabbleModel().getPlayerList();

        for (int i = allPlayers.size() - 1; i >= 0; i--) {
        allPlayersScores.setText(allPlayers.get(i).getName() + " " + allPlayers.get(i).getScore());

        }

    }
}
