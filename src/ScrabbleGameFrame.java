import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScrabbleGameFrame extends JFrame implements ScrabbleView {
    private ArrayList<ScrabbleView> panels;
    private ArrayList<Integer> onBoard;
    private BoardPanel boardPanel;
    private HandPanel handPanel;
    private InfoPanel infoPanel;

    private ScrabbleModel model;

    private Board textBoard;
    public ScrabbleGameFrame(ScrabbleModel model) {
        super("Scrabble.xyz");

        this.onBoard = new ArrayList<>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));
        this.panels = new ArrayList<>();
        this.model = model;
        this.infoPanel = new InfoPanel(model);
        infoPanel.setBackground(new Color(187, 252, 249));
        this.handPanel = new HandPanel(model);
        this.boardPanel = new BoardPanel(model);

        this.add(boardPanel, BorderLayout.CENTER);
        this.add(handPanel, BorderLayout.SOUTH);
        this.add(infoPanel, BorderLayout.NORTH);
        model.getViews().add(this);

        this.setSize(850, 750);
        this.setVisible(true);
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
            String message = "Congratulations " + listPlayers.get(0).getName() + "!\nLeaderboard";
            for(int i=0; i < listPlayers.size(); i++) {
                message += "\n" + listPlayers.get(i).getName() + " " + listPlayers.get(i).getScore();
            }
            JOptionPane.showMessageDialog(this, message);
        }
    }
}
