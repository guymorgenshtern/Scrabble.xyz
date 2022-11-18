import com.zetcode.Library;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A BotPlayer in the game of Scrabble.
 */
public class BotPlayer extends Player {

    /** A Library to use to create valid words. */
    private final Library library;

    /**
     * Creates a BotPlayer with the specified name. BotPlayer starts off with an empty hand and a score of zero.
     * @param name A String representing the name of the BotPlayer.
     * @throws IOException If an I/O error occurs.
     * @author Emily Tang 101192604
     */
    public BotPlayer(String name) throws IOException {
        super(name);
        library = new Library();
    }

    /**
     * @param board The Board that is currently in play.
     * @return A ScrabbleMove that the BotPlayer can make. Returns null if the BotPlayer cannot make a move.
     */
    public ScrabbleMove play(Board board) {
        // TODO: IMPLEMENTATION FOR PLAYING A TWO LETTER WORD

        Square[][] scrabbleBoard = board.getScrabbleBoard();

        // iterate through the board from left-to-right, top-to-bottom looking for a square with a letter
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (scrabbleBoard[row][col].getLetter() != ' ') {
                    // check surrounding squares for empty spaces
                    int numOfSurroundingEmptySpaces = 0;
                    // check to the left of the square
                    if (col != 0 && scrabbleBoard[row][col - 1].getLetter() == ' ') {
                        numOfSurroundingEmptySpaces++;
                    }
                    // check to the right of the square
                    if (col != Board.SIZE - 1 && scrabbleBoard[row][col + 1].getLetter() == ' ') {
                        numOfSurroundingEmptySpaces++;
                    }
                    // check to the top of the square
                    if (row != 0 && scrabbleBoard[row + 1][col].getLetter() == ' ') {
                        numOfSurroundingEmptySpaces++;
                    }
                    // check to the bottom of the square
                    if (row != Board.SIZE - 1 && scrabbleBoard[row - 1][col].getLetter() == ' ') {
                        numOfSurroundingEmptySpaces++;
                    }

                    // at the beginning of the game
                    if (numOfSurroundingEmptySpaces == 4) {
                        // temporarily add the letter from the board to the hand
                        // this letter will be removed once the move is validated in ScrabbleModel
                        String boardLetter = scrabbleBoard[row][col].getLetter() + ""; // getLetter() returns char
                        addLetter(boardLetter);

                        // iterate through the list of valid words provided by the dictionary
                        String validWord = "";
                        for (int i = 0; i < library.getValidWords().size() && validWord.equals(""); i++) {
                            String s = library.getValidWords().get(i);
                            if (hasLettersNeededForWord(s) && s.length() > 1 && s.contains(boardLetter.toLowerCase())) {
                                validWord = s;
                            }
                        }

                        // determine where the letter from the board is in the valid word
                        int index = -1;
                        for (int i = 0; i < validWord.length(); i++) {
                            if (boardLetter.equals((validWord.charAt(i) + "").toUpperCase())) {
                                index = i;
                            }
                        }

                        // create an ArrayList of BoardClicks
                        ArrayList<BoardClick> boardClicks = new ArrayList<>();
                        for (int i = 0; i < validWord.length(); i++) {
                            boardClicks.add(new BoardClick(new int[] { Board.SIZE / 2 - index + i, Board.SIZE / 2 }, validWord.charAt(i) + ""));
                        }

                        return new ScrabbleMove(boardClicks, ScrabbleModel.Direction.HORIZONTAL, this);
                    } else if (numOfSurroundingEmptySpaces == 3) {

                    }
                }
            }
        }
        return null;
    }

    /*
    private boolean hasLettersNeededForWord(String input)
    - could be helpful to create legal words... would need to temporarily pass in an additional letter to my hand

    * start small, need to be able to fit bot moves in while board keeps expanding *

    1. find a spot to put words... scrabblemodel can pass in board
    2. determine if the word is horizontal or vertical
    3. find a word to put in... probably need a library
    4. plop in a word... what a daunting task
     */
}
