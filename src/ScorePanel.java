import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates a JPanel to display all the Players Scores in the game.
 * @author Francisco De Grano 101147447 Blah
 */
public class ScorePanel extends JPanel implements ScrabbleView {

    /** Component for current players scores */
    ArrayList<JLabel> scores;


    /**
     * ScorePanel Creates the layout and location for the Player Scores
     * @param scrabbleModel The Game of Scrabble to update
     *
     */
    public ScorePanel(ScrabbleModel scrabbleModel) {
        super();
        this.setLayout(new GridLayout(1, 7));
        this.setSize(900, 250);

        // Player Score Label
        JLabel scoreViewLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        scoreViewLabel.setForeground(Color.BLACK);
        scores = new ArrayList<>();

        this.add(scoreViewLabel);
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel("", SwingConstants.RIGHT);
            scores.add(l);
            this.add(l);
        }


        this.setBackground(new Color(253, 239, 117));
        this.setVisible(true);
    }


    @Override
    public void update(ScrabbleEvent event) {

        ArrayList<Player> allPlayers = event.getScrabbleModel().getPlayerList();

        System.out.println("here1");
        for (int i = 0; i < allPlayers.size(); i++) {
            System.out.println("here");
            scores.get(i).setText(allPlayers.get(i).getName() + ": " + allPlayers.get(i).getScore());
        }
    }
}
