import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Creates the JPanel for the top of the program. It includes the players name, score, and an end turn button when
 * the user wants to skip or end turn.
 *
 * @author Alexander Hum 101180821
 */
public class InfoPanel extends JPanel implements ScrabbleView {

    /** JLabel for the current players name */
    private final JLabel playerName;

    /** JLabel for the current players name */
    private final JLabel playerScore;

    private final JButton undoButton;

    private final JButton redoButton;

    /**
     * InfoPanel creates the layout and location for thr buttons and labels.
     * @param scrabbleModel The Game of Scrabble to update
     * @author Alexander Hum 101180821
     */
    public InfoPanel(ScrabbleModel scrabbleModel) {
        super();
        this.setLayout(new GridLayout(1, 7));
        this.setSize(1000,100);

        // creates the labels
        playerName = new JLabel("", SwingConstants.CENTER);
        playerScore = new JLabel();
        JLabel currentNameLabel = new JLabel("Current Player Name:", SwingConstants.RIGHT);
        currentNameLabel.setForeground(Color.BLACK);
        JLabel currentScoreLabel = new JLabel("Score: ", SwingConstants.RIGHT);
        currentScoreLabel.setForeground(Color.BLACK);
        JButton endTurn = new JButton("End Turn");
        // action listener for what happens when the user clicks on the end turn button
        endTurn.addActionListener(e -> {
            scrabbleModel.play(scrabbleModel.getCurrentMove());
        });
        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> scrabbleModel.undo());
        redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> scrabbleModel.redo());

        //adds labels and buttons to the panel
        add(currentNameLabel);
        add(playerName);
        playerName.setForeground(Color.BLACK);
        this.add(currentScoreLabel);
        this.add(playerScore);
        playerScore.setForeground(Color.BLACK);
        this.add(endTurn);
        this.add(undoButton);
        this.add(redoButton);

        // set background colour
        setBackground(new Color(253, 239, 117));

        this.setVisible(true);
    }

    /**
     * updates the players name and score.
     * @param event for the ScrabbleEvent
     * @author Alexander Hum 101180821
     */
    @Override
    public void update(ScrabbleEvent event) {
        Player currentPlayer = event.getCurrentPlayer();
        playerName.setText(currentPlayer.getName()); // sets text for players name
        playerScore.setText(currentPlayer.getScore() + ""); // sets text for the players score

        Stack<UndoMove> undoStack = event.getScrabbleModel().getUndoStack();
        boolean setEnabled;
        if ((undoStack.size() > 0 && undoStack.peek().getMove().getPlayer() instanceof BotPlayer)
            || undoStack.size() == 0) {
            setEnabled = false;
        } else {
            setEnabled = true;
        }
        undoButton.setEnabled(setEnabled);
        redoButton.setEnabled(setEnabled);
    }
}
