import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    /**
     * Compares the scores of the specified players.
     * @param p1 The first Player to be compared.
     * @param p2 The second Player to be compared.
     * @return An integer to determine if the player's scores are equal or not.
     * @author Guy Morgenshtern 101151430
     */
    @Override
    public int compare(Player p1, Player p2) {
        return Integer.compare(p1.getScore(), p2.getScore());
    }

}

