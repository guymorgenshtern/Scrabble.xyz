public class BoardClick {
    private int[] coords;
    private String letter;

    public BoardClick(int[] coords, String letter) {
        this.coords = coords;
        this.letter = letter;
    }

    public int[] getCoords() {
        return coords;
    }

    public String getLetter() {
        return letter;
    }
}
