import javax.swing.*;
import java.io.IOException;

/**
 * Initializes players in a game of Scrabble using JOptionPanes. The first JOptionPane asks the user for the number of
 * players. The following JOptionPanes asks the user to input the names of the players.
 */
public class InitController extends JOptionPane {

    /**
     * Initializes players in a game of Scrabble.
     * @param scrabbleModel The Game of Scrabble to update.
     * @author Emily Tang 101192604
     */
    public InitController(ScrabbleModel scrabbleModel) throws IOException {
        // create a JPanel to ask user how many players are playing
        JPanel numPlayersPanel = new JPanel();
        numPlayersPanel.add(new JLabel("How many players are playing?"));

        // create a JComboBox to allow user to select the num of players using a dropdown menu and add it to the panel
        JComboBox<Integer> numPlayersComboBox = new JComboBox<>();
        numPlayersComboBox.addItem(2);
        numPlayersComboBox.addItem(3);
        numPlayersComboBox.addItem(4);
        numPlayersPanel.add(numPlayersComboBox);

        // user inputs number of total players
        int numOfTotalPlayers = 0;
        int result = showConfirmDialog(null, numPlayersPanel, "Number of Players", OK_CANCEL_OPTION);
        switch (result) {
            case OK_OPTION -> numOfTotalPlayers = (int) numPlayersComboBox.getSelectedItem();
            case CANCEL_OPTION -> System.exit(0);
        }

        // create a JPanel to ask user if they would like to play with BotPlayers
        JPanel numBotsPanel = new JPanel();
        numBotsPanel.add(new JLabel("Would you like to play with bots?\nIf yes, how many?"));

        // create a JComboBox to allow user to select the num of bots using a dropdown menu and add it to the panel
        // must have at least one real player
        JComboBox<Integer> numBotsComboBox = new JComboBox<>();
        for (int i = 0; i < numOfTotalPlayers - 1; i++) {
            numBotsComboBox.addItem(i + 1);
        }
        numBotsPanel.add(numBotsComboBox);

        // user inputs number of bots
        int numOfBots = 0;
        result = showConfirmDialog(null, numBotsPanel, "Number of Bots", OK_CANCEL_OPTION);
        switch (result) {
            case OK_OPTION -> numOfBots = (int) numBotsComboBox.getSelectedItem();
            case CANCEL_OPTION -> System.exit(0);
        }

        // create a String array to store the real player's names
        int numOfRealPlayers = numOfTotalPlayers - numOfBots;
        String[] namesOfRealPlayers = new String[numOfRealPlayers];

        // user inputs the names of the real players
        for (int i = 0; i < numOfRealPlayers; i++) {
            String name = "";
            while (name.equals("")) { // player's name must not be empty
                name = showInputDialog("What is Player " + (i + 1) + "'s name?");
            }
            namesOfRealPlayers[i] = name;
        }

        // update the game
        scrabbleModel.initializeGame(numOfBots, namesOfRealPlayers);
    }

    public static void main(String[] args) throws IOException {
        new InitController(new ScrabbleModel());
    }

}
