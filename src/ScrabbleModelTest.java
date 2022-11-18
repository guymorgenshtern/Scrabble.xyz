import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ScrabbleModelTest {

    /**
     * Test initializePlayers() in Game.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testInitializePlayers() throws IOException {
        ScrabbleModel scrabbleModel = new ScrabbleModel();
        assertNull(null); // game has not initialized players yet

        String[] playerNames = new String[] {"Guy", "Francisco", "Emily", "Alex"};
        scrabbleModel.initializeGame(playerNames);

        ArrayList<Player> initializedPlayerList = scrabbleModel.getPlayerList();
        assertEquals(playerNames.length, initializedPlayerList.size());
        for (int i = 0; i < initializedPlayerList.size(); i++) {
            assertEquals(playerNames[i], initializedPlayerList.get(i).getName()); // checks that name is the same
            assertEquals(0, initializedPlayerList.get(i).getScore());    // checks that score is zero
        }
    }
}