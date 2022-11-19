import com.zetcode.Library;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A BotPlayer in the game of Scrabble.
 */
public class BotPlayer extends Player {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int TOP = 2;
    private static final int BOTTOM = 3;

    public enum SquareStatus { EMPTY, NOT_EMPTY, DOES_NOT_EXIST };

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
     * @param board A 2D array of squares representing the Scrabble board.
     * @param row An integer representing the row of the Square to check the surroundings of.
     * @param col An integer representing the column of the Square to check the surroundings of.
     * @return A SquareStatus array representing the status of the surrounding squares.
     * @author Emily Tang 101192604
     */
    private SquareStatus[] getStatusOfSurroundingSquares(Square[][] board, int row, int col) {
        // create a SquareStatus array and initialize all indices as NOT_EMPTY
        SquareStatus[] statusOfSurroundingSquares = new SquareStatus[4];
        Arrays.fill(statusOfSurroundingSquares, SquareStatus.NOT_EMPTY);

        // check to the left of the square
        if (col == 0) {
            statusOfSurroundingSquares[LEFT] = SquareStatus.DOES_NOT_EXIST;
        } else if (board[row][col - 1].getLetter() == ' ') {
            statusOfSurroundingSquares[LEFT] = SquareStatus.EMPTY;
        }
        // check to the right of the square
        if (col == Board.SIZE - 1) {
            statusOfSurroundingSquares[RIGHT] = SquareStatus.DOES_NOT_EXIST;
        } else if (board[row][col + 1].getLetter() == ' ') {
            statusOfSurroundingSquares[RIGHT] = SquareStatus.EMPTY;
        }
        // check to the top of the square
        if (row == 0) {
            statusOfSurroundingSquares[TOP] = SquareStatus.DOES_NOT_EXIST;
        } else if (board[row + 1][col].getLetter() == ' ') {
            statusOfSurroundingSquares[TOP] = SquareStatus.EMPTY;
        }
        // check to the bottom of the square
        if (row == Board.SIZE - 1) {
            statusOfSurroundingSquares[BOTTOM] = SquareStatus.DOES_NOT_EXIST;
        } else if (board[row - 1][col].getLetter() == ' ') {
            statusOfSurroundingSquares[BOTTOM] = SquareStatus.EMPTY;
        }

        return statusOfSurroundingSquares;
    }

    /**
     * @param statusOfSurroundingSquares A SquareStatus array representing the status of the surrounding spaces.
     * @return An integer representing the number of surrounding empty spaces.
     * @author Emily Tang 101192604
     */
    private int getNumSurroundingEmptySquares(SquareStatus[] statusOfSurroundingSquares) {
        int numOfSurroundingEmptySpaces = 0;
        for (SquareStatus s : statusOfSurroundingSquares) {
            if (s == SquareStatus.EMPTY) {
                numOfSurroundingEmptySpaces++;
            }
        }
        return numOfSurroundingEmptySpaces;
    }

