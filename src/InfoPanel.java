import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Creates the JPanel for the top of the program. It includes the players name, score, and an end turn button when
 * the user wants to skip or end turn.
 * @author Alexander Hum 101180821. Edited by Emily Tang 101192604 and Guy Morgenshtern 101151430.
 */
public class InfoPanel extends JPanel implements ScrabbleView {

    /** A JLabel for the current player's name. */
    private final JLabel playerName;

    /** A JLabel for the current player's name. */
    private final JLabel playerScore;

    /** A JButton to undo a ScrabbleMove. */
    private final JButton undoButton;

    /** A JButton to redo a ScrabbleMove. */
    private final JButton redoButton;

    /**
     * Creates an InfoPanel to display the name and the score of the current player. InfoPanel also houses buttons to
     * end the current player's turn, and to allow for undoing and redoing of ScrabbleMoves.
     * @param scrabbleModel The ScrabbleModel to update.
     * @author Alexander Hum 101180821. Edited by Emily Tang 101192604 and Guy Morgenshtern 101151430.
     */
    public InfoPanel(ScrabbleModel scrabbleModel) {
        super();
        setLayout(new GridLayout(1, 7));
        setSize(900,100);

        // creates the labels
        playerName = new JLabel("", SwingConstants.CENTER);
        playerScore = new JLabel();
        JLabel currentNameLabel = new JLabel("Current Player Name:", SwingConstants.RIGHT);
        currentNameLabel.setForeground(Color.BLACK);
        JLabel currentScoreLabel = new JLabel("Score: ", SwingConstants.RIGHT);
        currentScoreLabel.setForeground(Color.BLACK);

        JButton endTurn = new JButton("End Turn");
        // ActionListener for what happens when the user clicks on the end turn button
        endTurn.addActionListener(e -> {
            scrabbleModel.play(scrabbleModel.getCurrentMove());
        });

        undoButton = new JButton("Undo");
        // ActionListener for what happens when the user clicks on the undo button
        undoButton.addActionListener(e -> scrabbleModel.undo());

        redoButton = new JButton("Redo");
        // ActionListener for what happens when the user clicks on the redo button
        redoButton.addActionListener(e -> scrabbleModel.redo());

        //adds labels and buttons to the panel
        add(currentNameLabel);
        add(playerName);
        playerName.setForeground(Color.BLACK);
        add(currentScoreLabel);
        add(playerScore);
        playerScore.setForeground(Color.BLACK);
        add(endTurn);
        add(undoButton);
        add(redoButton);

        // set background colour
        setBackground(new Color(253, 239, 117));

        setVisible(true);
    }

    /**
     * Updates the current player's name and score. Disables/enables the undo and redo buttons.
     * @param event A ScrabbleEvent that has occurred.
     * @author Alexander Hum 101180821. Edited by Emily Tang 101192604.
     */
    @Override
    public void update(ScrabbleEvent event) {
        Player currentPlayer = event.getCurrentPlayer();
        playerName.setText(currentPlayer.getName()); // displays the current player's name
        playerScore.setText(currentPlayer.getScore() + ""); // displays the current player's score

        // disable/enable the undo and redo buttons based on the amount of ScrabbleMoves in the stack
        // do not allow the BotPlayer to undo moves
        Stack<UndoMove> undoStack = event.getScrabbleModel().getUndoStack();
        boolean setEnabled;
        if (undoStack == null || ((undoStack.size() > 0 && undoStack.peek().getMove().getPlayer() instanceof BotPlayer)
            || undoStack.size() == 0)) {
            setEnabled = false;
        } else {
            setEnabled = true;
        }
        undoButton.setEnabled(setEnabled);

        Stack<UndoMove> redoStack = event.getScrabbleModel().getRedoStack();
        redoButton.setEnabled(redoStack != null && (redoStack.size() > 0));
    }

}
