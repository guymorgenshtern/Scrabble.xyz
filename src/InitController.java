import javax.swing.*;
import java.io.IOException;

/**
 * Initializes players in a game of Scrabble using JOptionPanes. The first JOptionPane asks the user for the number of
 * players. The following JOptionPanes asks the user to input the names of the players.
 */
public class InitController extends JOptionPane {

    /**
     * Initializes players in a game of Scrabble.
     * @param game The Game of Scrabble to update.
     * @author Emily Tang 101192604
     */
    public InitController(Game game) throws IOException {
        // create a JPanels to allow longer descriptors
        JPanel numPanel = new JPanel();
        numPanel.add(new JLabel("How many players are playing?"));

        // create a JComboBox to allow user to select the num of players using a dropdown menu and add it to the panel
        JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.addItem(2);
        comboBox.addItem(3);
        comboBox.addItem(4);
        numPanel.add(comboBox);

        // user inputs number of players
        int numOfPlayers = 0;
        int result = showConfirmDialog(null, numPanel, "Number of Players", OK_CANCEL_OPTION);
        switch (result) {
            case OK_OPTION -> numOfPlayers = (int) comboBox.getSelectedItem(); // not-editable, never a problem
            case CANCEL_OPTION -> System.exit(0); // hahahah you don't get to play suckerr
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
        game.initializeGame(nameOfPlayers);
    }
}
