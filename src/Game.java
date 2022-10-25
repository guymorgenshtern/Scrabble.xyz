import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private static Board board;
    private static Player p1;
    private static Player p2;
    private static ArrayList<Player> playerList;
    private static LetterBag letterBag;

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

    public static void playGame() {
        Scanner userInput = new Scanner(System.in);
        int playerTurnCounter = 0;
        Player currentPlayer;
        while (true) {
            currentPlayer = playerList.get(playerTurnCounter % playerList.size());
            board.printBoard();
            printLegend();
            System.out.println(currentPlayer.getName() + ":");
            System.out.println("Your current rack is: ");
            currentPlayer.printRack();
            System.out.println(" ");
            System.out.println("What word do you want to play? \"q\" to quit");
            String word = userInput.nextLine();

            if (word.equals("q")) {
                System.exit(0);
            }

            System.out.println("Play word (h)orizontally or (v)ertically?");
            String direction = userInput.nextLine();

            System.out.println("Starting row?");
            int row = Integer.parseInt(userInput.nextLine());

            System.out.println("Starting column?");
            int column = Integer.parseInt(userInput.nextLine());

            if (currentPlayer.playWord(word)) {
                if (direction.equals("v")) {
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
                for (int i = 0 ; i < word.length(); i++) {
                    currentPlayer.addLetter(letterBag.getRandomLetter());
                }
            } else {
                System.out.println("cant bruh");
            }
            playerTurnCounter++;
        }
    }
}