    /**
     * @param board The Board that is currently in play.
     * @return A ScrabbleMove that the BotPlayer can make. Returns null if the BotPlayer cannot make a move.
     */
    public ScrabbleMove play(Board board) {
        Square[][] scrabbleBoard = board.getScrabbleBoard();

        // iterate through the board from left-to-right, top-to-bottom looking for a square with a letter
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (scrabbleBoard[row][col].getLetter() != ' ') { // found a square with a letter
                    // check the status of the surrounding squares and determine the number of surrounding empty squares
                    SquareStatus[] statusOfSurroundingSquares = getStatusOfSurroundingSquares(scrabbleBoard, row, col);
                    int numOfSurroundingEmptySquares = getNumSurroundingEmptySquares(statusOfSurroundingSquares);

                    // at the beginning of the game
                    if (numOfSurroundingEmptySquares == 4) {
                        // temporarily add the letter from the board to the hand
                        // this letter will be removed once the move is validated in ScrabbleModel
                        String boardLetter = scrabbleBoard[row][col].getLetter() + ""; // getLetter() returns char
                        addLetter(boardLetter);

                        // iterate through the list of valid words provided by the dictionary
                        String validWord = "";
                        for (int i = 0; i < library.getValidWords().size() && validWord.equals(""); i++) {
                            String s = library.getValidWords().get(i);
                            if (hasLettersNeededForWord(s) && s.length() > 1 && s.contains(boardLetter.toLowerCase())) {
                                validWord = s.toUpperCase(); // letters are displayed as uppercase on the board
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
                            if (i != index) { // do not add the pre-existing letter into ScrabbleMove
                                boardClicks.add(new BoardClick(new int[]{Board.SIZE / 2 - index + i, Board.SIZE / 2}, validWord.charAt(i) + ""));
                            }
                        }

                        return new ScrabbleMove(boardClicks, ScrabbleModel.Direction.HORIZONTAL, this);

                    } else if (numOfSurroundingEmptySquares == 2 || numOfSurroundingEmptySquares == 3) {
                        // temporarily add the letter from the board to the hand
                        // this letter will be removed once the move is validated in ScrabbleModel
                        String boardLetter = scrabbleBoard[row][col].getLetter() + ""; // getLetter() returns char
                        addLetter(boardLetter);

                        // determine if the letter part of a horizontally-placed or vertically-placed word
                        ScrabbleModel.Direction direction = null; // the direction of the word the Bot will place
                        if (statusOfSurroundingSquares[LEFT] == SquareStatus.NOT_EMPTY
                            || statusOfSurroundingSquares[RIGHT] == SquareStatus.NOT_EMPTY) {
                            direction = ScrabbleModel.Direction.VERTICAL;
                        } else {
                            direction = ScrabbleModel.Direction.HORIZONTAL;
                        }

                        // find a two-letter word that begins with the board letter
                        String validTwoLetterWord = "";
                        for (int i = 0; i < library.getValidWords().size() && validTwoLetterWord.equals(""); i++) {
                            String s = library.getValidWords().get(i);
                            if (hasLettersNeededForWord(s) && s.length() == 2
                                    && s.substring(0, 1).toUpperCase().equals(boardLetter)
                                    && !s.substring(1).toUpperCase().equals(boardLetter)) { // second letter used cannot be the board letter
                                validTwoLetterWord = s.toUpperCase(); // letters are displayed as uppercase on the board
                            }
                        }

                        // if a word is found, determine if the spaces next to the letter-to-be-placed are empty
                        if (direction == ScrabbleModel.Direction.HORIZONTAL && !validTwoLetterWord.equals("")
                                && getNumSurroundingEmptySquares(getStatusOfSurroundingSquares(scrabbleBoard, row, col + 1)) == 3) {
                            // create an ArrayList of BoardClicks to add to a new ScrabbleMove
                            ArrayList<BoardClick> boardClicks = new ArrayList<>();
                            boardClicks.add(new BoardClick(new int[] { row, col + 1 }, validTwoLetterWord.charAt(1) + ""));
                            return new ScrabbleMove(boardClicks, direction, this);
                        } else if (direction == ScrabbleModel.Direction.VERTICAL && !validTwoLetterWord.equals("")
                                && getNumSurroundingEmptySquares(getStatusOfSurroundingSquares(scrabbleBoard, row + 1, col)) == 3) {
                            // create an ArrayList of BoardClicks to add to a new ScrabbleMove
                            ArrayList<BoardClick> boardClicks = new ArrayList<>();
                            boardClicks.add(new BoardClick(new int[] { row + 1, col }, validTwoLetterWord.charAt(1) + ""));
                            return new ScrabbleMove(boardClicks, direction, this);
                        }

                        // could not find a valid word to add to the board, remove the board letter from the hand
                        removeLetter(boardLetter);
                    }
                }
            }
        }
        return null; // couldn't find a word to place, so mr. bitty bot is going to pass his turn
    }

}
