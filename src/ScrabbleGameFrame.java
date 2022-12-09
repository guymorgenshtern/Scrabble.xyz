import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ScrabbleGameFrame extends JFrame implements ScrabbleView {
    private BoardPanel boardPanel;
    private HandPanel handPanel;
    private InfoPanel infoPanel;
    private ScorePanel scorePanel;

    private ScrabbleModel model;

    private Board textBoard;

    /**
     * Constructor
     * @param model represents the Scrabble Game
     * @throws IOException Thrown when an error occurs
     * @author Guy Morgenshtern - 101151430
     */
    public ScrabbleGameFrame(ScrabbleModel model) throws IOException {
        super("Scrabble.xyz");

        model.getViews().add(this);
        this.setFont(new Font("Helvetica", Font.PLAIN, 12));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.model = model;
        this.infoPanel = new InfoPanel(model);
        this.handPanel = new HandPanel(model);
        this.boardPanel = new BoardPanel(model);
        this.scorePanel = new ScorePanel(model);
        JButton endTurn = new JButton();
        this.textBoard = model.getBoard();
        endTurn.setText("End Turn");
        endTurn.addActionListener(e -> {
            model.play(model.getCurrentMove());
        });
        /*
        JLabel score = new JLabel();
        score.setText("Score:    \n");
        score.setSize(1000,100);
         */

        this.add(infoPanel);
        this.add(boardPanel);
        this.add(scorePanel);
        this.add(handPanel);


        JTextField saveScrabble = new JTextField(15);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem save = new JMenuItem("Save");

        JPanel saveScrabblePanel = new JPanel();
        saveScrabblePanel.add(saveScrabble);
        save.addActionListener(e-> {
            int result = JOptionPane.showConfirmDialog(null, saveScrabblePanel,
                    "Choose file to save to", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    model.saveScrabble(saveScrabble.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        menuBar.add(menu);
        menu.add(save);
        this.setJMenuBar(menuBar);

        this.setSize(1000, 800); // Expanded to enable text to show on button
        this.setVisible(true);

        new InitController(model); // initialize the model
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

    public static void main (String[] args) throws IOException {
        new ScrabbleGameFrame(new ScrabbleModel());
    }
}
