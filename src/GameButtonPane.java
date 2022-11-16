import javax.swing.*;
import java.awt.*;

public class GameButtonPane extends JPanel{
    private JButton play;
    private JButton skipTurn;
    //  JButton swapLetter; //  Commented out for now
    private Game game;

    public GameButtonPane(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.setSize(700,500);
        play = new JButton("Play");
        skipTurn = new JButton("Skip Turn");

        this.add(play);
        this.add(skipTurn);

        play.setAlignmentX(Component.CENTER_ALIGNMENT);
        skipTurn.setAlignmentX(Component.CENTER_ALIGNMENT);

        play.addActionListener();   // Insert Controller
        play.addActionListener();   // Insert Controller
    }
}
