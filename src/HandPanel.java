import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * HandPanel will display the current player's hand.
 * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
 */
public class HandPanel extends JPanel implements ScrabbleView {

    /** An array of JButtons to display the current player's hand. */
    private final JButton [] buttons;

    /** A ScrabbleModel to update the "look" of. */
    private final ScrabbleModel scrabbleModel;

    /**
     * Initializes the HandPanel.
     * @param scrabbleModel A ScrabbleModel to update the "look" of.
     * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
     */
    public HandPanel(ScrabbleModel scrabbleModel){
        super();
        setLayout(new GridLayout(1, 7));
        setSize(900,100);

        this.scrabbleModel = scrabbleModel;

        buttons = new JButton[7];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setActionCommand("" + i);
            int letter = i;
            buttons[i].addActionListener(e -> {
                scrabbleModel.setSelectedLetter(buttons[letter].getText());
                scrabbleModel.getUsedLetters().add(letter);
                updateVisibility();
            });
            add(buttons[i]);
        }
        setVisible(true);
    }

    /**
     * Sets the JButtons to display the specified player's hand.
     * @param p A Player to display the hand of.
     * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
     */
    private void setHandForTurn(Player p) {
        ArrayList<String> hand = p.getAvailableLetters();
        for (int i = 0; i < buttons.length; i++) {
            if (i > hand.size() - 1) {
                buttons[i].setEnabled(false);
            } else {
                buttons[i].setText(hand.get(i));
            }
        }
    }

    /**
     * Updates the visibility of the JButton.
     * @author Guy Morgenshtern 101151430
     */
    private void updateVisibility() {
        for (JButton b : buttons) {
            b.setEnabled(true);
        }
        for (Integer i : scrabbleModel.getUsedLetters()) {
            buttons[i].setEnabled(false);
        }
    }

    /**
     * Updates the "look" of the HandPanel.
     * @param event A ScrabbleEvent to update.
     * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
     */
    @Override
    public void update(ScrabbleEvent event) {
        // update the HandPanel if the current player is not a BotPlayer
        Player currentPlayer = event.getCurrentPlayer();
        if (!(currentPlayer instanceof BotPlayer)) {
            setHandForTurn(currentPlayer);
        }
        updateVisibility();
    }

}
