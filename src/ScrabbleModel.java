import com.zetcode.Library;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

/**
 * A text-based playable version of Scrabble.
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleModel implements Serializable {

    /** A Scrabble board. */
    private Board board;

    /** An ArrayList of players. */
    private ArrayList<Player> playerList;

    /** A LetterBag containing the letter tiles. */
    private LetterBag letterBag;

    /** A Library for word validation checking. */
    private Library lib;

    /** A HashMap to store the score of each letter. */
    private HashMap<String, Integer> scorePerLetter;

    /** An ArrayList of ScrabbleViews that subscribes to the model. */
    private transient ArrayList<ScrabbleView> views;

    /** A PlayerComparator to compare the scores of players. */
    private PlayerComparator playerComparator;

    /** A word can either be horizontally, or vertically placed onto the board. */
    public enum Direction { HORIZONTAL, VERTICAL }

    /**
     * A word can either be horizontally, or vertically placed onto the board.
     */
    private boolean loadGame;
    /**
     * The game can either be finished or not finished.
     */
    public enum GameStatus { FINISHED, NOT_FINISHED, TIE, LOADED }

    /** A String representing the selected letter. */
    private transient String selectedLetter;

    /** A ScrabbleMove to represent the current move. */
    private ScrabbleMove currentMove;

    /** An integer representing the amount of consecutive skips. */
    private transient int skipCounter;

    /** A GameStatus representing the current status of the game. */
    private GameStatus status;

    /** An ArrayList of Integers to delete letters from the hand properly. */
    private transient ArrayList<Integer> usedLetters;

    /** An integer representing the player counter. */
    private int playerTurnCounter;

    /** A stack with UndoMove's for undo */
    private transient Stack<UndoMove> undoStack;

    /** A stack with UndoMove's for redo */
    private transient Stack<UndoMove> redoStack;

    public ScrabbleModel(boolean loadGame) {
        this.loadGame = loadGame;
        views = new ArrayList<>();
    }

    /**
     * Runs a text-based playable version of Scrabble.
     * @throws IOException If an I/O error occurs.
     * @author Guy Morgenshtern 101151430
     */

    public ScrabbleModel() throws IOException {
        board = new Board("/default_board.txt");
        initializeScrabbleModel();
        undoStack = new Stack<>();
        redoStack = new Stack<>();

    }

    /**
     * Initializes a ScrabbleModel using the specified custom board.
     * @param customBoard A 2D String array representing the custom board.
     * @param numRows An integer representing the number of rows the custom board has.
     * @param numCols An integer representing the number of columns the custom board has.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @author Emily Tang 101192604
     */
    public ScrabbleModel(String[][] customBoard, int numRows, int numCols) throws IOException {
        board = new Board(customBoard, numRows, numCols);
        initializeScrabbleModel();
    }

    /**
     * Initializes all fields of the ScrabbleModel except for the board.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @author Emily Tang 101192604
     */
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
        playerComparator = new PlayerComparator();
        initializeLetterBag("/letters_by_quantity");
    }

    public boolean isLoadGame() {
        return loadGame;
    }

    public void setLoadGame(boolean loadGame) {
        this.loadGame = loadGame;
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
     * Initialises a Scrabble.xyz game from a model generated from loading
     * @author Guy Morgenshtern - 101151430
     */
    public void initializeLoadedGame() throws IOException {
        views = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        usedLetters = new ArrayList<>();
        // numberOfTries = 0;
        ScrabbleMove initMove = new ScrabbleMove();
        initMove.setValid(true);
        new ScrabbleGameFrame(this); // navigate to the default game of Scrabble
        for (ScrabbleView v : this.views) {
            v.update(new ScrabbleEvent(this, initMove, this.getCurrentPlayer(), this.board, GameStatus.LOADED));
        }
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
            letterBag.addLetter(line.substring(0, 1), Integer.parseInt(line.substring(2, line.lastIndexOf("-"))));

            //letter score
            scorePerLetter.put(line.substring(0, 1), Integer.parseInt(line.substring(line.lastIndexOf("-") + 1)));
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
     * Gets the number of moves for the undo stack
     * @return An integer of the number of moves
     */
    public int getNumberOfUndoStack() { return undoStack.size(); }

    /**
     * Gets the number of moves for the redo stack
     * @return An integer of the number of moves
     */
    public int getNumberOfRedoStack() { return redoStack.size(); }

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
     * Initialize the board with a letter in the centre of the board.
     * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
     */
    public void initializeBoard() {
        // get a letter to place in the centre of the board
        String firstLetter = letterBag.getRandomLetter();
        while (firstLetter.equals(" ")) { // first letter cannot be blank
            letterBag.addLetter(" ", 1); // add the blank letter back into the letterBag
            firstLetter = letterBag.getRandomLetter(); // pull another letter from the letterBag
        }
        int rowCenterSquare = (board.getNumRows() - 1) / 2;
        int columnCenterSquare = (board.getNumCols() - 1) / 2;
        board.setSquare(firstLetter.toCharArray()[0], rowCenterSquare, columnCenterSquare);


        // Game is initialized with a singular letter in the centre of the board
        ArrayList<BoardClick> coordsList = new ArrayList<>();
        int[] coords = new int[2];
        coords[0] = rowCenterSquare;
        coords[1] = columnCenterSquare;
        BoardClick b = new BoardClick(coords, firstLetter);
        coordsList.add(b);
        ScrabbleMove m = new ScrabbleMove();
        m.setCoords(coordsList);
        m.setValid(true);
        m.setMoveType(ScrabbleMove.MoveType.INIT);

        // update the views to display the letter at the centre of the board
        for (ScrabbleView v : getViews()) {
            v.update(new ScrabbleEvent(this, m, playerList.get(0), board, GameStatus.NOT_FINISHED));
        }
    }

    /**
     * Given a ScrabbleMove, find the full word on the board (including all pre-existing adjacent letters).
     * @param scrabbleMove A ScrabbleMove to investigate.
     * @return A String representing the full word.
     * @author Guy Morgenshtern 101151430
     */
    private String findFullWord(ScrabbleMove scrabbleMove) {
        //getting the coords of first letter of move
        int x = scrabbleMove.getCoords().get(0).getCoords()[0];
        int y = scrabbleMove.getCoords().get(0).getCoords()[1];
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
     * Finds any additional words that were created by the ScrabbleMove provided.
     * @param move A ScrabbleMove to investigate.
     * @return An ArrayList of Strings representing the surrounding words.
     * @author Guy Morgenshtern 101151430
     */
    private ArrayList<String> findSurroundingWords(ScrabbleMove move) {
        Direction newDirection = move.getDirection() == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
        ArrayList<String> surroundingWordList = new ArrayList<>();
        for (int i = 0; i < move.getCoords().size(); i++) {
            ArrayList<BoardClick> arr = new ArrayList<>();
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
     * Given a ScrabbleMove, determines its validity according to the Scrabble rules.
     * @param move A ScrabbleMove to investigate.
     * @return True, if the given ScrabbleMove is valid. False, if not.
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
     * Removes the given ScrabbleMove from the board when determined it is invalid.
     * @param move A ScrabbleMove to investigate.
     */
    private void deleteInvalidWordFromBoard(ScrabbleMove move) {
        ArrayList<BoardClick> boardClicks = move.getCoords();
        for (BoardClick boardClick : boardClicks) {
            board.getTileOnBoard(boardClick.getCoords()[0], boardClick.getCoords()[1]).setLetter(' ');
        }
    }
    /**
     * Calculates the total score for the given ScrabbleMove, including any surrounding words.
     * @param move A ScrabbleMove to investigate.
     * @return An integer representing the total score for the given ScrabbleMove.
     * @author Guy Morgenshtern 101151430
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
        ArrayList<BoardClick> boardClicks = move.getCoords();
        for (BoardClick boardClick : boardClicks) {
            Square tile = board.getTileOnBoard(boardClick.getCoords()[0], boardClick.getCoords()[1]);
            String letter = String.valueOf(tile.getLetter());

            //remove any letter that has already been scored
            lettersToScore.remove(letter);

            int value = getLetterScore(letter);

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
            total += getLetterScore(s);
        }
        //score any surrounding words created
        for (String s : findSurroundingWords(move)) {
            for (char c : s.toCharArray()) {
                total += scorePerLetter.get(c + "");
            }
        }
        for (Multiplier m : wordMultipliers) {
            total = m.calculateScore(total);
        }
        return total;
    }

    public Stack<UndoMove> getUndoStack() {
        return undoStack;
    }

    /**
     * Undoes the players current move
     * @author Alexander Hum 101180821
     */
    public void undo() {
        if (!(undoStack.peek().getMove().getPlayer() instanceof BotPlayer)) {
            UndoMove m = undoStack.pop();
            UndoMove m2 = redoStack.push(m);
            playerTurnCounter--;

            // sets squares on the board to be blank
            for (int i = 0; i < m.getMove().getCoords().size(); i++) {
                board.getTileOnBoard(m.getMove().getCoords().get(i).getCoords()[0], m.getMove().getCoords().get(i).getCoords()[1]).setLetter(' ');
            }

            Player currentPlayer = m.getMove().getPlayer();
            System.out.println("Current player is: " + currentPlayer);
            for (int i = 0; i < m.getMove().getCoords().size(); i++) {
                System.out.println("currentPlayer.getAvailableLetters().size() - 1 - i: " + (currentPlayer.getAvailableLetters().size() - 1 - i));
                this.letterBag.addLetter(currentPlayer.getAvailableLetters().get(currentPlayer.getAvailableLetters().size() - 1 - i), 1);
                currentPlayer.getAvailableLetters().remove(currentPlayer.getAvailableLetters().size() - 1 - i);
            }

            // letters in current players hand
            String letters = currentPlayer.getAvailableLetters().size() + " Letters in " + currentPlayer.getName() + "'s hand: ";
            for (String s : currentPlayer.getAvailableLetters()) {
                letters += s + " ";
            }
            System.out.println(letters);

            for (BoardClick b : m.getMove().getCoords()) {
                currentPlayer.addLetter(b.getLetter());
            }

            m.getMove().setMoveType(ScrabbleMove.MoveType.UNDO);
            currentPlayer.setScore(m.getScore());

            for (ScrabbleView v : views) {
                v.update(new ScrabbleEvent(this, m.getMove(), m.getMove().getPlayer(), board, GameStatus.NOT_FINISHED));
            }

            System.out.println(getCurrentPlayer().getScore());

            ScrabbleEvent event = new ScrabbleEvent(this, m.getMove(), playerList.get(playerTurnCounter % playerList.size()), this.board, GameStatus.NOT_FINISHED);
            currentMove = new ScrabbleMove();
            for (ScrabbleView v : this.getViews()) {
                v.update(event);
            }
            System.out.println("Popped " + m);
            System.out.println(undoStack.size());
            System.out.println("Redo Stack:\n" + m2);
        }
    }

    /**
     * Redoes the player current move
     * @author Alexander Hum 101180821
     */
    public void redo() {
        UndoMove r = redoStack.pop();
        r.getMove().setMoveType(ScrabbleMove.MoveType.REDO);
        for (BoardClick b : r.getMove().getCoords()) {
            board.getTileOnBoard(b.getCoords()[0], b.getCoords()[1]).setLetter(b.getLetter().charAt(0));
        }
        play(r.getMove());
    }

    /**
     * Determines if all players have skipped their turn in succession.
     * @return Return true, if all players skipped their turn in succession. False, if not.
     * @author Alexander Hum 101180821
     */
    private boolean haveAllPlayerSkipped() {
        skipCounter++;
        return skipCounter == playerList.size();
    }

    public Player getCurrentPlayer() {
        Player currentPlayer = playerList.get(playerTurnCounter % playerList.size());
        return currentPlayer;
    }

    /**
     * Plays the given ScrabbleMove that was constructed by a player.
     * @param move A ScrabbleMove to investigate.
     * @author Guy Morgenshtern 101151430. Edited by Alexander Hum 101180821.
     */
    public void play(ScrabbleMove move) {
        Player currentPlayer = (move.getMoveType() == ScrabbleMove.MoveType.REDO ? move.getPlayer() : getCurrentPlayer());
        boolean horizontal = true;
        boolean vertical = true;
//        numberOfTries++;

        // coords is checking letters on the board
        if(status == GameStatus.NOT_FINISHED) {
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
                    // if the player skipped but the game isn't over, skip to next player
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
                skipCounter = 0;

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
                    System.out.println("This is an issue!");
                }

                //begin building the rest of the move
                move.setPlayer(currentPlayer);
                move.setWord(findFullWord(move));
                System.out.println(currentPlayer.getName() + " is trying to play: " + move.getWord() + ".");
                move.setValid(checkMoveValidity(move));

                //score the move if it is valid
                if (move.isValid()) {
                    if (redoStack.size() > 0 && move.getMoveType() != ScrabbleMove.MoveType.REDO) {
                        redoStack.clear();
                    }

                    usedLetters.sort(Collections.reverseOrder());
                    ArrayList<Integer> indexesOfUsedLetters = (ArrayList<Integer>) usedLetters.clone();

                    UndoMove undoMove = new UndoMove(move, currentPlayer.getScore(), indexesOfUsedLetters);

                    //calculate score
                    currentPlayer.setScore(currentPlayer.getScore() + calculateMoveScore(move));

                    // remove played letters
                    usedLetters.sort(Collections.reverseOrder());
                    for (Integer index : usedLetters) {
                        currentPlayer.removeLetter(index);
                    }
                    // BotPlayer never touches a JButton, so usedLetters is never added to
                    if (currentPlayer instanceof BotPlayer) {
                        // remove used letters from the BotPlayer's hand
                        for (BoardClick boardClick : move.getCoords()) {
                            currentPlayer.removeLetter(boardClick.getLetter());
                        }
                    }

                    // deal new letters to player
                    int numberOfBoardClicksUsed = move.getCoords().size();
                    // must account for the additional boardLetter that was added to the BotPlayer's hand
                    if (currentPlayer instanceof BotPlayer) {
                        numberOfBoardClicksUsed--;
                    }
                    for (int i = 0; i < numberOfBoardClicksUsed; i++) {
                        currentPlayer.addLetter(letterBag.getRandomLetter());
                    }
                    // add move to stack
                    undoStack.push(undoMove);
                    System.out.println(undoStack);
                    //next player
                    playerTurnCounter++;
                } else {
                    deleteInvalidWordFromBoard(move);
                    move.setValid(false);
                }
                getUsedLetters().clear();


                //update views
                ScrabbleEvent event = new ScrabbleEvent(this, move, playerList.get(playerTurnCounter % playerList.size()), board, GameStatus.NOT_FINISHED);
                currentMove = new ScrabbleMove();
                for (ScrabbleView v : getViews()) {
                    v.update(event);
                }
            }

            // BotPlayer will play next if they're the next player
            Player nextPlayer = playerList.get(playerTurnCounter % playerList.size());
            if (nextPlayer instanceof BotPlayer && move.getMoveType() != ScrabbleMove.MoveType.REDO) {
                play(((BotPlayer) nextPlayer).play(board));
            }
        }
    }

//    public void loadScrabble(String fileName) throws IOException {
//        FileInputStream fileIn = new FileInputStream(fileName);
//        ObjectInputStream in = new ObjectInputStream(fileIn);
//        Player.deserialize(in);
//    }

//    public void saveScrabble(String prefix) throws IOException {
//        String suffix = ".ser";
//        String playerListFile = prefix + "_player_list" + suffix;
//        String boardFile = prefix + "_board" + suffix;
//
//        FileOutputStream fileOut = new FileOutputStream(playerListFile);
//        ObjectOutputStream out = new ObjectOutputStream(fileOut);
//        for (Player p : playerList) {
//            System.out.println(p);
//            p.serialize(out);
//        }
//        fileOut.close();
//
//        fileOut = new FileOutputStream(boardFile);
//        out = new ObjectOutputStream(fileOut);
//        this.board.serialize(out);
//        fileOut.close();
//
//    }

    public void saveScrabble(String prefix) throws IOException {
        String suffix = "_sxyz.ser";

        FileOutputStream fileOut = new FileOutputStream(prefix + suffix);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        fileOut.close();
    }

    public static ScrabbleModel loadScrabble(String filePath) throws IOException {

        FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(fileIn);
        } catch (StreamCorruptedException e) {
            JOptionPane.showMessageDialog(null, "Could not load game, please ensure file type is " +
                    "'.ser' and the file is a valid Scrabble.xyz save");
            e.printStackTrace();
        }

        ScrabbleModel m = new ScrabbleModel(true);
        try
        {
            m = (ScrabbleModel) in.readObject();

        }
        catch (EOFException exc) {
            System.out.println("end of file");
            fileIn.close();
            in.close();
        }
        catch (IOException | ClassNotFoundException exc) {
            JOptionPane.showMessageDialog(null, "Could not load game, please ensure file type is " +
                    "'.ser' and the file is a valid Scrabble.xyz save");
            exc.printStackTrace();
        }

        return m;
    }
}
