import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    private static Board board;
    private static Player p1;
    private static Player p2;
    private static ArrayList<Player> playerList;
    private static LetterBag letterBag;

    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    public static void main (String[] args) throws IOException {
        board = new Board("res/default_board.txt");

        playerList = new ArrayList<>();
        p1 = new Player("Guy");
        p2 = new Player("Alex");
        playerList.add(p1);
        playerList.add(p2);

        letterBag = new LetterBag();

        //board.printBoard();
        initializeLetterBag("res/letters_by_quantity");
        dealLetters();
        playGame();
    }

    private static void printLegend() {
        System.out.println("+: Triple Word, ~: Double Word, -: Triple Letter, *: Double Letter");
    }

    public static void initializeLetterBag(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line = br.readLine();

        while (line != null) {
            letterBag.addLetter(line.substring(0,1), Integer.parseInt(line.substring(2)));
            line = br.readLine();
        }
    }

    public static void dealLetters() {
        for (Player p : playerList) {
            for (int i = 0; i < 7; i++) {
                p.addLetter(letterBag.getRandomLetter());
            }
        }
    }

    // we can probably refactor word, row, column, direction to be a class called ScrabbleMove since it's used so often

    public static void playGame() {
        Scanner userInput = new Scanner(System.in);
        int playerTurnCounter = 0;
        Player currentPlayer;

        while (true) {
            //cycle through players
            currentPlayer = playerList.get(playerTurnCounter % playerList.size());

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

            //are all the spaces player wants to use available and does player have the letters necessary
            if (board.checkMoveValidity(word, row, column, direction) && (currentPlayer.playWord(word))) {

                //setting board
                if (direction == Direction.VERTICAL) {
                    for (char c : word.toCharArray()) {
                        Game.board.setTile(c, row, column);
                        row++;
                    }
                } else {
                    for (char c : word.toCharArray()) {
                        Game.board.setTile(c, row, column);
                        column++;
                    }
                }

                //awarding score
                currentPlayer.setScore(currentPlayer.getScore() + board.calculateMoveScore(word, row, column, direction));

                //re-upping letters
                for (int i = 0 ; i < word.length(); i++) {
                    currentPlayer.addLetter(letterBag.getRandomLetter());
                }
            } else { //can't play selected word
                System.out.println("cant bruh");
            }
            playerTurnCounter++;
        }
    }
}
