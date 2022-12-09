import java.io.*;
import java.util.ArrayList;

/**
 * A Board in the game of Scrabble.
 * @author Francisco DeGrano 101147447. Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
 */
public class Board implements Serializable {

    /** An integer representing the default size of the board. */
    private static final int DEFAULT_SIZE = 15;

    /** An integer representing the number of rows the board has. */
    private final int numRows;

    /** An integer representing the number of columns the board has. */
    private final int numCols;

    /** A 2D array of squares to represent the board. */
    private final Square[][] scrabbleBoard;

    /**
     * Creates a 15x15 Board with only regular squares.
     * @author Emily Tang 101192604
     */
    public Board() {
        numRows = DEFAULT_SIZE;
        numCols = DEFAULT_SIZE;
        scrabbleBoard = new Square[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                scrabbleBoard[row][col] = new Square();
            }
        }
    }

    /**
     * Creates a 15x15 Board using the specified file that contains ASCII characters.
     * @param fileName A String representing the name of the file that contains the layout of a 15x15 Scrabble board.
     * @throws IOException If an I/O error occurs.
     * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
     */
    public Board(String fileName) throws IOException {
        numRows = DEFAULT_SIZE;
        numCols = DEFAULT_SIZE;
        scrabbleBoard = new Square[numRows][numCols];
        initBoard(fileName);
    }

    /**
     * Creates a custom board using the specified 2D String array.
     * @param customBoard A 2D String array representing a custom board.
     * @param numRows An integer representing the number of rows the custom board has.
     * @param numCols An integer representing the number of columns the custom board has.
     * @author Emily Tang 101192604
     */
    public Board(String[][] customBoard, int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;

        scrabbleBoard = new Square[this.numRows][this.numCols];
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                // determine if there's a multiplier on the square or not
                if (customBoard[i][j].equals("")) {
                    scrabbleBoard[i][j] = new Square();
                } else {
                    // determine the type of multiplier on the square
                    Multiplier.Type type = customBoard[i][j].charAt(1) == 'L' ? Multiplier.Type.LETTER : Multiplier.Type.WORD;
                    scrabbleBoard[i][j] = new Square(new Multiplier(type, Integer.parseInt(customBoard[i][j].charAt(0) + "")));
                }
            }
        }
    }

    /**
     * Initializes the 15x15 Board using the specified file that contains ASCII characters.
     * @param fileName A String representing the name of the file that contains the orientation of the Scrabble board.
     * @throws IOException If an I/O error occurs.
     * @author Edited by Emily Tang 101192604.
     */
    private void initBoard(String fileName) throws IOException {
        // read in the board layout
        try (InputStream inputStream = getClass().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = br.readLine(); // get the first row of the board layout
            int row = -1;
            int column;

            // read until the end of the file is found
            while (line != null) {
                column = -1;
                row++;
                // iterate through the row
                for (char c : line.toCharArray()) {
                    column++;
                    scrabbleBoard[row][column] = c != '.' ? new Square(initMultiplier(c)) : new Square();
                }
                line = br.readLine();
            }
        }
    }

    /**
     * @param type An ASCII character representing the type of Multiplier to create.
     * @return A new Multiplier based on the specified ASCII character.
     * @author Emily Tang 101192604
     */
    private Multiplier initMultiplier(char type) {
        return switch (type) {
            case '+' -> new Multiplier(Multiplier.Type.WORD, 2);
            case '~' -> new Multiplier(Multiplier.Type.WORD, 3);
            case '*' -> new Multiplier(Multiplier.Type.LETTER, 2);
            case '-' -> new Multiplier(Multiplier.Type.LETTER, 3);
            default -> null;
        };
    }

    /**
     * @return A 2D array of squares representing the Board.
     * @author Guy Morgenshtern 101151430
     */
    public Square[][] getScrabbleBoard() {
        return scrabbleBoard;
    }

    /**
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     * @return The Square at the specified row and column.
     * @author Guy Morgenshtern 101151430
     */
    public Square getTileOnBoard(int row, int column){
        return this.scrabbleBoard[row][column];
    }

    /**
     * @param letter A Letter to place on the Square.
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     * @author Guy Morgenshtern 101151430
     */
    public void setSquare(char letter, int row, int column) {
        this.scrabbleBoard[row][column].setLetter(letter);
    }

    /**
     * @return An integer representing the number of rows the board has.
     * @author Emily Tang 101192604
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * @return An integer representing the number of columns the board has.
     * @author Emily Tang 101192604
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Prints a text-based representation of the Board for debugging purposes.
     * @author Guy Morgenshtern 101151430
     */
    public void printBoard() {
        System.out.printf("%7d", 0);
        for (int i = 1; i < scrabbleBoard.length; i++) {
            System.out.printf("%5d", i);
        }
        System.out.println(" ");
        for (int k = 0; k < scrabbleBoard.length; k++) {
            System.out.printf("%2d", k);
            for (int j = 0; j < scrabbleBoard[0].length; j++) {
                System.out.printf("%5s", scrabbleBoard[k][j].getLetter());
            }
            System.out.println(" ");
        }
    }
}
