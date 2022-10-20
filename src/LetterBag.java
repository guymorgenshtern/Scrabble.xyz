import java.util.HashMap;

public class LetterBag extends Letter {
    //created a HashMap object called m
    private HashMap<Letter, Integer> letterMap = new HashMap<>();
    private int size = 0;
    public LetterBag() {

    }
    public void addLetter(Letter letter, int quantity) {
        letterMap.put(letter, quantity);
    }
    // maybe change return type depending on how we do game class
    public void removeLetter(Letter letter, int quantity) {
        if(letterMap.get(letter) > 1) {
            letterMap.put(letter, letterMap.get(letter) - 1);
        }
        else {
            System.out.println("There are no letters in the bag\n");
        }
    }

    public int bagSize() {
        for(Letter k : letterMap.keySet()) {
            size++;
        }
        return size;
    }
}