import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ScrabbleGameFrame extends JFrame implements ScrabbleView {
    private ArrayList<ScrabbleView> panels;
    private ArrayList<Integer> onBoard;
    private BoardPanel boardPanel;
    private HandPanel handPanel;
    private PlayerComparator playerComparator;
    private InfoPanel infoPanel;

    private ScrabbleModel model;

    private Board textBoard;

    public ScrabbleGameFrame(ScrabbleModel model) throws IOException {
        super("Scrabble.xyz");

        this.onBoard = new ArrayList<>();
        this.playerComparator = new PlayerComparator();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));
        this.panels = new ArrayList<>();
        this.model = model;
        this.infoPanel = new InfoPanel(model);
        infoPanel.setBackground(new Color(187, 252, 249));
        this.handPanel = new HandPanel(model);
        this.boardPanel = new BoardPanel(model);
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
        model.getViews().add(this);

        this.setSize(850, 750);
        this.setVisible(true);

        // initialize the model
        new InitController(model);
    }

    public void attachTextBoard(Board b) {
        this.textBoard = b;
        boardPanel.attachTextBoard(b);
    }

    public Board getTextBoard() {
        return textBoard;
    }

    @Override
    public void update(ScrabbleEvent event) {
        boardPanel.update(event);
        handPanel.update(event);
        infoPanel.update(event);
        if(event.getGameStatus() == ScrabbleModel.GameStatus.FINISHED) {
            ArrayList<Player> listPlayers = event.getScrabbleModel().getPlayerList();
            listPlayers.sort(playerComparator);

            String message = "Congratulations " + listPlayers.get(listPlayers.size() - 1).getName() + "!\nLeaderboard";
            for (int i=listPlayers.size() - 1; i >= 0; i--) {
                message += "\n" + listPlayers.get(i).getName() + " " + listPlayers.get(i).getScore();
            }
            JOptionPane optionPane= new JOptionPane(message);

            final JDialog dialog = optionPane.createDialog(null, "Game Over!");
            dialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
            dialog.setVisible(true);
        }
    }
}
