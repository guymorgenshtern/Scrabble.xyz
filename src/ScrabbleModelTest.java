import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ScrabbleModelTest {

    /**
     * Test initializePlayers() with no BotPlayers in ScrabbleModel.
     * @throws IOException
     */
    @org.junit.Test
    public void testInitializePlayers() throws IOException {
        ScrabbleModel scrabbleModel = new ScrabbleModel();
        assertEquals(0, scrabbleModel.getPlayerList().size()); // ScrabbleModel has not initialized players yet

        String[] playerNames = new String[] { "Guy", "Francisco", "Emily", "Alex" };
        scrabbleModel.initializeGame(0, playerNames);

        ArrayList<Player> initializedPlayerList = scrabbleModel.getPlayerList();
        assertEquals(4, initializedPlayerList.size());
        for (int i = 0; i < initializedPlayerList.size(); i++) {
            assertTrue(initializedPlayerList.get(i) instanceof Player);
            assertEquals(playerNames[i], initializedPlayerList.get(i).getName());
            assertEquals(0, initializedPlayerList.get(i).getScore());
        }
    }

    /**
     * Test initializePlayers() with a BotPlayer in ScrabbleModel.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testInitializePlayersWithBot() throws IOException {
        ScrabbleModel scrabbleModel = new ScrabbleModel();
        assertEquals(0, scrabbleModel.getPlayerList().size()); // game has not initialized players yet

        String[] playerNames = new String[] { "Guy", "Francisco", "Emily" };
        scrabbleModel.initializeGame(1, playerNames);

        ArrayList<Player> initializedPlayerList = scrabbleModel.getPlayerList();
        assertEquals(4, initializedPlayerList.size());
        assertTrue(initializedPlayerList.get(0) instanceof BotPlayer);
        assertEquals("Dumb Dumpling", initializedPlayerList.get(0).getName());
        assertEquals(0, initializedPlayerList.get(0).getScore());
        for (int i = 0; i < playerNames.length; i++) {
            assertTrue(initializedPlayerList.get(i + 1) instanceof Player);
            assertEquals(playerNames[i], initializedPlayerList.get(i + 1).getName()); // checks that name is the same
            assertEquals(0, initializedPlayerList.get(i + 1).getScore());    // checks that score is zero
        }
    }
}
