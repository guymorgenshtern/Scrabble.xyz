import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements ScrabbleView {
    private JLabel playerName, playerScore, currentNameLabel, currentScoreLabel;
    private JButton endTurn;

    private ScrabbleModel scrabbleModel;

    public InfoPanel(ScrabbleModel scrabbleModel) {
        super();
        this.scrabbleModel = scrabbleModel;

        GridLayout horz = new GridLayout(1, 3);
        this.setLayout(horz);
        this.setSize(900,100);

        playerName = new JLabel("", SwingConstants.CENTER);
        playerScore = new JLabel();
        currentNameLabel = new JLabel("Current Player Name:", SwingConstants.RIGHT);
        currentScoreLabel = new JLabel("Score: ", SwingConstants.RIGHT);
        endTurn = new JButton("End Turn");
        endTurn.addActionListener(e -> {
            scrabbleModel.play(scrabbleModel.getCurrentMove());
        });
        this.add(currentNameLabel);
        this.add(playerName);
        this.add(currentScoreLabel);
        this.add(playerScore);
        this.add(endTurn);

        this.setVisible(true);
    }
    @Override
    public void update(ScrabbleEvent event) {
        playerName.setText(event.getCurrentPlayer().getName());
        String score = Integer.toString(event.getCurrentPlayer().getScore());
        playerScore.setText(score);
    }
}
