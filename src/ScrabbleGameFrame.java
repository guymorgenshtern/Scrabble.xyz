import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ScrabbleGameFrame extends JFrame implements ScrabbleView {

    /** A JPanel to display the board. */
    private final BoardPanel boardPanel;

    /** A JPanel to display the current player's hand. */
    private final HandPanel handPanel;

    /** A JPanel to store the game information. */
    private final InfoPanel infoPanel;

    /**
     * Initializes a ScrabbleGameFrame to display the game of Scrabble.
     * @param model A ScrabbleModel to represent.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleGameFrame(ScrabbleModel model) throws IOException {
        super("Scrabble.xyz");
        setFont(new Font("Helvetica", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        model.getViews().add(this);

        infoPanel = new InfoPanel(model);
        handPanel = new HandPanel(model);
        boardPanel = new BoardPanel(model);
        JButton endTurn = new JButton();
        endTurn.setText("End Turn");
        endTurn.addActionListener(e -> {
            model.play(model.getCurrentMove());
        });
        JLabel score = new JLabel();
        score.setText("Score:    \n");
        score.setSize(350,100);

        this.add(boardPanel, BorderLayout.CENTER);
        this.add(handPanel, BorderLayout.SOUTH);
        this.add(infoPanel, BorderLayout.NORTH);

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
        this.setJMenuBar(menuBar);

        setSize(1000, 800); // expanded to enable text to show on button
        setVisible(true);

        if (!model.isLoadGame()) {
            new InitController(model); // initialize the model
        }
    }

    /**
     * Update ScrabbleGameFrame and all of its panels.
     * @param event A ScrabbleEvent to update.
     * @author Guy Morgenshtern 101151430
     */
    @Override
    public void update(ScrabbleEvent event) {

        boardPanel.update(event);
        handPanel.update(event);
        infoPanel.update(event);

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
