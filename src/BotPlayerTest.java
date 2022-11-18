import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BotPlayerTest {

    /**
     * Test adding a two-letter word to the Board.
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

        // create expected ScrabbleMove
        ArrayList<BoardClick> boardClicks = new ArrayList<>();
        boardClicks.add(new BoardClick(new int[] {3, 7}, "a"));
        boardClicks.add(new BoardClick(new int[] {4, 7}, "b"));
        boardClicks.add(new BoardClick(new int[] {5, 7}, "e"));
        boardClicks.add(new BoardClick(new int[] {6, 7}, "r"));
        boardClicks.add(new BoardClick(new int[] {7, 7}, "d"));
        boardClicks.add(new BoardClick(new int[] {8, 7}, "e"));
        boardClicks.add(new BoardClick(new int[] {9, 7}, "e"));
        boardClicks.add(new BoardClick(new int[] {10, 7}, "n"));
        assertEquals(new ScrabbleMove(boardClicks, ScrabbleModel.Direction.HORIZONTAL, bot), bot.play(board));
    }

}