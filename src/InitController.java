import javax.swing.*;
import java.io.IOException;

/**
 * Initializes players in a game of Scrabble using JOptionPanes.
 *
 * The first JOptionPane asks the user for the total number of players. The second JOption asks the user how many bot
 * players they would like to play with. The following JOptionPanes asks the user to input the names of the real
 * players.
 */
public class InitController extends JOptionPane {

    /** A JPanel to ask the user how many players are playing in total. */
    private JPanel numPlayersPanel;

    /** A JPanel to ask the user how many bot players they would like to play with. */
    private JPanel numBotsPanel;

    /** A JComboBox to store options for the number of players in total. */
    private JComboBox<Integer> numPlayersComboBox;

    /** A JComboBox to store options for the number of bot players in total. */
    private JComboBox<Integer> numBotsComboBox;

    /**
     * Initializes players in a game of Scrabble.
     * @param scrabbleModel The Game of Scrabble to update.
     * @throws IOException If an I/O error occurs.
     * @author Emily Tang 101192604
     */
    public InitController(ScrabbleModel scrabbleModel) throws IOException {
        // user inputs number of total players
        createNumPlayersPanel();
        int numOfTotalPlayers = 0;
        int result = showConfirmDialog(null, numPlayersPanel, "Number of Players", YES_NO_OPTION);
        switch (result) {
            case OK_OPTION -> numOfTotalPlayers = (int) numPlayersComboBox.getSelectedItem();
            case NO_OPTION, CLOSED_OPTION -> System.exit(0); // end the game
        }

        // user inputs number of bots
        createNumBotsPanel(numOfTotalPlayers);
        int numOfBots = 0;
        result = showConfirmDialog(null, numBotsPanel, "Number of Bots", YES_NO_OPTION);
        switch (result) {
            case OK_OPTION -> numOfBots = (int) numBotsComboBox.getSelectedItem();
            case NO_OPTION, CLOSED_OPTION -> System.exit(0); // end the game
        }

        // create a String array to store the real player's names
        int numOfRealPlayers = numOfTotalPlayers - numOfBots;
        String[] namesOfRealPlayers = new String[numOfRealPlayers];

        // user inputs the names of the real players
        for (int i = 0; i < numOfRealPlayers; i++) {
            String name = "";
            while (name.equals("")) { // player's name must not be empty
                name = showInputDialog("What is Player " + (i + 1) + "'s name?");
                if (name == null) { // user hit the close window button
                    System.exit(0); // end the game
                }
            }
            namesOfRealPlayers[i] = name;
        }

        // update the game
        scrabbleModel.initializeGame(numOfBots, namesOfRealPlayers);
    }

    /**
     * Creates a JPanel to ask the user how many players are playing in total.
     * @author Emily Tang 101192604
     */
    private void createNumPlayersPanel() {
        // create a JPanel to ask user how many players are playing
        numPlayersPanel = new JPanel();
        numPlayersPanel.add(new JLabel("How many players are playing?"));

        // create a JComboBox to allow user to select the num of players using a dropdown menu and add it to the panel
        numPlayersComboBox = new JComboBox<>();
        for (int i = 2; i <= 4; i++) {
            numPlayersComboBox.addItem(i);
        }
        numPlayersPanel.add(numPlayersComboBox);
    }

    /**
     * Creates a JPanel to ask the user how many bot players they would like to play with.
     * @param numOfTotalPlayers An integer representing the number of players in total.
     * @author Emily Tang 101192604
     */
    private void createNumBotsPanel(int numOfTotalPlayers) {
        // create a JPanel to ask user if they would like to play with BotPlayers
        numBotsPanel = new JPanel();
        numBotsPanel.add(new JLabel("Would you like to play with bots?\nIf yes, how many?"));

        // create a JComboBox to allow user to select the num of bots using a dropdown menu and add it to the panel
        // must have at least one real player
        numBotsComboBox = new JComboBox<>();
        for (int i = 0; i < numOfTotalPlayers; i++) {
            numBotsComboBox.addItem(i);
        }
        numBotsPanel.add(numBotsComboBox);
    }

}
