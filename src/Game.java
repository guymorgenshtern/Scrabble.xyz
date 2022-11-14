import com.zetcode.Library;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * MAKE THIS CLASS ACTUALLY WORK INSTEAD OF BEING STATIC
 */

/**
 * A text-based playable version of Scrabble.
 * @author Guy Morgenshtern 101151430
 */
public class Game {

    /** A Scrabble board. */
    private Board board;

    /** An ArrayList of players. */
    private ArrayList<Player> playerList;

    /** A LetterBag containing the letter tiles. */
    private LetterBag letterBag;

    /** A Library for word validation checking. */
    private Library lib;

    private HashMap<String, Integer> scorePerLetter;

    private ArrayList<ScrabbleView> views;
    /**
     * A word can either be horizontally, or vertically placed onto the board.
     */
    public enum Direction { HORIZONTAL, VERTICAL }
    public enum Status { DONE, NOT_DONE }

    /**
     * Runs a text-based playable version of Scrabble.
     * @throws IOException If an I/O error occurs.
     */
    public Game() throws IOException {
        this.board = new Board("res/default_board.txt");
        this.lib = new Library();
        this.scorePerLetter = new HashMap<>();
        this.views = new ArrayList<>();

        playerList = new ArrayList<>();
        // TODO: initialize InitController here instead??
        // initializePlayers();

        this.letterBag = new LetterBag();

        initializeLetterBag("res/letters_by_quantity");
        System.out.println(letterBag.bagSize());
    }

    public ArrayList<ScrabbleView> getViews() {
        return this.views;
    }

    /**
     * Initializes players with their specified names.
     * NOTE: Error checking already in InitController. Users can only enter between two and four players there.
     * @param namesOfPlayers An array of Strings representing the names of the players.
     * @author Emily Tang 101192604
     */
    public void initializePlayers(String[] namesOfPlayers) {
        for (String name : namesOfPlayers) {
            System.out.println(name);
            playerList.add(new Player(name));
        }
    }

    /**
     * @return An ArrayList of Players
     * @author Emily Tang 101192604
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Prints a legend of the ASCII symbols used to represent the text-based board.
     */
    private void printLegend() {
        System.out.println("+: Triple Word, ~: Double Word, -: Triple Letter, *: Double Letter");
    }

    private int getLetterScore(String l) {
        return this.scorePerLetter.get(l);
    }

    /**
     * Initializes the LetterBag using the specified file.
     * @param fileName A String representing the name of the file that contains the specific quantity of letters.
     * @throws IOException If an I/O error occurs.
     */
    public void initializeLetterBag(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line = br.readLine();

        while (line != null) {
            letterBag.addLetter(line.substring(0,1), Integer.parseInt(line.substring(2,line.lastIndexOf("-"))));
            this.scorePerLetter.put(line.substring(0,1), Integer.parseInt(line.substring(line.lastIndexOf("-"))));
            line = br.readLine();
        }
    }

    /**
     * Deals seven letters to each player.
     */
    public void dealLetters() {
        for (Player p : playerList) {
            for (int i = 0; i < 7; i++) {
                p.addLetter(letterBag.getRandomLetter());
            }
        }
    }

    public void initializeBoard() {
        String firstLetter = letterBag.getRandomLetter();
        int centerSquare = (board.getSize() - 1) / 2;
        board.setSquare(firstLetter.toCharArray()[0], centerSquare, centerSquare);
        int coords[][] = new int[1][2];
        coords[0][0] = (board.getSize() - 1) / 2;
        coords[0][1] = (board.getSize() - 1) / 2;
        for (ScrabbleView v : this.getViews()) {
            v.update(new ScrabbleEvent(this, coords, playerList.get(0), board, Status.NOT_DONE));
        }

    }

    private String findFullWord(ScrabbleMove scrabbleMove) {
        //this assumes the move array is sorted from first letter->last, we should make sure this is always the case
        int x = scrabbleMove.getCoords()[0][0];
        int y = scrabbleMove.getCoords()[0][1];
        String word = "";

        if(scrabbleMove.getDirection() == Direction.HORIZONTAL) {
            int walkingPointer = x;
            while (this.board.getTileOnBoard(walkingPointer,y).getLetter() != ' ') {
                word += String.valueOf(this.board.getTileOnBoard(walkingPointer,y).getLetter());
                walkingPointer++;
            }
            walkingPointer = x - 1;
            while (this.board.getTileOnBoard(walkingPointer,y).getLetter() != ' ') {
                word = this.board.getTileOnBoard(walkingPointer,y).getLetter() + word;
                walkingPointer++;
            }
        } else if(scrabbleMove.getDirection() == Direction.VERTICAL) {
            int walkingPointer = y;
            while (this.board.getTileOnBoard(x,walkingPointer).getLetter() != ' ') {
                word += String.valueOf(this.board.getTileOnBoard(x,walkingPointer).getLetter());
                walkingPointer++;
            }
            walkingPointer = y - 1;
            while (this.board.getTileOnBoard(x,walkingPointer).getLetter() != ' ') {
                word = this.board.getTileOnBoard(x,walkingPointer).getLetter() + word;
                walkingPointer++;
            }
        }
        return word;
    }

