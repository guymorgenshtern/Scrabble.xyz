import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ScrabbleGameFrame extends JFrame implements ScrabbleView {
    private BoardPanel boardPanel;
    private HandPanel handPanel;
    private InfoPanel infoPanel;

    private ScrabbleModel model;

    private Board textBoard;

    /**
     * Constructor
     * @param model
     * @throws IOException
     * @author Guy Morgenshtern - 101151430
     */
    public ScrabbleGameFrame(ScrabbleModel model) throws IOException {
        super("Scrabble.xyz");

        model.getViews().add(this);
        this.setFont(new Font("Helvetica", Font.PLAIN, 12));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));
        this.model = model;
        this.infoPanel = new InfoPanel(model);
        this.handPanel = new HandPanel(model);
        this.boardPanel = new BoardPanel(model);
        JButton endTurn = new JButton();
        this.textBoard = model.getBoard();
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
        model.getViews().add(this);

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
