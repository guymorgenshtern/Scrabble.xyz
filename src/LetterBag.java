import java.io.IOException;
import java.util.HashMap;

/**
 * Letter Bag that will hold letters and the quantity
 */
public class LetterBag  {

    /** A HashMap to store the quantity of each letter. */
    private final HashMap<String, Integer> letterBag;

    /** An integer to represent the number of tiles in the LetterBag. */
    private int size;

    /**
     * Creates an empty LetterBag.
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public LetterBag() {
        size = 0;
        letterBag = new HashMap<>();
    }

    /**
     * Adds the specified letter to the LetterBag.
     * @param letter A String representing the letter to add.
     * @param quantity An integer representing the quantity of the specified letter to add.
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public void addLetter(String letter, int quantity) {
        letterBag.put(letter, quantity);
        size += quantity;
    }

    /**
     * Removes one tile of the specified letter from the LetterBag.
     * @param letter A String representing the letter to remove.
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public void removeLetter(String letter) {
        letter = letter.toUpperCase();
        if (size > 0) {
            if (letterBag.get(letter) > 1) {
                letterBag.put(letter, letterBag.get(letter) - 1);
            } else {
                letterBag.remove(letter);
            }
            size--;
        }
    }

    /**
     * @return A String representing a random letter from the LetterBag.
     * @author Alexander Hum 101180821. Edited by Guy Morgenshtern 101151430.
     */
    public String getRandomLetter() {
        int random = (int) ((Math.random() * (this.size)));
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