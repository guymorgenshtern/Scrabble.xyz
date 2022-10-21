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

import java.util.HashMap;
import java.util.HashSet; // Use Hashset for file containing words Dictionary

public class Board {

    private char [][] scrabbleBoard;
    private static HashMap<String, String> boardScore;

    /**
     * Board Initialize (Init) Method
     *
     */
    private void initBoard(){
        this.scrabbleBoard = new char[15][15];
        for (int i = 14; i >= 0; i--){
            for (int j=0; j<15; j++){
                this.scrabbleBoard[i][j] = ' ';

            }
        }
    }

}
