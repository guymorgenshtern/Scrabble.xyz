import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class GameTest {

    /**
     * Test initializePlayers() in Game.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testInitializePlayers() throws IOException {
        Game game = new Game();
        assertNull(null); // game has not initialized players yet

        String[] playerNames = new String[] {"Guy", "Francisco", "Emily", "Alex"};
        game.initializePlayers(playerNames);

        ArrayList<Player> initializedPlayerList = game.getPlayerList();
        assertEquals(playerNames.length, initializedPlayerList.size());
        for (int i = 0; i < initializedPlayerList.size(); i++) {
            assertEquals(playerNames[i], initializedPlayerList.get(i).getName()); // checks that name is the same
            assertEquals(0, initializedPlayerList.get(i).getScore());    // checks that score is zero
        }
    }

    /**
     * Test dealLetters() in Game.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testDealLetters() throws IOException {
        Game game = new Game();
        game.initializePlayers(new String[] {"Guy"});

        assertEquals(0, game.getPlayerList().get(0).getAvailableLetters().size());

        game.dealLetters();

        // determine the amount of letters the player has
        Collection<Integer> numberOfLetters = game.getPlayerList().get(0).getAvailableLetters().values();
        int sum = 0;
        for (int number : numberOfLetters) {
            sum += number;
        }
        assertEquals(7, sum);
    }

    /**
     * Test initializeBoard() in Game.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testInitializeBoard() throws IOException {
        Game game = new Game();

        // there should be no letter in the middle of the board
        Board board = game.getBoard();
        int centerSquare = (board.getSize() - 1) / 2;
        assertNull(board.getTileOnBoard(centerSquare, centerSquare);

        // there should be a letter in the middle of the board
        game.initializeBoard();
        assertNotNull(game.getBoard().getTileOnBoard(centerSquare, centerSquare));
    }

}
