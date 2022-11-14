import java.util.HashMap;

/**
 *  Class Player for every play that enters the game
 *  @author Guy Morgenshtern 101151430
 */
public class Player {
    /** A hash map for the available letters */
    private HashMap<String, Integer> availableLetters;
    /** A score for the player */
    private int score;
    /** A name for the player */
    private String name;

    /**
     * Creates player with a name
     * @param name String representing the name of the player
     * @author Guy Morgenshtern 101151430
     */
    public Player(String name) {
        this.score = 0;
        this.availableLetters = new HashMap<>();
        this.name = name;
    }

    /**
     * @return A HashMap representing the letters the Player has.
     * @author Emily Tang 101192604
     */
    public HashMap<String, Integer> getAvailableLetters() {
        return availableLetters;
    }

    /**
     * @return the name in a string
     * @author Guy Morgenshtern 101151430
     */
    public String getName() {
        return name;
    }
    /**
     * @param l string representation of the latter
     * @author Guy Morgenshtern 101151430
     */
    public void addLetter(String l) {
        l = l.toUpperCase();
        if (this.availableLetters.containsKey(l)) {
            this.availableLetters.put(l, this.availableLetters.get(l) + 1);
        } else {
            this.availableLetters.put(l, 1);
        }
    }
    /**
     * checks if the input word from the user has all the letters to make that word
     * @param input string representation of word that will be checked
     * @return true if the user has all the letters needed
     * @author Guy Morgenshtern 101151430
     */
    private boolean hasLettersNeededForWord(String input) {
        char[] inputAsArray = input.toCharArray();

        for (char c : inputAsArray) {
            int q = availableLetters.getOrDefault(String.valueOf(c).toUpperCase(), 0);
            if (q == 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * checks if the player can place the word on the board properly
     * @param input string representation of inputted word
     * @return true if the
     * @author Guy Morgenshtern 101151430
     */
    public boolean playWord(String input) {
        if (hasLettersNeededForWord(input)) {
            for (char l : input.toCharArray()) {
                String c = String.valueOf(l).toUpperCase();
                if (availableLetters.get(c) - 1 == 0) {
                    availableLetters.remove(c);
                } else {
                    availableLetters.put(c, availableLetters.get(c) - 1);
                }
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * prints the players current letters that they have
     * @author Guy Morgenshtern 101151430
     */
    public void printRack(){
        for (String k : availableLetters.keySet()) {
            for (int i = 0; i < availableLetters.get(k); i++) {
                System.out.print(k + ",");
            }
        }
    }

    /**
     * @return the players score
     * @author Guy Morgenshtern 101151430
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score integer for the score
     * @author Guy Morgenshtern 101151430
     */
    public void setScore(int score) {
        this.score = score;
    }
}
