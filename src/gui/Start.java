package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Start scene of the game.
 */
public class Start extends JPanel {

    private JButton nextButton;
    private JLabel numPlayerLabel;
    private JComboBox playerNumSelector;

    private String playerNum;

    private Gui gui;

    /**
     * Start scene of the game, which allows the user to specify the number of players in the
     * game and the initial setup of the game.
     */
    public Start(Gui gui)  {
        this.gui = gui;

        numPlayerLabel = new JLabel("Enter number of players");

        // select number of players from the array below to limit the number of players
        // to remain within 2-9 players
        String[] numPlayers = {"2", "3", "4", "5", "6", "7", "8", "9"};
        playerNumSelector = new JComboBox(numPlayers);
        playerNumSelector.setSelectedIndex(0);

        nextButton = new JButton("Next");

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNumPlayers();
                gui.selectAI();
            }
        });

        add(numPlayerLabel);
        add(playerNumSelector);
        add(nextButton);

        setBorder(new EmptyBorder(290, 290, 290, 290));

    }

    /**
     * Setter method for number of players selected by user.
     */
    public void setNumPlayers() {
        playerNum = (String) playerNumSelector.getSelectedItem();
    }

    /**
     * Getter method for number of players selected by user.
     * @return number of players
     */
    public int getNumPlayers() {
        return Integer.parseInt(playerNum);
    }


}
