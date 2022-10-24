import java.util.Arrays;
import java.util.HashMap;

public class LetterBag  {
    //created a HashMap object called m
    private HashMap<String, Integer> letterMap;
    private int size;

    public LetterBag() {
        size = 0;
        letterMap = new HashMap<>();
    }

    public void addLetter(String letter, int quantity) {
        letterMap.put(letter, quantity);
        this.size += quantity;
    }
    // maybe change return type depending on how we do game class
    public void removeLetter(String letter) {
        if (size > 0) {
            if (letterMap.get(letter) > 1) {
                letterMap.put(letter, letterMap.get(letter) - 1);
                this.size--;
            } else {
                letterMap.remove(letter);
            }
        }
    }

    public String getRandomLetter() {
         int random = (int) ((Math.random() * (this.size)));
         String[] availableLetters = new String[this.size];
         int count = 0;
         for (String k : letterMap.keySet()) {
             for (int i = 0; i < letterMap.get(k); i++) {
                 availableLetters[count] = k;
                 count++;

             }
         }
         removeLetter(availableLetters[random]);
         return availableLetters[random];
    }

    public int bagSize() {
        return size;
    }
}