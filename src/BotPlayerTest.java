import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BotPlayerTest {

    /**
     * Test lettersNeededForInput().
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testLettersNeededForInput() {
        // create a Player and add letters to its hand
        Player player = new Player("Lightning Mcqueen");
        String[] lettersToAdd = new String[] {"R", "E", "B", "A", "E", "N", "E", "D"};
        for (String s : lettersToAdd) {
            player.addLetter(s);
        }
        assertTrue(player.hasLettersNeededForWord("aberdeen"));
    }

    /**
     * Test adding a word to the Board at the very beginning of a Scrabble game.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testPlayAtBeginningOfScrabble() throws IOException {
        // create and initialize a board with only a middle letter
        Board board = new Board();
        board.getScrabbleBoard()[7][7].setLetter('D');

        // create a BotPlayer and add letters to its hand
        BotPlayer bot = new BotPlayer("Robert the Bobert");
        assertEquals("Robert the Bobert", bot.getName());
        assertEquals(0, bot.getAvailableLetters().size());
        assertEquals(0, bot.getScore());
        String[] lettersToAdd = new String[] {"R", "E", "B", "A", "E", "N", "E"};
        for (String s : lettersToAdd) {
            bot.addLetter(s);
        }
        assertEquals(7, bot.getAvailableLetters().size());

        ScrabbleMove actualScrabbleMove = bot.play(board);
        assertEquals(ScrabbleModel.Direction.HORIZONTAL, actualScrabbleMove.getDirection());
        assertEquals(bot, actualScrabbleMove.getPlayer());

        // test board clicks
        ArrayList<BoardClick> actualBoardClicks = actualScrabbleMove.getCoords();
        for (int i = 0; i < actualBoardClicks.size(); i++) {
            assertEquals(7, actualBoardClicks.get(i).coords()[1]);
        }
        assertEquals(3, actualBoardClicks.get(0).coords()[0]);
        assertEquals(4, actualBoardClicks.get(1).coords()[0]);
        assertEquals(5, actualBoardClicks.get(2).coords()[0]);
        assertEquals(6, actualBoardClicks.get(3).coords()[0]);
        assertEquals(8, actualBoardClicks.get(4).coords()[0]);
        assertEquals(9, actualBoardClicks.get(5).coords()[0]);
        assertEquals(10, actualBoardClicks.get(6).coords()[0]);
        assertEquals("A", actualBoardClicks.get(0).letter());
        assertEquals("B", actualBoardClicks.get(1).letter());
        assertEquals("E", actualBoardClicks.get(2).letter());
        assertEquals("R", actualBoardClicks.get(3).letter());
        assertEquals("E", actualBoardClicks.get(4).letter());
        assertEquals("E", actualBoardClicks.get(5).letter());
        assertEquals("N", actualBoardClicks.get(6).letter());
    }

    /**
     * Test adding a vertical two-letter word to the Board.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testPlayVerticalTwoLetterWord() throws IOException {
        // create and initialize a Board
        Board board = new Board();
        board.getScrabbleBoard()[7][5].setLetter('B');
        board.getScrabbleBoard()[7][6].setLetter('A');
        board.getScrabbleBoard()[7][7].setLetter('N');
        board.getScrabbleBoard()[7][8].setLetter('A');
        board.getScrabbleBoard()[7][9].setLetter('N');
        board.getScrabbleBoard()[7][10].setLetter('A');

        // create a BotPlayer and add letters to its hand
        BotPlayer bot = new BotPlayer("Dennis the Menace");
        assertEquals(0, bot.getAvailableLetters().size());
        String[] lettersToAdd = new String[] { "O", "O", "O", "O", "O", "O", "O" };
        for (String s : lettersToAdd) {
            bot.addLetter(s);
        }
        assertEquals(7, bot.getAvailableLetters().size());

        ScrabbleMove actualMove = bot.play(board);
        assertEquals(8, actualMove.getCoords().get(0).coords()[0]);
        assertEquals(5, actualMove.getCoords().get(0).coords()[1]);
        assertEquals("O", actualMove.getCoords().get(0).letter());
        assertEquals(ScrabbleModel.Direction.VERTICAL, actualMove.getDirection());
        assertEquals(bot, actualMove.getPlayer());
    }

    /**
     * Test adding a horizontal two-letter word to the Board.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testPlayHorizontalTwoLetterWord() throws IOException {
        // create and initialize a Board
        Board board = new Board();
        board.getScrabbleBoard()[5][7].setLetter('A');
        board.getScrabbleBoard()[6][7].setLetter('P');
        board.getScrabbleBoard()[7][7].setLetter('P');
        board.getScrabbleBoard()[8][7].setLetter('L');
        board.getScrabbleBoard()[9][7].setLetter('E');
        board.getScrabbleBoard()[6][8].setLetter('A');

        // create a BotPlayer and add letters to its hand
        BotPlayer bot = new BotPlayer("Kirby");
        String[] lettersToAdd = new String[] { "A", "A", "A" };
        for (String s : lettersToAdd) {
            bot.addLetter(s);
        }
        assertEquals(3, bot.getAvailableLetters().size());

        ScrabbleMove actualMove = bot.play(board);
        assertEquals(8, actualMove.getCoords().get(0).coords()[0]);
        assertEquals(8, actualMove.getCoords().get(0).coords()[1]);
        assertEquals("A", actualMove.getCoords().get(0).letter());
        assertEquals(ScrabbleModel.Direction.HORIZONTAL, actualMove.getDirection());
        assertEquals(bot, actualMove.getPlayer());
    }

}