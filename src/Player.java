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

    public String getName() {
        return name;
    }

    public void addLetter(String l) {
        l = l.toUpperCase();
        if (this.availableLetters.containsKey(l)) {
            this.availableLetters.put(l, this.availableLetters.get(l) + 1);
        } else {
            this.availableLetters.put(l, 1);
        }
    }

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

    public void printRack(){
        for (String k : availableLetters.keySet()) {
            for (int i = 0; i < availableLetters.get(k); i++) {
                System.out.print(k + ",");
            }
        }
    }

}
