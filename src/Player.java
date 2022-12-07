import java.io.*;
import java.util.ArrayList;

/**
 *  A Player in the game of Scrabble.
 *  @author Guy Morgenshtern 101151430
 */
public class Player implements Serializable {

    /** A String representing the player's name. */
    private final String name;

    /** A HashMap representing the player's hand. Stores available letters and their quantities. */
    private ArrayList<String> hand;

    /** An integer representing the player's score. */
    private int score;

    /**
     * Creates a Player with the specified name. Player starts off with an empty hand and a score of zero.
     * @param name A String representing the name of the Player.
     * @author Guy Morgenshtern 101151430
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    /**
     * @return A String representing the name of the Player.
     * @author Guy Morgenshtern 101151430
     */
    public String getName() {
        return name;
    }

    /**
     * @param l A String representation of the letter to be added to the player's hand.
     * @author Guy Morgenshtern 101151430
     */
    public void addLetter(String l) {
        this.hand.add(l);
    }

    /**
     * Determines if the Player has the letters to make the specified word.
     * @param input A String representation of word that will be checked.
     * @return True, if Player has all letters needed. False, if not.
     * @author Guy Morgenshtern 101151430
     */
    public boolean hasLettersNeededForWord(String input) {
        // iterate through the specified word
        for (char c : input.toCharArray()) {
            // determine if c is in hand, returns 0 if not in hand
            boolean q = hand.contains(String.valueOf(c).toUpperCase());
            if (!q) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the specified letter from the player's hand.
     * @param l A String representation of the letter to be removed from the hand.
     * @author Guy Morgenshtern 101151430
     */
    public void removeLetter(String l) {
        this.hand.remove(l);
    }

    public void removeLetter(Integer i) {
        this.hand.remove(i.intValue());
    }

    /**
     * @return An ArrayList of Strings representing the player's hand.
     * @author Guy Morgenshtern 101151430
     */
    public ArrayList<String> getAvailableLetters() {
        return this.hand;
    }

    /**
     * @return An integer representing the player's score.
     * @author Guy Morgenshtern 101151430
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score to the specified integer.
     * @param score An integer representing the player's score.
     * @author Guy Morgenshtern 101151430
     */
    public void setScore(int score) {
        this.score = score;
    }
}
