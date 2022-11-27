import com.zetcode.Library;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A text-based playable version of Scrabble.
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleModel {

    /** A Scrabble board. */
    private final Board board;

    /** An ArrayList of players. */
    private ArrayList<Player> playerList;

    /** A LetterBag containing the letter tiles. */
    private LetterBag letterBag;

    /** A Library for word validation checking. */
    private Library lib;

    /** A HashMap to store the score of each letter. */
    private HashMap<String, Integer> scorePerLetter;

    /** An ArrayList of ScrabbleViews that subscribes to the model. */
    private ArrayList<ScrabbleView> views;

    /** An integer representing the number of tries the current player has made to add a word to the board. */
    private int numberOfTries;

    private PlayerComparator playerComparator;

    /**
     * A word can either be horizontally, or vertically placed onto the board.
     */
    public enum Direction { HORIZONTAL, VERTICAL }

    /**
     * The game can either be finished or not finished.
     */
    public enum GameStatus { FINISHED, NOT_FINISHED, TIE }

    /** A String representing the selected letter. */
    private String selectedLetter;

    /** A ScrabbleMove to represent the current move. */
    private ScrabbleMove currentMove;

    /** An integer representing the amount of consecutive skips. */
    private int skipCount;

    /** A GameStatus representing the current status of the game. */
    private GameStatus status;

    /** An ArrayList of Integers to delete letters from the hand properly. */
    private ArrayList<Integer> usedLetters;

    /** An integer representing the player counter. */
    private int playerTurnCounter;

    /**
     * Runs a text-based playable version of Scrabble.
     * @throws IOException If an I/O error occurs.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleModel() throws IOException {
        board = new Board("/default_board.txt");
        initializeScrabbleModel();
    }

    public ScrabbleModel(String[][] customBoard, int numRows, int numCols) throws IOException {
        board = new Board(customBoard, numRows, numCols);
        initializeScrabbleModel();
    }

    private void initializeScrabbleModel() throws IOException {
        lib = new Library();
        scorePerLetter = new HashMap<>();
        views = new ArrayList<>();
        selectedLetter = "";
        playerList = new ArrayList<>();
        playerTurnCounter = 0;
        currentMove = new ScrabbleMove();
        letterBag = new LetterBag();
        usedLetters = new ArrayList<>();
        status = GameStatus.NOT_FINISHED;
        numberOfTries = 0;
        playerComparator = new PlayerComparator();
        initializeLetterBag("/letters_by_quantity");
    }

    /**
     * @return A Board representing the current board.
     * @author Guy Morgenshtern 101151430
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return An ArrayList of Integers to delete letters from the hand properly.
     * @author Guy Morgenshtern 101151430
     */
    public ArrayList<Integer> getUsedLetters() {
        return usedLetters;
    }

    /**
     * @return A ScrabbleMove to represent the current move.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove getCurrentMove() {
        return currentMove;
    }

    /**
     * @return A String representing the selected letter.
     * @author Guy Morgenshtern 101151430
     */
    public String getSelectedLetter() {
        return selectedLetter;
    }

    /**
     * Sets the selected letter to be the specified string.
     * @param selectedLetter A String representing the selected letter.
     * @author Guy Morgenshtern 101151430
     */
    public void setSelectedLetter(String selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    /**
     * @return An ArrayList of ScrabbleViews.
     * @author Guy Morgenshtern 101151430
     */
    public ArrayList<ScrabbleView> getViews() {
        return views;
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
        for (String name : namesOfRealPlayers) {
            playerList.add(new Player(name));
        }
        for (int i = 0; i < numOfBots; i++) {
            playerList.add(new BotPlayer(namesOfBotPlayers[i]));
        }
        dealLetters();
        initializeBoard();
    }

    /**
     * Initializes the LetterBag using the specified file.
     * @param fileName A String representing the name of the file that contains the specific quantity of letters.
     * @throws IOException If an I/O error occurs.
     */
    public void initializeLetterBag(String fileName) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line = br.readLine();
        while (line != null) {
            //letter quantity
            letterBag.addLetter(line.substring(0,1), Integer.parseInt(line.substring(2,line.lastIndexOf("-"))));

            //letter score
            scorePerLetter.put(line.substring(0,1), Integer.parseInt(line.substring(line.lastIndexOf("-") + 1)));
            line = br.readLine();
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
     * @param l A String representing a letter to check the score of.
     * @return An integer representing the score of letter.
     * @author Guy Morgenshtern 101151430
     */
    private int getLetterScore(String l) {
        return scorePerLetter.get(l);
    }

    /**
     * Deals seven letters to each player.
     * @author Guy Morgenshtern 101151430
     */
    public void dealLetters() {
        for (Player p : playerList) {
            for (int i = 0; i < 7; i++) {
                p.addLetter(letterBag.getRandomLetter());
            }
        }
    }

    /**
     * initialize the board
     * @author Guy Morgenshtern 101151430
     */
    public void initializeBoard() {
        String firstLetter = letterBag.getRandomLetter();
        while (firstLetter.equals("")) {
            firstLetter = letterBag.getRandomLetter();
        }
        int rowCenterSquare = (board.getNumRows() - 1) / 2;
        int columnCenterSquare = (board.getNumCols() - 1) / 2;
        board.setSquare(firstLetter.toCharArray()[0], rowCenterSquare, columnCenterSquare);
        ArrayList<BoardClick> coordsList = new ArrayList<>();

        // Game is initialized with a singular letter in the centre of the board
        int[] coords = new int[2];
        coords[0] = rowCenterSquare;
        coords[1] = columnCenterSquare;
        BoardClick b = new BoardClick(coords, firstLetter);
        coordsList.add(b);
        ScrabbleMove m = new ScrabbleMove();
        m.setCoords(coordsList);
        m.setValid(true);

        //update the views for the first time
        for (ScrabbleView v : this.getViews()) {
            v.update(new ScrabbleEvent(this, m, playerList.get(0), board, GameStatus.NOT_FINISHED));
        }
    }

    /**
     * given a scrabble move, finds the full word on the baord, including all existing adjacent letter
     * @param scrabbleMove
     * @return String: final word
     * @author Guy Morgenshtern 101151430
     */
    private String findFullWord(ScrabbleMove scrabbleMove) {
        //getting the coords of first letter of move
        int x = scrabbleMove.getCoords().get(0).coords()[0];
        int y = scrabbleMove.getCoords().get(0).coords()[1];
        String word = "";

        //concat all letters to the right
        if(scrabbleMove.getDirection() == Direction.HORIZONTAL) {
            int walkingPointer = x;
            while (walkingPointer < board.getNumCols() && Character.isAlphabetic(board.getTileOnBoard(walkingPointer,y).getLetter())) {
                word += String.valueOf(board.getTileOnBoard(walkingPointer,y).getLetter());
                walkingPointer++;
            }

            //concat all letters to the left
            walkingPointer = x - 1;
            while (walkingPointer >= 0 && Character.isAlphabetic(board.getTileOnBoard(walkingPointer,y).getLetter())) {
                word = board.getTileOnBoard(walkingPointer,y).getLetter() + word;
                walkingPointer--;
            }
            //concat all letters below
        } else if (scrabbleMove.getDirection() == Direction.VERTICAL) {
            int walkingPointer = y;
            while (walkingPointer < board.getNumRows() && Character.isAlphabetic(board.getTileOnBoard(x,walkingPointer).getLetter())) {
                word += String.valueOf(board.getTileOnBoard(x,walkingPointer).getLetter());
                walkingPointer++;
            }
            //concat all letters above
            walkingPointer = y - 1;
            while (walkingPointer >= 0 && Character.isAlphabetic(board.getTileOnBoard(x,walkingPointer).getLetter())) {
                word = board.getTileOnBoard(x,walkingPointer).getLetter() + word;
                walkingPointer--;
            }
        }
        return word;
    }

    /**
     * Finds any additional words that were created by the move provided
     * @param move
     * @return ArrayList of surrounding words
     * @author Guy Morgenshtern 101151430
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
     * @author Guy Morgenshtern 101151430
     */
    private boolean checkMoveValidity(ScrabbleMove move) {
        boolean surroundingWordsAreWords = true;

        boolean adjacentToSquare = (move.getWord().length() > move.getCoords().size());
        boolean isWord = lib.isValidWord(move.getWord().toLowerCase());
        for (String word : findSurroundingWords(move)) {
            if (!lib.isValidWord(word)) {
                surroundingWordsAreWords = false;
            }
        }
        return adjacentToSquare && isWord && surroundingWordsAreWords;
    }

    /**
     * Removes word from the board when determined it is invalid
     * @param move
     */
    private void deleteInvalidWordFromBoard(ScrabbleMove move){
        for (int i = 0; i < move.getCoords().size(); i++) {
            board.getTileOnBoard(move.getCoords().get(i).coords()[0], move.getCoords().get(i).coords()[1]).setLetter(' ');
        }
    }

    /**
     * Calculates the total score for a move including any secondary surrounding words
     * @param move
     * @return total score for the move
     * @author Guy Morgenshtern - 101151430
     */
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
            Square tile = this.board.getTileOnBoard(move.getCoords().get(i).coords()[0], move.getCoords().get(i).coords()[1]);
            String letter = String.valueOf(tile.getLetter());

            //remove any letter that has already been scored
            lettersToScore.remove(letter);


            int value = this.getLetterScore(letter);

            //if letter multiplier, use it right away, if word multiplier store it to be applied at end of scoring
            if (tile.isPremiumSquare()) {
                if (tile.getMultiplier().getType() == Multiplier.Type.LETTER) {
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
     * Determines if all players have skipped their turn in succession.
     * @return Return true, if all players skipped their turn in succession. False, if not.
     * @author Alexander Hum 101180821
     */
    private boolean haveAllPlayerSkipped() {
        skipCount++;
        return skipCount == playerList.size();
    }

    /**
     * Plays a move constructed by a player
     * @param move
     * @author Guy Morgenshtern 101151430. Edited by Alexander Hum 101180821.
     */
    public void play(ScrabbleMove move) {
        Player currentPlayer = playerList.get(playerTurnCounter % playerList.size());
        boolean horizontal = true;
        boolean vertical = true;
        Direction dir = null;
        this.numberOfTries++;

        // coords is checking letters on the board

        if (status == GameStatus.NOT_FINISHED) {
            if (move.getCoords().size() == 0) {
                //if the game has ended (all players have skipped their turn in succession)
                if (haveAllPlayerSkipped()) {
                    status = (playerList.get(0).getScore() == playerList.get(1).getScore())? GameStatus.TIE: GameStatus.FINISHED;
                    //create the end game event
                    playerList.sort(playerComparator);
                    ScrabbleEvent event = new ScrabbleEvent(this, move, currentPlayer, this.board, status);
                    for (ScrabbleView v : this.getViews()) {
                        v.update(event);
                    }
                } else {
                    //if the player skipped but the game isn't over, skip to next player
                    int rand = (int)(Math.random() * 7);
                    currentPlayer.removeLetter(rand);
                    currentPlayer.addLetter(letterBag.getRandomLetter());
                    playerTurnCounter++;
                    ScrabbleEvent event = new ScrabbleEvent(this, move, playerList.get(playerTurnCounter % playerList.size()), this.board, GameStatus.NOT_FINISHED);
                    for (ScrabbleView v : this.getViews()) {
                        v.update(event);
                    }
                }
            } else {
                //reset skip count since a player has played a real turn
                skipCount = 0;

                //determine if the word is horizontal or vertical
                for (int i = 1; i < move.getCoords().size(); i++) {
                    horizontal = horizontal && move.getCoords().get(i).coords()[1] == move.getCoords().get(0).coords()[1];
                    vertical = vertical && move.getCoords().get(i).coords()[0] == move.getCoords().get(0).coords()[0];
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
                    this.numberOfTries = 0;
                    //calculate score
                    currentPlayer.setScore(currentPlayer.getScore() + calculateMoveScore(move));

                    //removed played letters
                    usedLetters.sort(Collections.reverseOrder());
                    for (Integer index : usedLetters) {
                        currentPlayer.removeLetter(index);
                    }

                    //give new letters to player
                    for (int i = 0; i < move.getCoords().size(); i++) {
                        currentPlayer.addLetter(letterBag.getRandomLetter());
                    }

                    //next player
                    playerTurnCounter++;
                } else {
                    deleteInvalidWordFromBoard(move);
                    move.setValid(false);
                    // boardSize squared
                    int MAX_ATTEMPTS = 100;
                    if (this.numberOfTries == MAX_ATTEMPTS) {
                        playerTurnCounter++;
                        haveAllPlayerSkipped();
                        this.numberOfTries = 0;
                        System.out.println("Player attempted too many turns");
                    }
                }
                this.getUsedLetters().clear();


                //update views
                ScrabbleEvent event = new ScrabbleEvent(this, move, playerList.get(playerTurnCounter % playerList.size()), this.board, GameStatus.NOT_FINISHED);
                currentMove = new ScrabbleMove();
                for (ScrabbleView v : this.getViews()) {
                    v.update(event);
                }
            }

            //bot play
            if (playerList.get(playerTurnCounter % playerList.size()) instanceof BotPlayer) {
                this.play(((BotPlayer) playerList.get(playerTurnCounter % playerList.size())).play(board));
            }
        }
    }

}
