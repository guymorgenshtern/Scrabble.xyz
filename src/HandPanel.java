import javax.swing.*;
import java.awt.*;

/**
 * HandPanel will display the current player's hand.
 */
public class HandPanel extends JPanel implements ScrabbleView {

    /** An array of JButtons to display the current player's hand. */
    private final JButton [] buttons;

    /** A ScrabbleModel to update the "look" of. */
    private final ScrabbleModel scrabbleModel;

    /**
     * Initializes the HandPanel.
     * @param scrabbleModel A ScrabbleModel to update the "look" of.
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
     */
    private void setHandForTurn(Player p) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(p.getAvailableLetters().get(i));
            if (i > p.getAvailableLetters().size() - 1) {
                buttons[i].setEnabled(false);
            }
        }
    }

    /**
     * Updates the visibility of the JButton.
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
     */
    @Override
    public void update(ScrabbleEvent event) {
        setHandForTurn(event.getCurrentPlayer());
        updateVisibility();
    }

}
