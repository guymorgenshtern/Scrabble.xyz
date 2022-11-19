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
    public enum GameStatus {FINISHED, NOT_FINISHED}
    private String selectedLetter;
    private ScrabbleMove currentMove;
    private ScrabbleMove skipMove;
    private int skipCount;

    private ArrayList<Integer> usedLetters;

    private int playerTurnCounter;

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
        this.playerTurnCounter = 0;
        this.currentMove = new ScrabbleMove();
        this.letterBag = new LetterBag();
        this.usedLetters = new ArrayList<>();

        initializeLetterBag("res/letters_by_quantity");
        System.out.println("Letter Bag Size = " + letterBag.bagSize());
    }

    public ArrayList<Integer> getUsedLetters() {
        return usedLetters;
    }

    public ScrabbleMove getCurrentMove() {
        return currentMove;
    }

    public ScrabbleMove getSkipMove() {
        return skipMove;
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
     * Initializes BotPlayers. Initializes real players with their specified names. Deal letters to players. Initialize
     * the game board.
     * NOTE: Error checking already in InitController. Users can only enter between two and four players there.
     * @param numOfBots An integer representing the amount of BotPlayers the user would like to play Scrabble with.
     * @param namesOfRealPlayers An array of Strings representing the names of the real players.
     * @throws IOException If an I/O error occurs.
     * @author Emily Tang 101192604
     */
    public void initializeGame(int numOfBots, String[] namesOfRealPlayers) throws IOException {
        String[] namesOfBotPlayers = new String[] { "Dumb Dumpling", "Stupid Spaghetti", "Incompetent Iguana" };
        for (int i = 0; i < numOfBots; i++) {
            playerList.add(new BotPlayer(namesOfBotPlayers[i]));
        }
        for (String name : namesOfRealPlayers) {
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
            //letter quantity
            letterBag.addLetter(line.substring(0,1), Integer.parseInt(line.substring(2,line.lastIndexOf("-"))));

            //letter score
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

        //Game is initialized with a singular letter in the centre of the baord
        int coords[] = new int[2];
        coords[0] = (Board.SIZE - 1) / 2;
        coords[1] = (Board.SIZE - 1) / 2;
        BoardClick b = new BoardClick(coords, firstLetter);
        coordsList.add(b);
        ScrabbleMove m = new ScrabbleMove();
        m.setCoords(coordsList);
        m.setValid(true);

        //update the views for the first time
        for (ScrabbleView v : this.getViews()) {
            v.update(new ScrabbleEvent(this, m, playerList.get(0), board, Status.NOT_DONE, GameStatus.NOT_FINISHED));
        }

    }

    /**
     * given a scrabble move, finds the full word on the baord, including all existing adjacent letter
     * @param scrabbleMove
     * @return String: final word
     */
    private String findFullWord(ScrabbleMove scrabbleMove) {
        //this assumes the move array is sorted from first letter->last, we should make sure this is always the case
        System.out.println(scrabbleMove.getCoords());

        //getting the coords of first letter of move
        int x = scrabbleMove.getCoords().get(0).getCoords()[0];
        int y = scrabbleMove.getCoords().get(0).getCoords()[1];
        String word = "";

        //concat all letters to the right
        if(scrabbleMove.getDirection() == Direction.HORIZONTAL) {
            int walkingPointer = x;
            while (walkingPointer < Board.SIZE && Character.isAlphabetic(this.board.getTileOnBoard(walkingPointer,y).getLetter())) {
                word += String.valueOf(this.board.getTileOnBoard(walkingPointer,y).getLetter());
                walkingPointer++;
            }

            //concat all letters to the left
            walkingPointer = x - 1;
            while (walkingPointer >= 0 && Character.isAlphabetic(this.board.getTileOnBoard(walkingPointer,y).getLetter())) {
                word = this.board.getTileOnBoard(walkingPointer,y).getLetter() + word;
                walkingPointer--;
            }
            //concat all letters below
        } else if(scrabbleMove.getDirection() == Direction.VERTICAL) {
            int walkingPointer = y;
            while (walkingPointer < Board.SIZE && Character.isAlphabetic(this.board.getTileOnBoard(x,walkingPointer).getLetter())) {
                word += String.valueOf(this.board.getTileOnBoard(x,walkingPointer).getLetter());
                walkingPointer++;
            }
            //concat all letters above
            walkingPointer = y - 1;
            while (walkingPointer >= 0 && Character.isAlphabetic(this.board.getTileOnBoard(x,walkingPointer).getLetter())) {
                word = this.board.getTileOnBoard(x,walkingPointer).getLetter() + word;
                walkingPointer--;

            }
        }
        return word;
    }

    /**
     * Finds any additional words that were created by the move provided
     * @param move
     * @return ArrayList of surrounding words
     */
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

    /**
     * Given a scrabble move, determines its validity according to scrabble rules
     * @param move
     * @return boolean of the validity
     */
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
            this.board.printBoard();
        }
    }

    private int calculateMoveScore(ScrabbleMove move) {
        int total = 0;
        ArrayList<String> lettersToScore = new ArrayList<>();
        for (char c : move.getWord().toCharArray()) {
            lettersToScore.add(String.valueOf(c));
        }

        ArrayList<Multiplier> wordMultipliers = new ArrayList<>();

        //scoring is split into letters played in the move and any existing adjacent letters

        //letters from move scoring
        //important to do because we need their coordinates to determine if they lie on a multiplier
        for (int i = 0; i < move.getCoords().size(); i++) {
            Square tile = this.board.getTileOnBoard(move.getCoords().get(i).getCoords()[0], move.getCoords().get(i).getCoords()[1]);
            String letter = String.valueOf(tile.getLetter());

            //remove any letter that has already been scored
            lettersToScore.remove(letter);


            int value = this.getLetterScore(letter);

            //if letter multiplier, use it right away, if word multiplier store it to be applied at end of scoring
            if (tile.isPremiumSquare()) {
                if (tile.getMultiplier().getType() == Multiplier.Type.LETTER) { //will be fixed with merge with emily
                    value = tile.getMultiplier().calculateScore(value);
                } else {
                    wordMultipliers.add(tile.getMultiplier());
                }
            }
            total += value;
        }

        //score letters not explicitly placed on board but present in the move
        for (String s : lettersToScore) {
            total += this.getLetterScore(s);
        }

        //score any surrounding words created
        for (String s : this.findSurroundingWords(move)) {
            for (char c : s.toCharArray()) {
                total += this.scorePerLetter.get(c + "");
            }

        }

        for (Multiplier m : wordMultipliers) {
            total = m.calculateScore(total);
        }

        return total;
    }

    /**
     * determines if all players have skipped their turn in succession
     * @return boolean
     */
    private boolean haveAllPlayerSkipped() {
        skipCount++;
        return skipCount == playerList.size();
    }

    /**
     * Plays a move constructed by a player
     * @param move
     */
    public void play(ScrabbleMove move) {
        Player currentPlayer = playerList.get(playerTurnCounter % playerList.size());
        boolean horizontal = true;
        boolean vertical = true;
        Direction dir = null;

        // coords is checking letters on the board
        if (move.getCoords().size() == 0) {
            //if the game has ended (all players have skipped their turn in succession)
            if (haveAllPlayerSkipped()) {
                //create the end game event
                ScrabbleEvent event = new ScrabbleEvent(this, move, currentPlayer, this.board, Status.DONE, GameStatus.FINISHED);
                for (ScrabbleView v : this.getViews()) {
                    v.update(event);
                }
            } else {
                //if the player skipped but the game isn't over, skip to next player
                playerTurnCounter++;
                ScrabbleEvent event = new ScrabbleEvent(this, move, currentPlayer, this.board, Status.DONE, GameStatus.NOT_FINISHED);
                for (ScrabbleView v : this.getViews()) {
                    v.update(event);
                }
            }
        } else {
            //reset skip count since a player has played a real turn
            skipCount = 0;

            //determine if the word is horizontal or vertical
            for (int i = 1; i < move.getCoords().size(); i++) {
                horizontal = horizontal && move.getCoords().get(i).getCoords()[1] == move.getCoords().get(0).getCoords()[1];
                vertical = vertical && move.getCoords().get(i).getCoords()[0] == move.getCoords().get(0).getCoords()[0];
            }

            if ((horizontal) && (vertical) && move.getCoords().size() == 1) {
                move.setDirection(Direction.HORIZONTAL);
                String horizontalCheck = findFullWord(move);
                move.setDirection(Direction.VERTICAL);
                String verticalCheck = findFullWord(move);

                if (verticalCheck.length() > move.getCoords().size()) {
                    move.setWord(verticalCheck);
                    move.setDirection(Direction.VERTICAL);
                } else {
                    move.setWord(horizontalCheck);
                    move.setDirection(Direction.HORIZONTAL);
                }
            } else if (vertical) {
                move.setDirection(Direction.VERTICAL);
            } else if (horizontal) {
                move.setDirection(Direction.HORIZONTAL);
            } else {
                System.out.println("this is an issue");
            }

            //begin building the rest of the move
            move.setPlayer(currentPlayer);
            move.setWord(findFullWord(move));
            System.out.println(move.getWord());
            move.setValid(checkMoveValidity(move));

            //score the move if it is valid
            if (move.isValid()) {
                //calculate score
                currentPlayer.setScore(currentPlayer.getScore() + calculateMoveScore(move));

                //removed played letters
                for (BoardClick b : move.getCoords()) {
                    currentPlayer.removeLetter(String.valueOf(this.board.getTileOnBoard(b.getCoords()[0], b.getCoords()[1]).getLetter()));
                }

                //give new letters to player
                for (int i = 0; i < move.getCoords().size(); i++) {
                    currentPlayer.addLetter(letterBag.getRandomLetter());
                }

                //next player
                playerTurnCounter++;
                System.out.println(currentPlayer.getScore());
                //UPDATE VIEWS
            } else {
                deleteInvalidWordFromBoard(move);
                move.setValid(false);
            }
            //ADD INVALID MOVE BOOLEAN TO EVENT -> USE IT TO WIPE LETTERS ON BOARDPANEL
            this.getUsedLetters().clear();

            //update views
            ScrabbleEvent event = new ScrabbleEvent(this, move, playerList.get(playerTurnCounter % playerList.size()), this.board, Status.NOT_DONE, GameStatus.NOT_FINISHED);
            currentMove = new ScrabbleMove();
            for (ScrabbleView v : this.getViews()) {
                v.update(event);
            }
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
