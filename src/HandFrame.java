import javax.swing.*;
import java.awt.*;

/*
    Following Class will represent the users Hand/Rack
 */
public class HandFrame extends JFrame implements ScrabbleView{
    private JButton [] buttons;
    private Game game;

    public HandFrame(Game game){
        super();
        this.game = game;

        GridLayout buttonGrid = new GridLayout(1, 7); // Place handsize
        this.setLayout(buttonGrid);
        this.setSize(900,100);

        buttons = new JButton[7];   // Hand Size Again (7)

        for(int i = 0; i < buttons.length; i ++){  // Game.HandSize value (7){
            buttons[i] = new JButton();
            buttons[i].setActionCommand("" + i);
           // buttons[i].addActionListener(controller); // insert controller
            this.add(buttons[i]);
        }

        this.setVisible(true);

    }

    private void setHandForTurn(Player p) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(p.getAvailableLetters()[i]);
        }
    }

    /*
    Use following update section to update the Scrabble Game Model
        -   missing players rack size is 0
     */
    @Override
    public void update(ScrabbleEvent event){ // Use this section to update the Scrabble game model
        setHandForTurn(event.getCurrentPlayer());
    }
}
