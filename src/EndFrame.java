import javax.swing.*;

/**
 * EndFrame for when the scrabble game ends and displays the leaderboard
 */
public class EndFrame extends JFrame implements EndView {
    public EndFrame() {
    }

    /**
     * Updates the GameEvent
     * @param e event for the game
     * @author Alexander Hum 101180821
     */
    @Override
    public void update(GameEvent e) {
        String message = "Congratulations " + e.getPlayerList().get(0).getName() + "!\nLeaderboard";
        for(int i=0; i < playerList.size; i++) {
            message += "\n" + e.getPlayerList().get(i).getName() + " " + e.getPlayerList().get(i).getScore();
        }
        JOptionPane.showMessageDialog(this, message);
    }
}
