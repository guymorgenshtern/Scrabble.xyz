import com.zetcode.Library;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A text-based playable version of Scrabble.
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleModel {

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

    private String selectedLetter;
    private ScrabbleMove currentMove;

    /**
     * Runs a text-based playable version of Scrabble.
     * @throws IOException If an I/O error occurs.
     */
    public ScrabbleModel() throws IOException {
        this.board = new Board("res/default_board.txt");
        this.lib = new Library();
        this.scorePerLetter = new HashMap<>();
        this.views = new ArrayList<>();
        this.selectedLetter = "";
        playerList = new ArrayList<>();
        // TODO: initialize InitController here instead??
        // initializePlayers();
        this.currentMove = new ScrabbleMove();
        this.letterBag = new LetterBag();

        initializeLetterBag("res/letters_by_quantity");
        System.out.println("Letter Bag Size = " + letterBag.bagSize());
    }

    public ScrabbleMove getCurrentMove() {
        return currentMove;
    }

    public String getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(String selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public ArrayList<ScrabbleView> getViews() {
        return this.views;
    }

    /**
     * Initializes players with their specified names. Deal letters to players. Initialize the game board.
     * NOTE: Error checking already in InitController. Users can only enter between two and four players there.
     * @param namesOfPlayers An array of Strings representing the names of the players.
     * @author Emily Tang 101192604
     */
    public void initializeGame(String[] namesOfPlayers) {
        for (String name : namesOfPlayers) {
            System.out.println(name);
            playerList.add(new Player(name));
        }
        dealLetters();
        initializeBoard();
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
            this.scorePerLetter.put(line.substring(0,1), Integer.parseInt(line.substring(line.lastIndexOf("-") + 1)));
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
        int centerSquare = (Board.SIZE - 1) / 2;
        board.setSquare(firstLetter.toCharArray()[0], centerSquare, centerSquare);
        ArrayList<BoardClick> coordsList = new ArrayList<>();
        int coords[] = new int[2];
        coords[0] = (Board.SIZE - 1) / 2;
        coords[1] = (Board.SIZE - 1) / 2;
        BoardClick b = new BoardClick(coords, firstLetter);
        coordsList.add(b);
        ScrabbleMove m = new ScrabbleMove();
        m.setCoords(coordsList);
        for (ScrabbleView v : this.getViews()) {
            v.update(new ScrabbleEvent(this, m, playerList.get(0), board, Status.NOT_DONE));
        }

    }

    private String findFullWord(ScrabbleMove scrabbleMove) {
        //this assumes the move array is sorted from first letter->last, we should make sure this is always the case
        System.out.println(scrabbleMove.getCoords());
        int x = scrabbleMove.getCoords().get(0).getCoords()[0];
        int y = scrabbleMove.getCoords().get(0).getCoords()[1];
        String word = "";
        if(scrabbleMove.getDirection() == Direction.HORIZONTAL) {
            int walkingPointer = x;
            while (walkingPointer < Board.SIZE && Character.isAlphabetic(this.board.getTileOnBoard(walkingPointer,y).getLetter())) {
                word += String.valueOf(this.board.getTileOnBoard(walkingPointer,y).getLetter());
                walkingPointer++;
            }
            walkingPointer = x - 1;
            while (walkingPointer >= 0 && Character.isAlphabetic(this.board.getTileOnBoard(walkingPointer,y).getLetter())) {
                word = this.board.getTileOnBoard(walkingPointer,y).getLetter() + word;
                walkingPointer--;
            }
        } else if(scrabbleMove.getDirection() == Direction.VERTICAL) {
            int walkingPointer = y;
            while (walkingPointer < Board.SIZE && Character.isAlphabetic(this.board.getTileOnBoard(x,walkingPointer).getLetter())) {
                word += String.valueOf(this.board.getTileOnBoard(x,walkingPointer).getLetter());
                walkingPointer++;
            }
            walkingPointer = y - 1;
            while (walkingPointer >= 0 && Character.isAlphabetic(this.board.getTileOnBoard(x,walkingPointer).getLetter())) {
                word = this.board.getTileOnBoard(x,walkingPointer).getLetter() + word;
                walkingPointer--;

            }
        }
        return word;
    }

    private ArrayList<String> findSurroundingWords(ScrabbleMove move) {
        Direction newDirection = move.getDirection() == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
        ArrayList<String> surroundingWordList = new ArrayList<>();
        for (int i = 0; i < move.getCoords().size(); i++) {
            ArrayList<BoardClick> arr= new ArrayList<>();
            arr.add(move.getCoords().get(i));
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

        adjacentToSquare = (move.getWord().length() > move.getCoords().size());
        isWord = lib.isValidWord(move.getWord().toLowerCase());
        for (String word : findSurroundingWords(move)) {
            if (!lib.isValidWord(word)) {
                surroundingWordsAreWords = false;
            }
        }
        return adjacentToSquare && isWord && surroundingWordsAreWords;
    }

    private void deleteInvalidWordFromBoard(ScrabbleMove move){
        for (int i = 0; i < move.getCoords().size(); i++) {
            this.board.getTileOnBoard(move.getCoords().get(i).getCoords()[0], move.getCoords().get(i).getCoords()[1]).setLetter(' ');
            move.getPlayer().addLetter(String.valueOf( this.board.getTileOnBoard(move.getCoords().get(i).getCoords()[0], move.getCoords().get(i).getCoords()[1])));
        }
    }

//    private int calculateSurroundingWordsScore(int coords[][]) {
//        for (this.findSurroundingWords())
//    }

    private int calculateMoveScore(ScrabbleMove move) {
        int total = 0;
        ArrayList<String> lettersToScore = new ArrayList<>();
        for (char c : move.getWord().toCharArray()) {
            lettersToScore.add(String.valueOf(c));
        }

        ArrayList<Multiplier> wordMultipliers = new ArrayList<>();
        for (int i = 0; i < move.getCoords().size(); i++) {
            Square tile = this.board.getTileOnBoard(move.getCoords().get(i).getCoords()[0], move.getCoords().get(i).getCoords()[1]);
            String letter = String.valueOf(tile.getLetter());
            lettersToScore.remove(letter);

            int value = this.getLetterScore(letter);

            if (tile.isPremiumSquare()) {
                if (tile.getMultiplier().getType() == Multiplier.Type.LETTER) { //will be fixed with merge with emily
                    value = tile.getMultiplier().calculateScore(value);
                } else {
                    wordMultipliers.add(tile.getMultiplier());
                }
            }
            total += value;
        }

        for (String s : lettersToScore) {
            total += this.getLetterScore(s);
        }

        for (String s : this.findSurroundingWords(move)) {
            total += this.scorePerLetter.get(s);
        }

        for (Multiplier m : wordMultipliers) {
            total = m.calculateScore(total);
        }

        return total;
    }

    public void play(ScrabbleMove move) {
        int playerTurnCounter = 0;
        Player currentPlayer = playerList.get(playerTurnCounter % playerList.size());
        boolean horizontal = true;
        boolean vertical = true;
        Direction dir = null;
        for (int i = 1; i < move.getCoords().size(); i++) {
            horizontal = horizontal && move.getCoords().get(i).getCoords()[1] == move.getCoords().get(0).getCoords()[1];
            vertical = vertical && move.getCoords().get(i).getCoords()[0] == move.getCoords().get(0).getCoords()[0];
        }

        if ((horizontal) && (vertical)) {
            System.out.println("decide what to do here");
        } else if (vertical) {
            dir = Direction.VERTICAL;
            System.out.println("here");
        } else if (horizontal) {
            dir = Direction.HORIZONTAL;
        } else {
            System.out.println("this is an issue");
        }

        move.setDirection(dir);
        move.setPlayer(currentPlayer);
        move.setWord(findFullWord(move));
        System.out.println(move.getWord());
        boolean validMove = checkMoveValidity(move);
        System.out.println(validMove);


        if (validMove) {
            //calculate score
            currentPlayer.setScore(currentPlayer.getScore() + calculateMoveScore(move));
            for (BoardClick b : move.getCoords()) {
                currentPlayer.removeLetter(b.getLetter());
            }

            for (int i = 0; i < move.getCoords().size(); i++) {
                currentPlayer.addLetter(letterBag.getRandomLetter());
            }

            playerTurnCounter++;
            System.out.println(currentPlayer.getScore());
            //UPDATE VIEWS
        } else {
            deleteInvalidWordFromBoard(move);
        }
        ScrabbleEvent event = new ScrabbleEvent(this, move, playerList.get(playerTurnCounter % playerList.size()), this.board, Status.NOT_DONE);
        //currentMove = new ScrabbleMove();
        for (ScrabbleView v: this.getViews()) {
            v.update(event);
        }
    }

    public static void main (String args[]) throws IOException {
        ScrabbleModel scrabble = new ScrabbleModel();

        ScrabbleGameFrame gameFrame = new ScrabbleGameFrame(scrabble);
        gameFrame.attachTextBoard(scrabble.board);
        scrabble.getViews().add(gameFrame);
        InitController initFrame = new InitController(scrabble);
    }
}
