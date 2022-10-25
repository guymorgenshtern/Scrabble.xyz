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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet; // Use Hashset for file containing words Dictionary

public class Board {

    private Square [][] scrabbleBoard;
    private static HashMap<String, Multiplier> boardScore;

    /**
     * Board Initialize (Init) Method
     *
     */

    public Board(String fileName) throws IOException {
        this.scrabbleBoard = new Square[15][15];
        Board.boardScore = new HashMap<>();
        this.initBoard(fileName);
    }
    private void initBoard() {
        this.scrabbleBoard = new Square[15][15];
        for (int i = 14; i >= 0; i--) {
            for (int j = 0; j < 15; j++) {
                this.scrabbleBoard[i][j] = new Square();

            }
        }
    }
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

    //Francisco Degrano
    //Guy Morgenshtern
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
                this.scrabbleBoard[row][column] = new Square(new WordMultiplier(2));
            case '~':
                this.scrabbleBoard[row][column] = new Square(new WordMultiplier(3));
            case '*':
                this.scrabbleBoard[row][column] = new Square(new LetterMultiplier(2));
            case '-':
                this.scrabbleBoard[row][column] = new Square(new LetterMultiplier(3));
        }
        this.scrabbleBoard[row][column].setLetter(type);
    }

    // This may not be needed as we can just keep score in the game class later on post implementation of Board
    public static Multiplier getTileBoardScore(String ref){
        return Board.boardScore.getOrDefault(ref, null); // ref Object Key
    }

    /**
     *
     * @param row
     * @param column
     * @return word user placed their word on "word place on (x, y)"
     */
    public Square getTileOnBoard(int row, int column){
        return this.scrabbleBoard[row][column];

    }

    public boolean setTile(char letter, int row, int column) {
        this.scrabbleBoard[row][column].setLetter(letter);
        return true;
    }

    public boolean isSquareFilled(int row, int column) {
        Square s = this.scrabbleBoard[row][column];

        return !(s.getLetter() == '.' || s.getLetter() == '+' || s.getLetter() == '*' || s.getLetter() == '~'
                || s.getLetter() == '-');
    }

    public boolean checkMoveValidity(String word, int row, int column, Game.Direction direction) {
        if (direction == Game.Direction.HORIZONTAL) {
            for (int i = column; i < word.length(); i++) {
                if(isSquareFilled(row,i)) {
                    return false;
                }
            }
        } else {
            for (int i = row; i < word.length(); i++) {
                if(isSquareFilled(i,column)) {
                    return false;
                }
            }
        }
        //validity checks for surrounding letter combinations creating valid words will eventually be added
        return true;
    }

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
