import com.zetcode.Library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A text-based playable version of Scrabble.
 * @author Guy Morgenshtern 101151430
 */
public class Game {

    /** A Scrabble board. */
    private static Board board;

    /** An ArrayList of players. */
    private static ArrayList<Player> playerList;

    /** A LetterBag containing the letter tiles. */
    private static LetterBag letterBag;

    /** A Library for word validation checking. */
    private static Library lib;

    /**
     * A word can either be horizontally, or vertically placed onto the board.
     */
    public enum Direction { HORIZONTAL, VERTICAL }

    /**
     * Runs a text-based playable version of Scrabble.
     * @param args An array of command-line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main (String[] args) throws IOException {
        board = new Board("res/default_board.txt");
        lib = new Library();

        playerList = new ArrayList<>();
        // TODO: initialize InitController here instead??
        // initializePlayers();

        letterBag = new LetterBag();

        initializeLetterBag("res/letters_by_quantity");
        dealLetters();
        playGame();
    }

    /**
     * Initializes players with their specified names.
     * NOTE: Error checking already in InitController. User can only enter between two and four players there.
     * @param namesOfPlayers An array of Strings representing the names of the players.
     * @author Emily Tang 101192604
     */
    public void initializePlayers(String[] namesOfPlayers) {
        for (String name : namesOfPlayers) {
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
    private static void printLegend() {
        System.out.println("+: Triple Word, ~: Double Word, -: Triple Letter, *: Double Letter");
    }

    /**
     * Initializes the LetterBag using the specified file.
     * @param fileName A String representing the name of the file that contains the specific quantity of letters.
     * @throws IOException If an I/O error occurs.
     */
    public static void initializeLetterBag(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line = br.readLine();

        while (line != null) {
            letterBag.addLetter(line.substring(0,1), Integer.parseInt(line.substring(2)));
            line = br.readLine();
        }
    }

    /**
     * Deals seven letters to each player.
     */
    public static void dealLetters() {
        for (Player p : playerList) {
            for (int i = 0; i < 7; i++) {
                p.addLetter(letterBag.getRandomLetter());
            }
        }
    }

    /**
     * Scrabble game logic.
     */
    public static void playGame() {
        Scanner userInput = new Scanner(System.in);
        int playerTurnCounter = 0;
        Player currentPlayer;

        while (true) {
            //cycle through players
            currentPlayer = playerList.get(playerTurnCounter % playerList.size());
            boolean validMove = true;
            //game details
            board.printBoard();
            printLegend();
            System.out.println(currentPlayer.getName() + ": " + currentPlayer.getScore() + " points");
            System.out.println("Your current rack is: ");
            currentPlayer.printRack();
            System.out.println(" ");

            //move details
            System.out.println("What word do you want to play? \"q\" to quit");
            String word = userInput.nextLine();

            if (word.equals("q")) {
                System.exit(0);
            }

            System.out.println("Play word (h)orizontally or (v)ertically?");
            Direction direction = userInput.nextLine().equals("h") ? Direction.HORIZONTAL : Direction.VERTICAL;

            System.out.println("Starting row?");
            int row = Integer.parseInt(userInput.nextLine());

            System.out.println("Starting column?");
            int column = Integer.parseInt(userInput.nextLine());

            ScrabbleMove move = new ScrabbleMove(word, row, column, direction);

            //are all the spaces player wants to use available and does player have the letters necessary
            if (lib.isValidWord(word) && board.checkMoveValidity(move) && (currentPlayer.playWord(word))) {

                //setting board
                if (direction == Direction.VERTICAL) {
                    for (char c : word.toCharArray()) {
                        Game.board.setSquare(c, row, column);
                        row++;
                    }
                } else {
                    for (char c : word.toCharArray()) {
                        Game.board.setSquare(c, row, column);
                        column++;
                    }
                }

                //awarding score
                currentPlayer.setScore(currentPlayer.getScore() + board.calculateMoveScore(move));

                //re-upping letters
                for (int i = 0 ; i < word.length(); i++) {
                    currentPlayer.addLetter(letterBag.getRandomLetter());
                }
            } else { //can't play selected word
                System.out.println("Invalid move");
                validMove = false;
            }
            if (validMove) {
                playerTurnCounter++;
            }
        }
    }
}
