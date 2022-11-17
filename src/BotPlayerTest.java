import java.io.IOException;

import static org.junit.Assert.*;

public class BotPlayerTest {

    /**
     * Test adding a two-letter word to the Board.
     * @author Emily Tang 101192604
     */
    @org.junit.Test
    public void testTwoLetterWord() throws IOException {
        // create and initialize a board with only a middle letter
        Board board = new Board();
        board.getScrabbleBoard()[7][7].setLetter('A');

        // create a BotPlayer and add letters to its hand
        BotPlayer bot = new BotPlayer("Robert the Bobert");
        String[] lettersToAdd = new String[] {"T", "S", "B", "H", "L", "E", "F"};
        for (String s : lettersToAdd) {
            bot.addLetter(s);
        }


    }

}