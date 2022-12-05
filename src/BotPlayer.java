import com.zetcode.Library;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A BotPlayer in the game of Scrabble.
 *
 * If the BotPlayer goes first, it will play the first valid word it can make.
 * During any other turn, the BotPlayer will play the first valid two-letter word it can make. The first letter of the
 * valid word will always be a pre-existing letter on the board.
 */
public class BotPlayer extends Player {

    /** An integer representing the index of an array to the left of the current Square. */
    private static final int LEFT = 0;

    /** An integer representing the index of an array to the right of the current Square. */
    private static final int RIGHT = 1;

    /** An integer representing the index of an array to the top of the current Square. */
    private static final int TOP = 2;

    /** An integer representing the index of an array to the bottom of the current Square. */
    private static final int BOTTOM = 3;

    /** A Square can either be empty (has a letter), not empty (does not have a letter), or does not exist (out of
     * bounds). */
    public enum SquareStatus { EMPTY, NOT_EMPTY, DOES_NOT_EXIST };

    /** An ArrayList of Strings representing all the valid words. */
    private final ArrayList<String> library;

    /**
     * Creates a BotPlayer with the specified name. BotPlayer starts off with an empty hand and a score of zero.
     * @param name A String representing the name of the BotPlayer.
     * @throws IOException If an I/O error occurs.
     * @author Emily Tang 101192604
     */
    public BotPlayer(String name) throws IOException {
        super(name);
        library = (new Library()).getValidWords();
    }

