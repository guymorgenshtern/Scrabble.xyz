import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements ScrabbleView {

    private final JLabel playerName;
    private final JLabel playerScore;

    public InfoPanel(ScrabbleModel scrabbleModel) {
        super();
        this.setLayout(new GridLayout(1, 3));
        this.setSize(900,100);

        playerName = new JLabel("", SwingConstants.CENTER);
        playerScore = new JLabel();
        JLabel currentNameLabel = new JLabel("Current Player Name:", SwingConstants.RIGHT);
        JLabel currentScoreLabel = new JLabel("Score: ", SwingConstants.RIGHT);
        JButton endTurn = new JButton("End Turn");
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
        playerScore.setText(Integer.toString(event.getCurrentPlayer().getScore()));
    }
}
