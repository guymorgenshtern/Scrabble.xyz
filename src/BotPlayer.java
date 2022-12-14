import com.zetcode.Library;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A BotPlayer in the game of Scrabble.
 * @author Emily Tang 101192604
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
    public enum SquareStatus { EMPTY, NOT_EMPTY, DOES_NOT_EXIST }

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

    /**
     * @param statusOfSurroundingSquares A SquareStatus array representing the status of the surrounding spaces to check.
     * @param dir1 An integer representing the first direction to check.
     * @param dir2 An integer representing the second direction to check.
     * @param dir3 An integer representing the third direction to check.
     * @return True, if a letter can be added to the Square queried by the specified SquareStatus[]. False, if not.
     * @author Emily Tang 101192604
     */
    private boolean canAddLetter(SquareStatus[] statusOfSurroundingSquares, int dir1, int dir2, int dir3) {
        return statusOfSurroundingSquares[dir1] != SquareStatus.NOT_EMPTY
                && statusOfSurroundingSquares[dir2] != SquareStatus.NOT_EMPTY
                && statusOfSurroundingSquares[dir3] != SquareStatus.NOT_EMPTY;
    }

    /**
     * @param statusOfSurroundingSquares A SquareStatus array representing the status of the surrounding spaces to check.
     * @return True, if a letter can be added to the Square queried by the specified SquareStatus array to make a
     * horizontal word. False, it not.
     * @author Emily Tang 101192604
     */
    private boolean canAddLetterHorizontally(SquareStatus[] statusOfSurroundingSquares) {
        return canAddLetter(statusOfSurroundingSquares, TOP, RIGHT, BOTTOM);
    }

    /**
     * @param statusOfSurroundingSquares A SquareStatus array representing the status of the surrounding spaces to check.
     * @return True, if a letter can be added to the Square queried by the specified SquareStatus array to make a
     * vertical word. False, it not.
     * @author Emily Tang 101192604
     */
    private boolean canAddLetterVertically(SquareStatus[] statusOfSurroundingSquares) {
        return canAddLetter(statusOfSurroundingSquares, RIGHT, BOTTOM, LEFT);
    }

    /**
     * @param direction A Direction representing the orientation of the word that the BotPlayer will play.
     * @param board The Board that is currently in play.
     * @param row An integer representing the row on the board to check.
     * @param col An integer representing the column on the board to check.
     * @return An integer representing the max length of the word the BotPlayer can play.
     * @author Emily Tang 101192604
     */
    private int getMaxLengthOfWord(ScrabbleModel.Direction direction, Board board, int row, int col) {
        int maxLengthOfWord = 1; // must account for the pre-existing boardLetter
        if (direction == ScrabbleModel.Direction.HORIZONTAL) {
            int j = col;
            while (j < board.getNumCols() && canAddLetterHorizontally(getStatusOfSurroundingSquares(board, row, j))) {
                j++; // move towards the right
                maxLengthOfWord++;
            }
        } else {
            int i = row;
            while (i < board.getNumRows() && canAddLetterVertically(getStatusOfSurroundingSquares(board, i, col))) {
                i++; // move towards the bottom
                maxLengthOfWord++;
            }
        }
        return maxLengthOfWord;
    }

    /**
     * @param validWords An ArrayList of Strings.
     * @param maxLengthOfWord An integer representing the maximum length of the word that the BotPlayer can play.
     * @return A String representing the longest word in the specified ArrayList of Strings.
     * @author Emily Tang 101192604
     */
    private String getLongestWord(ArrayList<String> validWords, int maxLengthOfWord) {
        if (validWords.size() > 0) {
            int indexOfLongestWord = 0;
            int lengthOfLongestWord = 0;
            for (int i = 0; i < validWords.size(); i++) {
                String currentWord = validWords.get(i);
                int currentLength = currentWord.length();
                if (currentLength == maxLengthOfWord) {
                    return currentWord;
                } else if (currentLength > lengthOfLongestWord) {
                    // save the length of the current word and its index
                    lengthOfLongestWord = currentLength;
                    indexOfLongestWord = i;
                }
            }
            return validWords.get(indexOfLongestWord);
        }
        return null;
    }

    /**
     * @param validWord A String representing a valid word that the BotPlayer would like to play.
     * @param boardLetter A character to determine the index of in the specified String.
     * @return An integer representing the index of the specified character in the specified String.
     * @author Emily Tang 101192604
     */
    private int getIndexOfBoardLetter(String validWord, char boardLetter) {
        int index = -1;
        for (int i = 0; i < validWord.length(); i++) {
            if (validWord.charAt(i) == boardLetter) {
                index = i;
            }
        }
        return index;
    }

    /**
     * @param boardLetter A String representing the pre-existing letter on the Board that the BotPlayer will add onto.
     * @return An ArrayList of Strings representing valid words that the BotPlayer could play (if the BotPlayer is
     * playing the first move of the game).
     * @author Emily Tang 101192604
     */
    private ArrayList<String> findValidWordsForFirstMove(String boardLetter) {
        ArrayList<String> validWords = new ArrayList<>();
        for (int i = 0; i < library.size() && validWords.size() < 101; i++) {
            String s = library.get(i).toUpperCase();
            if (hasLettersNeededForWord(s) && s.length() > 1 && s.contains(boardLetter)) {
                validWords.add(s);
            }
        }
        return validWords;
    }

    /**
     * @param board The Board that is currently in play.
     * @param validWord A String representing the valid word that the BotPlayer would like to play.
     * @param index An index representing where the pre-existing board letter is in the specified valid word.
     * @param row An integer representing the row where the pre-existing board letter is on the board.
     * @param col An integer representing the column where the pre-existing board letter is on the board.
     * @return A ScrabbleMove representing the BotPlayer's turn (if the BotPlayer is playing the first move of the game).
     * @author Emily Tang 101192604
     */
    private ScrabbleMove getScrabbleMoveForFirstMove(Board board, String validWord, int index, int row, int col) {
        // determine the BoardClicks that make up the ScrabbleMove
        ArrayList<BoardClick> boardClicks = new ArrayList<>();
        for (int i = 0; i < validWord.length(); i++) {
            if (i != index) { // do not add the pre-existing letter into ScrabbleMove
                int walkingColumn = col - index + i;
                boardClicks.add(new BoardClick(new int[] { row, walkingColumn }, validWord.charAt(i) + ""));
                board.getTileOnBoard(row, walkingColumn).setLetter(validWord.charAt(i));
            }
        }
        return new ScrabbleMove(boardClicks, ScrabbleModel.Direction.HORIZONTAL, this);
    }

    /**
     * @param boardLetter A String representing the pre-existing letter on the Board that the BotPlayer will add onto.
     * @param maxLengthOfWord An integer representing the longest word that the BotPlayer could play.
     * @return An ArrayList of Strings representing valid words that the BotPlayer could play.
     * @author Emily Tang 101192604
     */
    private ArrayList<String> findValidWords(String boardLetter, int maxLengthOfWord) {
        ArrayList<String> validWords = new ArrayList<>();
        for (int i = 0; i < library.size() && validWords.size() < 101; i++) {
            String s = library.get(i).toUpperCase(); // letters in BotPlayer's hand are in uppercase
            int length = s.length(); // determine the length of the selected word from the library
            if (hasLettersNeededForWord(s) // determine if the BotPlayer has the letters needed to make the selected word
                    && length > 1 && length <= maxLengthOfWord
                    && s.substring(0, 1).equals(boardLetter)) { // first letter of word must be the letter that's already on the board
                validWords.add(s);
            }
        }
        return validWords;
    }

    /**
     * @param board The Board that is currently in play.
     * @param validWord A String representing the valid word that the BotPlayer would like to play.
     * @param direction A Direction representing the orientation of the word that the BotPlayer would like to play.
     * @param row An integer representing the starting row of the word that the BotPlayer would like to play.
     * @param col An integer representing the starting column of the word that the BotPlayer would like to play.
     * @return A ScrabbleMove representing the BotPlayer's turn.
     * @author Emily Tang 101192604
     */
    private ScrabbleMove getScrabbleMove(Board board, String validWord, ScrabbleModel.Direction direction, int row, int col) {
        // determine the BoardClicks that make up the ScrabbleMove
        ArrayList<BoardClick> boardClicks = new ArrayList<>();
        for (int i = 1; i < validWord.length(); i++) { // do not include the letter that's already on the board
            System.out.println("Adding letter " + validWord.charAt(i) + " to BoardClick at " + row + " " + col + ".");
            boardClicks.add(new BoardClick(new int[] { row, col }, validWord.charAt(i) + ""));
            board.getTileOnBoard(row, col).setLetter(validWord.charAt(i));
            if (direction == ScrabbleModel.Direction.HORIZONTAL) {
                col++; // move towards the right
            } else {
                row++; // move towards the bottom
            }
        }
        return new ScrabbleMove(boardClicks, direction, this);
    }

    /**
     * @param board The Board that is currently in play.
     * @return A ScrabbleMove that the BotPlayer can make. Returns null if the BotPlayer cannot make a move.
     * @author Emily Tang 101192604
     */
    public ScrabbleMove play(Board board) {
        Square[][] scrabbleBoard = board.getScrabbleBoard();

        System.out.println("BotPlayer is at the beginning of their turn! They have " + hand.size() + " letters in their hand!");

        // letters in BotPlayer's hand
        String letters = getAvailableLetters().size() + " Letters in " + getName() + "'s hand: ";
        for (String s : getAvailableLetters()) {
            letters += s + " ";
        }
        System.out.println(letters);

        // iterate through the board from left-to-right, top-to-bottom looking for a square with a letter
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                if (scrabbleBoard[row][col].getLetter() != ' ') { // found a square with a letter
                    System.out.println("BotPlayer found the letter '" + scrabbleBoard[row][col].getLetter() + "' at square " + row + " " + col + ".");

                    // check the status of the surrounding squares and determine the number of surrounding empty squares
                    SquareStatus[] statusOfSurroundingSquares = getStatusOfSurroundingSquares(board, row, col);
                    int numOfSurroundingEmptySquares = getNumSurroundingEmptySquares(statusOfSurroundingSquares);

                    if (numOfSurroundingEmptySquares == 4) { // this means we're playing the first turn
                        String boardLetter = addBoardLetterToHand(scrabbleBoard[row][col].getLetter());

                        // find 100 valid words that the BotPlayer could play
                        ArrayList<String> validWords = findValidWordsForFirstMove(boardLetter);

                        // determine the longest possible word the BotPlayer can play out of the 100 valid words
                        String validWord = getLongestWord(validWords, board.getNumCols() / 2);

                        if (validWord != null) {
                            System.out.println("BotPlayer found a valid word! The word is: " + validWord + "!");
                            // determine where the letter from the board is in the valid word
                            int index = getIndexOfBoardLetter(validWord, boardLetter.charAt(0));
                            // create an ArrayList of BoardClicks, BotPlayer will add this word horizontally
                            // return new ScrabbleMove(getScrabbleMoveForFirstMove(board, validWord, index, row, col), ScrabbleModel.Direction.HORIZONTAL, this);
                            return getScrabbleMoveForFirstMove(board, validWord, index, row, col);
                        }

                    } else if (numOfSurroundingEmptySquares == 2 || numOfSurroundingEmptySquares == 3) {
                        String boardLetter = addBoardLetterToHand(scrabbleBoard[row][col].getLetter());

                        // determine if the letter part of a horizontally-placed or vertically-placed word
                        boolean isBoardWordHorizontal = statusOfSurroundingSquares[LEFT] == SquareStatus.NOT_EMPTY
                                || statusOfSurroundingSquares[RIGHT] == SquareStatus.NOT_EMPTY;
                        ScrabbleModel.Direction direction = isBoardWordHorizontal ? ScrabbleModel.Direction.VERTICAL : ScrabbleModel.Direction.HORIZONTAL;
                        int rowToPlaceLetter = isBoardWordHorizontal ? row + 1 : row;
                        int colToPlaceLetter = isBoardWordHorizontal ? col : col + 1;

                        // determine if there is a word in front of the boardLetter
                        boolean wordInFrontOfBoardLetter = (direction == ScrabbleModel.Direction.HORIZONTAL && statusOfSurroundingSquares[LEFT] == SquareStatus.NOT_EMPTY)
                                || (direction == ScrabbleModel.Direction.VERTICAL && statusOfSurroundingSquares[TOP] == SquareStatus.NOT_EMPTY);

                        // determine the length of the longest word the BotPlayer can place
                        int maxLengthOfWord = getMaxLengthOfWord(direction, board, rowToPlaceLetter, colToPlaceLetter);

                        if (rowToPlaceLetter < board.getNumRows() && colToPlaceLetter < board.getNumCols() // do not add a word past the right and bottom edges of the board
                                && scrabbleBoard[rowToPlaceLetter][colToPlaceLetter].getLetter() == ' '
                                && maxLengthOfWord > 1
                                && !wordInFrontOfBoardLetter) { // do not add onto a pre-existing word

                            // find 100 valid words that begin with the pre-existing letter on the board
                            ArrayList<String> validWords = findValidWords(boardLetter, maxLengthOfWord);

                            // determine the longest possible word the BotPlayer can play out of the 100 valid words
                            String validWord = getLongestWord(validWords, maxLengthOfWord);

                            if (validWord != null) {
                                System.out.println("BotPlayer found a valid word! The word is: " + validWord + "!");
                                // create an ArrayList of BoardClicks
                                // return new ScrabbleMove(getScrabbleMove(board, validWord, direction, rowToPlaceLetter, colToPlaceLetter), direction, this);
                                return getScrabbleMove(board, validWord, direction, rowToPlaceLetter, colToPlaceLetter);
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
