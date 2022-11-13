import com.zetcode.Library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * A word can either be horizontally, or vertically placed onto the board.
     */
    public enum Direction { HORIZONTAL, VERTICAL }

    /**
     * Runs a text-based playable version of Scrabble.
     * @throws IOException If an I/O error occurs.
     */
    public Game() throws IOException {
        this.board = new Board("res/default_board.txt");
        this.lib = new Library();

        this.playerList = new ArrayList<>();
        initializePlayers();


        this.letterBag = new LetterBag();

        initializeLetterBag("res/letters_by_quantity");
        dealLetters();
        playGame();
    }

    /**
     * initializes players based on input
     */
    public void initializePlayers() {
        Scanner userInput = new Scanner(System.in);

        int numPlayers = 0;
        while (!(numPlayers > 1 && numPlayers <= 4)) {
            System.out.println("How many players are playing?");
            numPlayers = Integer.parseInt(userInput.nextLine());
        }


        for (int i = 0; i < numPlayers; i++) {
            System.out.printf("Player %d's name: ", i + 1);
            String name = userInput.nextLine();
            playerList.add(new Player(name));
            System.out.println(" ");
        }
    }

    /**
     * Prints a legend of the ASCII symbols used to represent the text-based board.
     */
    private void printLegend() {
        System.out.println("+: Triple Word, ~: Double Word, -: Triple Letter, *: Double Letter");
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
            letterBag.addLetter(line.substring(0,1), Integer.parseInt(line.substring(2)));
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
    }

    private String findFullWord(Direction dir, int move[][]) {
        //this assumes the move array is sorted from first letter->last, we should make sure this is always the case
        int x = move[0][0];
        int y = move[0][1];
        String word = "";

        if(dir == Direction.HORIZONTAL) {
            int startingPosition = move[0][0];
            int walkingPointer = startingPosition;
            while (this.board.getTileOnBoard(walkingPointer,y).getLetter() != ' ') {
                word += String.valueOf(this.board.getTileOnBoard(x,y).getLetter());
                walkingPointer++;
            }
            walkingPointer = startingPosition + 1;
            while (this.board.getTileOnBoard(walkingPointer,y).getLetter() != ' ') {
                word = this.board.getTileOnBoard(x,y).getLetter() + word;
                walkingPointer++;
            }
        } else if(dir == Direction.VERTICAL) {
            int startingPosition = move[0][1];
            int walkingPointer = startingPosition;
            while (this.board.getTileOnBoard(x,walkingPointer).getLetter() != ' ') {
                word += String.valueOf(this.board.getTileOnBoard(x,y).getLetter());
                walkingPointer++;
            }
            walkingPointer = startingPosition + 1;
            while (this.board.getTileOnBoard(x,walkingPointer).getLetter() != ' ') {
                word = this.board.getTileOnBoard(x,y).getLetter() + word;
                walkingPointer++;
            }
        }
        return word;
    }

    public void play(int move[][]) {
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

        String finalWord = findFullWord(dir, move);


    }
    /**
     * Scrabble game logic.
     */
    public void playGame() {

        initializeBoard();

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
            if (board.checkMoveValidity(move) && (currentPlayer.playWord(word))) {

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
