import java.util.HashMap;

public class LetterBag  {
    //created a HashMap object called m
    private HashMap<String, Integer> letterMap = new HashMap<>();
    private int size = 0;
    public LetterBag() {

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
         int count = 0;
         for (String k : letterMap.keySet()) {
             count += letterMap.get(k);
             if (count == random) {
                 removeLetter(k);
                 return k;
             }
         }
         return null;
    }

    public int bagSize() {
        return size;
    }
}