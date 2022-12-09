import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  A Player in the game of Scrabble.
 *  @author Guy Morgenshtern 101151430
 */
public class Player implements Serializable {

    /** A String representing the player's name. */
    private final String name;

    /** A HashMap representing the player's hand. Stores available letters and their quantities. */
    protected final ArrayList<String> hand;

    /** An integer representing the player's score. */
    private int score;

    /**
     * Creates a Player with the specified name. Player starts off with an empty hand and a score of zero.
     * @param name A String representing the name of the Player.
     * @author Guy Morgenshtern 101151430
     */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
        score = 0;
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
        hand.add(l);
    }

    /**
     * Determines if the Player has the letters to make the specified word.
     * @param input A String representation of word that will be checked.
     * @return True, if Player has all letters needed. False, if not.
     * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
     */
    public boolean hasLettersNeededForWord(String input) {
        // create a HashMap to store all the occurrences of each letter in the Player's hand
        HashMap<String, Integer> letterOccurrencesInHand = new HashMap<>();
        for (String s : hand) {
            if (letterOccurrencesInHand.containsKey(s)) {
                letterOccurrencesInHand.put(s, letterOccurrencesInHand.get(s) + 1);
            } else {
                letterOccurrencesInHand.put(s, 1);
            }
        }

        // create a HashMap to store all the occurrences of each letter in the inputted String
        HashMap<String, Integer> letterOccurrencesInInput = new HashMap<>();
        for (char c : input.toUpperCase().toCharArray()) {
            String s = c + "";
            if (letterOccurrencesInInput.containsKey(s)) {
                letterOccurrencesInInput.put(s, letterOccurrencesInInput.get(s) + 1);
            } else {
                letterOccurrencesInInput.put(s, 1);
            }
        }

        // determine if the player has enough of each letter to create the inputted String
        for (String key : letterOccurrencesInInput.keySet()) {
            if (!letterOccurrencesInHand.containsKey(key)
                || (letterOccurrencesInInput.get(key) > letterOccurrencesInHand.get(key))) { // cannot use more letters than you have
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
        hand.remove(l);
    }

    /**
     * Removes the letter at the specified index from the player's hand.
     * @param i An integer representing the index of the letter to be removed from the player's hand.
     */
    public void removeLetter(int i) {
        hand.remove(i);
    }

    /**
     * @return An ArrayList of Strings representing the player's hand.
     * @author Guy Morgenshtern 101151430
     */
    public ArrayList<String> getAvailableLetters() {
        return hand;
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
