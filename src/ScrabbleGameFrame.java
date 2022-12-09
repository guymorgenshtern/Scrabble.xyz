import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A GUI representation of a game of Scrabble.
 * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604 and Alexander Hum 101180821.
 */
public class ScrabbleGameFrame extends JFrame implements ScrabbleView {

    /** A JPanel to store the game information. */
    private final InfoPanel infoPanel;

    /** A JPanel to display the board. */
    private final BoardPanel boardPanel;

    /** A JPanel to display the current player's hand. */
    private final HandPanel handPanel;

    /** A JPanel to display the players and their current scores. */
    private final ScorePanel scorePanel;

    /**
     * Initializes a ScrabbleGameFrame to display the game of Scrabble.
     * @param model A ScrabbleModel to represent.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleGameFrame(ScrabbleModel model) throws IOException {
        // initialize the JFrame with a vertical BoxLayout
        super("Scrabble.xyz");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setFont(new Font("Helvetica", Font.PLAIN, 12));

        // ScrabbleGameFrame waits for updates from the model
        model.getViews().add(this);

        // create the JPanels that will make up the ScrabbleGameFrame
        infoPanel = new InfoPanel(model);
        handPanel = new HandPanel(model);
        boardPanel = new BoardPanel(model);
        scorePanel = new ScorePanel();

        // set the maximum size of the panels
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        handPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        scorePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // add the panels to the JFrame
        add(infoPanel);
        add(boardPanel);
        add(handPanel);
        add(scorePanel);

        JFileChooser fc = new JFileChooser();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem save = new JMenuItem("Save");

        save.addActionListener(e-> {
            fc.setDialogTitle("Save Scrabble.xyz (files are automatically given 'sxyz.ser' suffix)");
            int saveFileResult = fc.showSaveDialog(this);

            if (saveFileResult == JFileChooser.APPROVE_OPTION) {
                try {
                    model.setLoadGame(true);
                    model.saveScrabble(fc.getSelectedFile().getPath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        menuBar.add(menu);
        menu.add(save);
        setJMenuBar(menuBar);

        if (!model.isLoadGame()) {
            new InitController(model); // initialize the model
        }

        // finish initializing the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800); // expanded to enable text to show on button
        setVisible(true);
    }

    /**
     * Update ScrabbleGameFrame and all of its panels.
     * @param event A ScrabbleEvent to update.
     * @author Guy Morgenshtern 101151430 and Alexander Hum 101180821
     */
    @Override
    public void update(ScrabbleEvent event) {
        // update the JPanels
        infoPanel.update(event);
        boardPanel.update(event);
        handPanel.update(event);
        scorePanel.update(event);

        if (event.getGameStatus() == ScrabbleModel.GameStatus.FINISHED || event.getGameStatus() == ScrabbleModel.GameStatus.TIE) {
            ArrayList<Player> listPlayers = event.getScrabbleModel().getPlayerList();

            String message;
            if (event.getGameStatus() == ScrabbleModel.GameStatus.TIE) {
                message = "Tie!";
            } else {
                message = "Congratulations " + listPlayers.get(listPlayers.size() - 1).getName() + "!\n\nLeaderboard";
            }

            for (int i = listPlayers.size() - 1; i >= 0; i--) {
                message += "\n" + listPlayers.get(i).getName() + " " + listPlayers.get(i).getScore();
            }
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

}
