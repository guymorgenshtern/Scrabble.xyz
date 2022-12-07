import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Letter Bag that will hold letters and the quantity
 */
public class LetterBag implements Serializable {

    /** A HashMap for the letter map */
    private HashMap<String, Integer> letterBag;

    /** size of the bag */
    private int size;

    /**
     * Creates a LetterBag.
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public LetterBag() {
        size = 0;
        letterBag = new HashMap<>();
    }

    /**
     * Adds a letter to letter map
     * @param letter a string representing a letter
     * @param quantity an integer representing the quantity of letters
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public void addLetter(String letter, int quantity) {
        letterBag.put(letter, quantity);
        this.size += quantity;
    }

    /**
     * removes a letter from the letter map
     * @param letter a string representing a letter
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public void removeLetter(String letter) {
        letter = letter.toUpperCase();
        if (this.size > 0) {
            if (letterBag.get(letter) > 1) {
                letterBag.put(letter, letterBag.get(letter) - 1);
            } else {
                letterBag.remove(letter);
            }
            this.size--;
        }
    }

    /**
     * gets a random letter from the letter map
     * @return the letter as a string
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public String getRandomLetter() {
         int random = (int) ((Math.random() * (this.size)));
         String[] availableLetters = new String[this.size];
         int count = 0;
         for (String k : letterBag.keySet()) {
             for (int i = 0; i < letterBag.get(k); i++) {
                 if (count == random) {
                     removeLetter(k);
                     return k;
                 }
                 count++;
             }
         }
         return null;
    }

}