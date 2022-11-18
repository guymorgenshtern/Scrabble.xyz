import javax.swing.*;
import java.awt.*;

/*
    Following Class will represent the users Hand/Rack
 */
public class HandPanel extends JPanel implements ScrabbleView{
    private JButton [] buttons;
    private ScrabbleModel scrabbleModel;

    public HandPanel(ScrabbleModel scrabbleModel){
        super();
        this.scrabbleModel = scrabbleModel;

        GridLayout buttonGrid = new GridLayout(1, 7); // Place handsize
        this.setLayout(buttonGrid);
        this.setSize(900,100);

        buttons = new JButton[7];   // Hand Size Again (7)

        for(int i = 0; i < buttons.length; i++){  // Game.HandSize value (7){
            buttons[i] = new JButton();
            buttons[i].setActionCommand("" + i);
            int letter = i;
            buttons[i].addActionListener(e -> {
                scrabbleModel.setSelectedLetter(buttons[letter].getText());
                scrabbleModel.getUsedLetters().add(letter);
                updateVisibility();
           }); // insert controller
            this.add(buttons[i]);
        }

        this.setVisible(true);

    }

    private void setHandForTurn(Player p) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(p.getAvailableLetters().get(i));
        }
    }

    private void updateVisibility() {
        for (JButton b : buttons) {
            b.setEnabled(true);
        }
        for (Integer i : scrabbleModel.getUsedLetters()) {
            buttons[i].setEnabled(false);
        }
    }

    public JButton[] getButtons() {
        return buttons;
    }

    /*
        Use following update section to update the Scrabble Game Model
            -   missing players rack size is 0
         */
    @Override
    public void update(ScrabbleEvent event){ // Use this section to update the Scrabble game model

        setHandForTurn(event.getCurrentPlayer());
        updateVisibility();
    }
}
