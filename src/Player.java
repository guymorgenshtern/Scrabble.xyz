import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private HashMap<String, Integer> availableLetters;
    private int score;
    private String name;

    public Player(String name) {
        this.score = 0;
        this.availableLetters = new HashMap<>();
        this.name = name;
    }

    public void addLetter(String l) {
        if (this.availableLetters.containsKey(l)) {
            this.availableLetters.put(l, this.availableLetters.get(l) + 1);
        } else {
            this.availableLetters.put(l, 1);
        }
    }

    public void drawLetters(Letter letter) {

    }

    private boolean hasLettersNeededForWord(String input) {
        char[] inputAsArray = input.toCharArray();

        for (char c : inputAsArray) {
            int q = availableLetters.getOrDefault(c + "", 0);
            if (q == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean playWord(String input) {
        if (hasLettersNeededForWord(input)) {
            for (char l : input.toCharArray()) {
                if (availableLetters.get(l+"") - 1 == 0) {
                    availableLetters.remove(l+"");
                } else {
                    availableLetters.put(l + " ", availableLetters.get(l+"") - 1);
                }
            }
            return true;
        } else {
            return false;
        }

    }


}
