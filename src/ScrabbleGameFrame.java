import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScrabbleGameFrame extends JFrame implements ScrabbleView {
    private ArrayList<ScrabbleView> panels;
    private ArrayList<Integer> onBoard;
    private BoardPanel boardPanel;
    private HandPanel handPanel;

    private ScrabbleModel model;

    private Board textBoard;
    public ScrabbleGameFrame(ScrabbleModel model) {
        super("Scrabble.xyz");

        this.onBoard = new ArrayList<>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));
        this.panels = new ArrayList<>();
        this.model = model;
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

//        panels.add(handPanel);
//        panels.add(boardPanel);




        this.add(boardPanel, BorderLayout.CENTER);
        this.add(handPanel, BorderLayout.SOUTH);
        this.add(endTurn, BorderLayout.NORTH);
        this.add(score, BorderLayout.LINE_END);
        model.getViews().add(this);


        //TicTacToeController controller = new TicTacToeController(model, this);

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
        System.out.println("test");
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
