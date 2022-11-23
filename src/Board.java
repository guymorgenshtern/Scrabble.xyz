import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A Board in the game of Scrabble.
 * @author Francisco DeGrano 101147447. Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
 */
public class Board {

    /** An integer representing the size of the board. */
    public static int SIZE = 15;

    /** A 2D array of squares to represent the board. */
    private final Square[][] scrabbleBoard;

    /**
     * Creates a 15x15 Board with only regular squares.
     * @author Emily Tang 101192604
     */
    public Board() {
        scrabbleBoard = new Square[Board.SIZE][Board.SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                this.scrabbleBoard[row][col] = new Square();
            }
        }
    }

    /**
     * Creates a Board using the specified file.
     * @param fileName A String representing the name of the file that contains the layout of a 15x15 Scrabble board.
     * @throws IOException If an I/O error occurs.
     * @author Guy Morgenshtern - 101151430
     */
    public Board(String fileName) throws IOException {
        scrabbleBoard = new Square[SIZE][SIZE];
        initBoard(fileName);
    }

    /**
     * Initializes the 15x15 Board using the specified file.
     * @param fileName A String representing the name of the file that contains the orientation of the Scrabble board.
     * @throws IOException If an I/O error occurs.
     * @author Edited by Emily Tang 101192604.
     */
    private void initBoard(String fileName) throws IOException {
        // read in the board layout
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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
                    if (c != '.') {
                        // create a Square with a Multiplier
                        scrabbleBoard[row][column] = new Square(initMultiplier(c));
                    } else {
                        // create a Square with a blank letter
                        scrabbleBoard[row][column] = new Square();
                    }

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
     * @author Guy Morgenshtern - 101151430
     */
    public Square[][] getScrabbleBoard() {
        return scrabbleBoard;
    }

    /**
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     * @return The Square at the specified row and column.
     * @author Guy Morgenshtern - 101151430
     */
    public Square getTileOnBoard(int row, int column){
        return this.scrabbleBoard[row][column];
    }

    /**
     * @param letter A Letter to place on the Square.
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     * @author Guy Morgenshtern - 101151430
     */
    public void setSquare(char letter, int row, int column) {
        this.scrabbleBoard[row][column].setLetter(letter);
    }

    /**
     * Prints a text-based representation of the Board. For debug purposes
     * @author Guy Morgenshtern - 101151430
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
