import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

    /**
     * tests if score is calculated and given to player correctly
     * @throws IOException
     * @author Guy Morgenshtern - 101151430
     */
    @org.junit.Test
    public void testCalculateMoveScore() throws IOException {
        ScrabbleModel scrabbleModel = new ScrabbleModel();

        String[] playerNames = new String[] { "Guy", "Francisco", "Emily" };
        scrabbleModel.initializeGame(0, playerNames);

        ArrayList<BoardClick> clicks = new ArrayList<>();
        scrabbleModel.getBoard().setSquare('H', 7, 7);
        scrabbleModel.getBoard().setSquare('E', 8, 7);
        scrabbleModel.getBoard().setSquare('L', 9, 7);
        scrabbleModel.getBoard().setSquare('L', 10, 7);
        scrabbleModel.getBoard().setSquare('O', 11, 7);

        clicks.add(new BoardClick(new int[]{8, 7}, "E"));
        clicks.add(new BoardClick(new int[]{9, 7}, "L"));
        clicks.add(new BoardClick(new int[]{10, 7}, "L"));
        clicks.add(new BoardClick(new int[]{11, 7}, "O"));

        ScrabbleMove move = new ScrabbleMove(clicks, ScrabbleModel.Direction.VERTICAL, scrabbleModel.getPlayerList().get(0));
        scrabbleModel.play(move);

        assert(scrabbleModel.getPlayerList().get(0).getScore() == 9);
    }

    /**
     * Tests if letter stays on board when valid move is played
     * @throws IOException
     * @author Guy Morgenshtern - 101151430
     */
    @org.junit.Test
    public void testPlayValidMove() throws IOException {
        ScrabbleModel scrabbleModel = new ScrabbleModel();

        String[] playerNames = new String[] { "Guy", "Francisco", "Emily" };
        scrabbleModel.initializeGame(0, playerNames);

        ArrayList<BoardClick> clicks = new ArrayList<>();
        scrabbleModel.getBoard().setSquare('H', 7, 7);
        scrabbleModel.getBoard().setSquare('E', 8, 7);
        scrabbleModel.getBoard().setSquare('L', 9, 7);
        scrabbleModel.getBoard().setSquare('L', 10, 7);
        scrabbleModel.getBoard().setSquare('O', 11, 7);

        clicks.add(new BoardClick(new int[]{8, 7}, "E"));
        clicks.add(new BoardClick(new int[]{9, 7}, "L"));
        clicks.add(new BoardClick(new int[]{10, 7}, "L"));
        clicks.add(new BoardClick(new int[]{11, 7}, "O"));

        ScrabbleMove move = new ScrabbleMove(clicks, ScrabbleModel.Direction.VERTICAL, scrabbleModel.getPlayerList().get(0));
        scrabbleModel.play(move);

        assert(scrabbleModel.getBoard().getTileOnBoard(11, 7).getLetter() == 'O');
    }

    /**
     * Tests if letters cleared after invalid move
     * @throws IOException
     * @author Guy Morgenshtern - 101151430
     */
    @org.junit.Test
    public void testPlayInvalidMove() throws IOException {
        ScrabbleModel scrabbleModel = new ScrabbleModel();

        String[] playerNames = new String[] { "Guy", "Francisco", "Emily" };
        scrabbleModel.initializeGame(0, playerNames);

        ArrayList<BoardClick> clicks = new ArrayList<>();
        scrabbleModel.getBoard().setSquare('H', 7, 7);
        scrabbleModel.getBoard().setSquare('G', 8, 7);
        scrabbleModel.getBoard().setSquare('L', 9, 7);
        scrabbleModel.getBoard().setSquare('L', 10, 7);
        scrabbleModel.getBoard().setSquare('O', 11, 7);

        clicks.add(new BoardClick(new int[]{8, 7}, "E"));
        clicks.add(new BoardClick(new int[]{9, 7}, "L"));
        clicks.add(new BoardClick(new int[]{10, 7}, "L"));
        clicks.add(new BoardClick(new int[]{11, 7}, "O"));

        ScrabbleMove move = new ScrabbleMove(clicks, ScrabbleModel.Direction.VERTICAL, scrabbleModel.getPlayerList().get(0));
        scrabbleModel.play(move);

        assert(scrabbleModel.getBoard().getTileOnBoard(11, 7).getLetter() == ' ');
    }

    @org.junit.Test
    public void testSaveScrabble() throws IOException {
        ScrabbleModel scrabbleModel = new ScrabbleModel();

        String[] playerNames = new String[] { "Guy", "Francisco", "Emily" };
        scrabbleModel.initializeGame(0, playerNames);
        scrabbleModel.getPlayerList().get(0).setScore(10);
        scrabbleModel.saveScrabble("test");

        FileInputStream fileIn = new FileInputStream("test_player_list.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ScrabbleModel newModel = ScrabbleModel.loadScrabble("test");

        assert(in instanceof ObjectInputStream);
    }
}
