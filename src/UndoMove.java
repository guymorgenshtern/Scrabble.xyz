import java.util.ArrayList;

public class UndoMove {
    private ArrayList<Integer> indexOfUsedLetters;
    private int score;
    private ScrabbleMove move;

    public UndoMove(ScrabbleMove move, int score, ArrayList<Integer> indexesOfUsedLetters) {
        this.move = move;
        this.score = score;
        this.indexOfUsedLetters = indexesOfUsedLetters;
    }

    public ArrayList<Integer> getIndexOfUsedLetters() {
        return indexOfUsedLetters;
    }

    public int getScore() {
        return score;
    }

    public ScrabbleMove getMove() {
        return move;
    }

}