    /**
     * @param scrabbleBoard The Board that is currently in play.
     * @param row An integer representing the row of the Square to check the surroundings of.
     * @param col An integer representing the column of the Square to check the surroundings of.
     * @return A SquareStatus array representing the status of the surrounding squares.
     * @author Emily Tang 101192604
     */
    private SquareStatus[] getStatusOfSurroundingSquares(Board scrabbleBoard, int row, int col) {
        Square[][] board = scrabbleBoard.getScrabbleBoard();

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
        if (col == scrabbleBoard.getNumCols() - 1) {
            statusOfSurroundingSquares[RIGHT] = SquareStatus.DOES_NOT_EXIST;
        } else if (board[row][col + 1].getLetter() == ' ') {
            statusOfSurroundingSquares[RIGHT] = SquareStatus.EMPTY;
        }
        // check to the top of the square
        if (row == 0) {
            statusOfSurroundingSquares[TOP] = SquareStatus.DOES_NOT_EXIST;
        } else if (board[row - 1][col].getLetter() == ' ') {
            statusOfSurroundingSquares[TOP] = SquareStatus.EMPTY;
        }
        // check to the bottom of the square
        if (row == scrabbleBoard.getNumRows() - 1) {
            statusOfSurroundingSquares[BOTTOM] = SquareStatus.DOES_NOT_EXIST;
        } else if (board[row + 1][col].getLetter() == ' ') {
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
     * Adds the pre-existing letter from the board to the BotPlayer's hand. This letter will be removed once the move is
     * validated in ScrabbleModel.
     * @param boardLetter A character representation of the letter to add to the hand.
     * @return A String representation of the letter that was added to the hand.
     * @author Emily Tang 101192604
     */
    private String addBoardLetterToHand(char boardLetter) {
        String letter = boardLetter + "";
        addLetter(letter);
        return letter;
    }

    private boolean canAddLetter(SquareStatus[] statusOfSurroundingSquares, int dir1, int dir2, int dir3) {
        return statusOfSurroundingSquares[dir1] != SquareStatus.NOT_EMPTY
                && statusOfSurroundingSquares[dir2] != SquareStatus.NOT_EMPTY
                && statusOfSurroundingSquares[dir3] != SquareStatus.NOT_EMPTY;
    }

    private boolean canAddLetterHorizontally(SquareStatus[] statusOfSurroundingSquares) {
        return canAddLetter(statusOfSurroundingSquares, TOP, RIGHT, BOTTOM);
    }

    private boolean canAddLetterVertically(SquareStatus[] statusOfSurroundingSquares) {
        return canAddLetter(statusOfSurroundingSquares, RIGHT, BOTTOM, LEFT);
    }

    private int numOccurrencesOfCharInWord(String word, String check) {
        int numOccurrences = 0;
        char checkChar = check.charAt(0);
        for (char c : word.toCharArray()) {
            if (c == checkChar) {
                numOccurrences++;
            }
        }
        return numOccurrences;
    }

    private String findLongestWord(ArrayList<String> validWords, int lengthOfLongestWordToBePlayed) {
        if (validWords.size() > 0) {
            int indexOfLongestWord = 0;
            int lengthOfLongestWordInList = 0;
            for (int i = 0; i < validWords.size(); i++) {
                if (validWords.get(i).length() == lengthOfLongestWordToBePlayed) {
                    return validWords.get(i);
                } else if (validWords.get(i).length() > lengthOfLongestWordInList) {
                    lengthOfLongestWordInList = validWords.get(i).length();
                    indexOfLongestWord = i;
                }
            }
            return validWords.get(indexOfLongestWord);
        }
        return null;
    }

    /**
     * @param row An integer representing the row on the board that the BotPlayer is adding the letter to.
     * @param col An integer representing the column on the board that the BotPlayer is adding the letter to.
     * @param letter A character representing the letter that the BotPlayer is adding to the board.
     * @param direction A Direction representing the orientation of the word that the BotPlayer is adding.
     * @return A ScrabbleMove to represent that the BotPlayer adding a two-letter word to the board.
     * @author Emily Tang 101192604. Edited by Guy Morgenshtern 101151430.
     */
    private ScrabbleMove createScrabbleMoveToAddTwoLetterWord(int row, int col, char letter, ScrabbleModel.Direction direction, Board board) {
        // create an ArrayList of BoardClicks to add to a new ScrabbleMove
        ArrayList<BoardClick> boardClicks = new ArrayList<>();
        boardClicks.add(new BoardClick(new int[] { row, col }, letter + ""));
        // sets tile on board to be the given letter
        board.getTileOnBoard(row, col).setLetter(letter);
        return new ScrabbleMove(boardClicks, direction, this);
    }

    /**
     * @param board The Board that is currently in play.
     * @return A ScrabbleMove that the BotPlayer can make. Returns null if the BotPlayer cannot make a move.
     * @author Emily Tang 101192604
     */
    public ScrabbleMove play(Board board) {
        Square[][] scrabbleBoard = board.getScrabbleBoard();

        System.out.println("I'm at the beginning of my turn! I have " + hand.size() + " letters in my hand!");

        // iterate through the board from left-to-right, top-to-bottom looking for a square with a letter
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                if (scrabbleBoard[row][col].getLetter() != ' ') { // found a square with a letter
                    System.out.println("Found " + scrabbleBoard[row][col].getLetter() + " at " + row + " " + col + "!");
                    // check the status of the surrounding squares and determine the number of surrounding empty squares
                    SquareStatus[] statusOfSurroundingSquares = getStatusOfSurroundingSquares(board, row, col);
                    int numOfSurroundingEmptySquares = getNumSurroundingEmptySquares(statusOfSurroundingSquares);

                    if (numOfSurroundingEmptySquares == 4) { // this means we're at the beginning of the game
                        String boardLetter = addBoardLetterToHand(scrabbleBoard[row][col].getLetter());

                        // iterate through the list of valid words provided by the dictionary
                        ArrayList<String> validWords = new ArrayList<>();
                        for (int i = 0; i < library.size() && validWords.size() < 101; i++) {
                            String s = library.get(i).toUpperCase();
                            if (hasLettersNeededForWord(s) && s.length() > 1 && s.contains(boardLetter)) {
                                validWords.add(s);
                            }
                        }
                        String validWord = findLongestWord(validWords, board.getNumCols());

                        if (validWord != null) {
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
                                removeLetter(validWord.charAt(i) + ""); // remove the letter from the BotPlayer's hand
                                if (i != index) { // do not add the pre-existing letter into ScrabbleMove
                                    boardClicks.add(new BoardClick(new int[]{board.getNumRows() / 2 - index, board.getNumCols() / 2 + i}, validWord.charAt(i) + ""));
                                    board.getTileOnBoard(board.getNumRows() / 2 - index, board.getNumCols() / 2 + i).setLetter(validWord.charAt(i));
                                }
                            }
                            System.out.println("I have " + hand.size() + " letters in my hand!");

                            return new ScrabbleMove(boardClicks, ScrabbleModel.Direction.HORIZONTAL, this);
                        }

                    } else if (numOfSurroundingEmptySquares == 2 || numOfSurroundingEmptySquares == 3) {
                        String boardLetter = addBoardLetterToHand(scrabbleBoard[row][col].getLetter());

                        // determine the number of occurrences of the boardLetter there are in the hand
                        int numOfBoardLetterInHand = 0;
                        for (String letter : hand) {
                            if (letter.equals(boardLetter)) {
                                numOfBoardLetterInHand++;
                            }
                        }

                        // determine if the letter part of a horizontally-placed or vertically-placed word
                        int rowToPlaceLetter;
                        int colToPlaceLetter;
                        ScrabbleModel.Direction direction; // the direction of the word the Bot will place
                        if (statusOfSurroundingSquares[LEFT] == SquareStatus.NOT_EMPTY
                            || statusOfSurroundingSquares[RIGHT] == SquareStatus.NOT_EMPTY) {
                            direction = ScrabbleModel.Direction.VERTICAL;
                            rowToPlaceLetter = row + 1;
                            colToPlaceLetter = col;
                        } else {
                            direction = ScrabbleModel.Direction.HORIZONTAL;
                            rowToPlaceLetter = row;
                            colToPlaceLetter = col + 1;
                        }

                        // determine the length of the longest word the BotPlayer can place
                        int lengthOfLongestWord = 1;
                        if (direction == ScrabbleModel.Direction.HORIZONTAL) {
                            int j = colToPlaceLetter;
                            while (j < board.getNumCols()
                                && canAddLetterHorizontally(getStatusOfSurroundingSquares(board, rowToPlaceLetter, j))) {
                                j++;
                                lengthOfLongestWord++;
                            }
                        } else {
                            int i = rowToPlaceLetter;
                            while (i < board.getNumRows()
                                && canAddLetterVertically(getStatusOfSurroundingSquares(board, i, colToPlaceLetter))) {
                                i++;
                                lengthOfLongestWord++;
                            }
                        }
                        System.out.println("The length of the longest word I can make is " + lengthOfLongestWord + "!");

                        if (rowToPlaceLetter < board.getNumRows() && colToPlaceLetter < board.getNumCols()
                                && scrabbleBoard[rowToPlaceLetter][colToPlaceLetter].getLetter() == ' ' && lengthOfLongestWord > 1) {
                            // find a valid word that begins with the board letter
                            ArrayList<String> validWords = new ArrayList<>();
                            for (int i = 0; i < library.size() && validWords.size() < 101; i++) {
                                String s = library.get(i).toUpperCase(); // letters in BotPlayer's hand are in uppercase
                                int length = s.length(); // determine the length of the selected word from the library
                                if (hasLettersNeededForWord(s) // determine if the BotPlayer has the letters needed to make the selected word
                                        && length > 1 && length <= lengthOfLongestWord
                                        && s.substring(0, 1).equals(boardLetter) // first letter of word must be the letter that's already on the board
                                        && numOccurrencesOfCharInWord(s, boardLetter) <= numOfBoardLetterInHand) {
                                    validWords.add(s);
                                }
                            }

                            String validWord = findLongestWord(validWords, lengthOfLongestWord);

                            if (validWord != null) {
                                System.out.println("Found a valid word! The word is: " + validWord + "!");
                                System.out.println("I have " + hand.size() + " letters in my hand!");
                                removeLetter(validWord.charAt(0) + "");
                                // create an ArrayList of BoardClicks (do not include the letter that's already on the board in this ArrayList)
                                ArrayList<BoardClick> boardClicks = new ArrayList<>();
                                for (int i = 1; i < validWord.length(); i++) {
                                    boardClicks.add(new BoardClick(new int[]{rowToPlaceLetter, colToPlaceLetter}, validWord.charAt(i) + ""));
                                    board.getTileOnBoard(rowToPlaceLetter, colToPlaceLetter).setLetter(validWord.charAt(i));
                                    removeLetter(validWord.charAt(i) + "");
                                    if (direction == ScrabbleModel.Direction.HORIZONTAL) {
                                        colToPlaceLetter++;
                                    } else {
                                        rowToPlaceLetter++;
                                    }
                                }

                                return new ScrabbleMove(boardClicks, direction, this);
                            }
                        }

                        // could not find a valid word to add to the board, remove the board letter from the hand
                        removeLetter(boardLetter);
                    }
                }
            }
        }
        // return an empty ScrabbleMove
        System.out.println("BOT SKIPPED TURN");
        return new ScrabbleMove(new ArrayList<>(), ScrabbleModel.Direction.VERTICAL, this);
    }

}
