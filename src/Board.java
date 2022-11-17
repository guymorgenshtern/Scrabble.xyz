/*
    -- Notes -- Francisco De Grano

        - Decide on Data Structure to use -> Hashmap + Hashset? https://www.w3schools.com/java/java_hashset.asp
        - Board is 15 x 15


        MULTIPLIERS
        - 2 Word Multipliers Separate from Board.java -> doubleWord, tripleWord
        - 2 Letter Multipliers Separate from Board.java -> doubleLetter, tripleLetter

        - BLANK TILES           -> M3
        - PREMIUM SQUARES       -> M3

 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A Board in the game of Scrabble.
 * @author Francisco DeGrano 101147447
 * @author Guy Morgenshtern 101151430
 */
public class Board {

    public static int SIZE = 15;

    /** A 2D array of squares to represent the board. */
    private Square [][] scrabbleBoard;

    private static HashMap<String, Multiplier> boardScore;

    /**
     * Creates a Board using the specified file.
     * @param fileName A String representing the name of the file that contains the orientation of the Scrabble board.
     * @throws IOException If an I/O error occurs.
     */
    public Board(String fileName) throws IOException {
        this.scrabbleBoard = new Square[15][15];
        Board.boardScore = new HashMap<>();
        this.initBoard(fileName);
    }

    /**
     * Initializes the 15x15 Board with only regular squares (no premium squares).
     */
    private void initBoard() {
        for (int i = 14; i >= 0; i--) {
            for (int j = 0; j < 15; j++) {
                this.scrabbleBoard[i][j] = new Square();
            }
        }
    }

    /**
     * Initializes the 15x15 Board using the specified file.
     * @param fileName A String representing the name of the file that contains the orientation of the Scrabble board.
     * @throws IOException If an I/O error occurs.
     */
    public void initBoard (String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        try {
            String line = br.readLine();
            int row = -1;
            int column;

            while (line != null) {
                column = -1;
                row++;
                for (char c : line.toCharArray()) {
                    column++;
                    this.scrabbleBoard[row][column] = new Square();
                    scrabbleBoard[row][column].setLetter(c);
                    if (c != '.') {
                        this.addBoardScore(c, row, column);
                    }
                }
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    /**
     * Initializes a Premium Square on the Board using a specified ASCII character, a row, and a column.
     * @param type A char representing a Premium Square.
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     */
    private void addBoardScore(char type, int row, int column){
        /*
        In this case not sure if multiplier classes (Word + Letter) should be called/used here in the Map with a
        put ie:

                Land on triple word -> Mapping is; board.Score.put("00", "3W")

         - Initialize Squares that are effected by multipliers in this class
         */

        /*
        Add word and letter multipliers here
         */
        switch (type) {
            case '+':
                this.scrabbleBoard[row][column] = new Square(new Multiplier(Multiplier.Type.WORD, 2));
            case '~':
                this.scrabbleBoard[row][column] = new Square(new Multiplier(Multiplier.Type.WORD, 3));
            case '*':
                this.scrabbleBoard[row][column] = new Square(new Multiplier(Multiplier.Type.LETTER, 2));
            case '-':
                this.scrabbleBoard[row][column] = new Square(new Multiplier(Multiplier.Type.LETTER, 3));
        }
        this.scrabbleBoard[row][column].setLetter(type);
    }

    /**
     * NOTE: This may not be needed as we can just keep score in the game class later on post implementation of Board.
     * @param ref A String representing a reference.
     * @return A Multiplier representing an Object key.
     */
    public static Multiplier getTileBoardScore(String ref) {
        return Board.boardScore.getOrDefault(ref, null); // ref Object Key
    }

    /**
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     * @return The Square at the specified row and column.
     */
    public Square getTileOnBoard(int row, int column){
        return this.scrabbleBoard[row][column];
    }

    /**
     * @param letter A Letter to place on the Square.
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     */
    public void setSquare(char letter, int row, int column) {
        this.scrabbleBoard[row][column].setLetter(letter);
    }

    /**
     * @param row An integer representing a row on the Board.
     * @param column An integer representing a column on the Board.
     * @return True, if the Square has a Letter on it. False, if not.
     */
    public boolean isSquareFilled(int row, int column) {
        Square s = this.scrabbleBoard[row][column];
        System.out.println(s.getLetter());
        return !(s.getLetter() == '.' || s.getLetter() == '+' || s.getLetter() == '*' || s.getLetter() == '~'
                || s.getLetter() == '-');
    }

    /**
     * Prints a text-based representation of the Board.›
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

    public Square[][] getScrabbleBoard() {
        return scrabbleBoard;
    }

    //    public ArrayList<String> surroundingWords(ScrabbleMove move) {
//        ArrayList<String> surroundingWords = new ArrayList<>();
//        String foundWord = move.getWord();
//        int count = 1;
//        if (move.getDirection() == Game.Direction.HORIZONTAL) {
//            while ((move.getColumn() - count >= 0) && isSquareFilled(move.getRow(), move.getColumn() - count)) {
//                foundWord = (this.scrabbleBoard[move.getRow()][move.getColumn() - count].getLetter())
//                        + foundWord;
//                System.out.println(this.scrabbleBoard[move.getRow()][move.getColumn() - count]);
//                count++;
//            }
//            count = 1;
//            while (isSquareFilled(move.getRow(), move.getColumn() + move.getWord().length() + count)) {
//                foundWord = foundWord + (this.scrabbleBoard[move.getRow()][move.getColumn() - count].getLetter());
//                count++;
//
//            }
//            surroundingWords.add(foundWord);
//        }
//    }

}
