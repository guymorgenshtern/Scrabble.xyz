import java.util.ArrayList;
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

    public void removeLetter(String l) {
        int amount = availableLetters.getOrDefault(l, -1);
        if (amount == 1) {
            availableLetters.remove(l);
        } else if (amount > 1){
            availableLetters.put(l, availableLetters.get(l) - 1);
        }
    }


    public ArrayList<String> getAvailableLetters() {
        ArrayList<String> letters = new ArrayList<>();
        for (String s : availableLetters.keySet()) {
            for (int i = 0; i < availableLetters.get(s); i++) {
                letters.add(s);
            }
        }
        return letters;
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
