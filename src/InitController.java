import javax.swing.*;

/**
 * Initializes players in a game of Scrabble using JOptionPanes.
 */
public class InitController extends JOptionPane {

    /**
     * Initializes players in a game of Scrabble.
     * @param game The Game of Scrabble to update.
     * @author Emily Tang 101192604
     */
    public InitController(Game game) {
        // user inputs the number of players
        int numOfPlayers = 0;
        while (!(numOfPlayers > 1 && numOfPlayers <= 4)) {
            try {
                numOfPlayers = Integer.parseInt(showInputDialog("How many players are playing?\n2 to 4 " +
                        "players can play at a time."));
            } catch (NumberFormatException e) { // if user enters a non-numeric value
                showMessageDialog(new JFrame(), "Please enter a valid numeric value.", "Alert",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        // create a String array to store the player's names
        String[] nameOfPlayers = new String[numOfPlayers];

        // user inputs the names of the players
        for (int i = 0; i < numOfPlayers; i++) {
            String name = "";
            while (name.equals("")) { // player's name must not be empty
                name = showInputDialog("What is Player " + (i + 1) + "'s name?");
            }
            nameOfPlayers[i] = name;
        }

        // update the game
        game.initializePlayers(nameOfPlayers);
    }
}
