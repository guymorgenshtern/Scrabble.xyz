import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

/**
 * ScorePanel displays all players and their current scores.
 * @author Francisco De Grano 101147447. Edited by Guy Morgenshtern 101151430 and Emily Tang 101192604.
 */
public class ScorePanel extends JPanel implements ScrabbleView {

    private static final String SPACE = "               ";

    /** An ArrayList of JLabels to display the players and their current scores. */
    private final ArrayList<JLabel> playerLabels;

    /**
     * Creates a JPanel to display all the players and their current scores.
     * @author Francisco De Grano 101147447. Edited by Guy Morgenshtern 101151430 and Emily Tang 101192604.
     */
    public ScorePanel() {
        // initialize the ScorePanel with a FlowLayout
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));
        setSize(900, 100);

        // initialize a title for the panel
        JLabel scoreboardLabel = new JLabel("Scoreboard" + SPACE);
        scoreboardLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        add(scoreboardLabel);

        // initialize an ArrayList of JLabels to display the players and their current scores
        playerLabels = new ArrayList<>();
        for (int i = 0; i < 4; i++) { // maximum number of players is 4
            JLabel l = new JLabel("");
            l.setFont(new Font("Dialog", Font.PLAIN, 14));
            playerLabels.add(l); // add the label to the playerLabels list
            add(l); // add the label to the ScorePanel
        }

        // finish initializing the ScorePanel
        setBackground(new Color(253, 239, 117));
        setVisible(true);
    }

    /**
     * Updates the ScorePanel to display the players and their current scores.
     * @param event A ScrabbleEvent that occurs.
     * @author Guy Morgenshtern 101151430 and Emily Tang 101192604.
     */
    @Override
    public void update(ScrabbleEvent event) {
        // get the current state of the players
        ArrayList<Player> players = event.getScrabbleModel().getPlayerList();

        // update the players in the ScorePanel
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            String text = p.getName() + ": " + p.getScore();
            if (i + 1 < players.size()) {
                text += SPACE;
            }
            playerLabels.get(i).setText(text);
        }
    }
}