    private ArrayList<String> findSurroundingWords(ScrabbleMove move) {
        Direction newDirection = move.getDirection() == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
        ArrayList<String> surroundingWordList = new ArrayList<>();
        for (int i = 0; i < move.getCoords().length; i++) {
            int arr[][] = new int[1][2];
            arr[0] = move.getCoords()[i];
            ScrabbleMove newMove = new ScrabbleMove(arr, newDirection, move.getPlayer());
            String found = findFullWord(newMove);
            if (found.length() > 1) {
                surroundingWordList.add(found);
            }
        }

        return surroundingWordList;
    }

    private boolean checkMoveValidity(ScrabbleMove move) {
        boolean adjacentToSquare = false;
        boolean isWord = false;
        boolean surroundingWordsAreWords = true;

        adjacentToSquare = (move.getWord().length() > move.getCoords().length);
        isWord = lib.isValidWord(move.getWord());
        for (String word : findSurroundingWords(move)) {
            if (!lib.isValidWord(word)) {
                surroundingWordsAreWords = false;
            }
        }
        return adjacentToSquare && isWord && surroundingWordsAreWords;
    }

    private void deleteInvalidWordFromBoard(ScrabbleMove move){
        for (int i = 0; i < move.getCoords().length; i++) {
            this.board.getTileOnBoard(move.getCoords()[i][0], move.getCoords()[i][1]).setLetter(' ');
            move.getPlayer().addLetter(String.valueOf( this.board.getTileOnBoard(move.getCoords()[i][0], move.getCoords()[i][1])));
        }
    }

//    private int calculateSurroundingWordsScore(int coords[][]) {
//        for (this.findSurroundingWords())
//    }

    private int calculateMoveScore(ScrabbleMove move) {
        int total = 0;
        ArrayList<Multiplier> wordMultipliers = new ArrayList<>();
        for (int i = 0; i < move.getCoords().length; i++) {
            Square tile = this.board.getTileOnBoard(move.getCoords()[i][0], move.getCoords()[i][1]);
            String letter = String.valueOf(tile.getLetter());
            int value = this.getLetterScore(letter);

            if (tile.isPremiumSquare()) {
                if (tile.getMultiplier().getType() == Multiplier.Type.LETTER) { //will be fixed with merge with emily
                    value = tile.getMultiplier().calculateScore(value);
                } else {
                    wordMultipliers.add(tile.getMultiplier());
                }
            }
            total += value;

            for (String s : this.findSurroundingWords(move)) {
                total += this.scorePerLetter.get(s);
            }

        }
        for (Multiplier m : wordMultipliers) {
            total = m.calculateScore(total);
        }

        return total;
    }

    public void play(int move[][]) {
        int playerTurnCounter = 0;
        Player currentPlayer = playerList.get(playerTurnCounter % playerList.size());
        int xDifferential = move[0][0];
        int yDifferential = move[0][1];
        Direction dir = null;
        for (int i = 1; i < move.length; i++) {
            xDifferential -= move[i][0];
            yDifferential -= move[i][1];
        }

        if ((xDifferential == 0) && (yDifferential == 0)) {
            System.out.println("decide what to do here");
        } else if (xDifferential == 0) {
            dir = Direction.VERTICAL;
        } else if (yDifferential == 0) {
            dir = Direction.HORIZONTAL;
        } else {
            System.out.println("this is an issue");
        }
        ScrabbleMove scrabbleMove = new ScrabbleMove(move, dir, currentPlayer);
        scrabbleMove.setWord(findFullWord(scrabbleMove));
        boolean validMove = checkMoveValidity(scrabbleMove);

        if (validMove) {
            //calculate score
            currentPlayer.setScore(currentPlayer.getScore() + calculateMoveScore(scrabbleMove));
            playerTurnCounter++;

            //UPDATE VIEWS
        } else {
            deleteInvalidWordFromBoard(scrabbleMove);
        }
    }

    public static void main (String args[]) throws IOException {
        Game scrabble = new Game();

        scrabble.getViews().add(new BoardFrame(scrabble));
        scrabble.getViews().add(new HandFrame(scrabble));
        InitController initFrame = new InitController(scrabble);



    }
}
