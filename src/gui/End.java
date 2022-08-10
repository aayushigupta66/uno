package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game ending scene to indicate the winner.
 */
public class End extends JPanel{

    private JLabel winnerLabel;
    private int playerId;

    private JButton newGame;

    private Gui gui;

    /**
     * Creates a frame that indicates the winner of the game.
     */
    public End(Gui gui)  {
        this.gui = gui;

        // creating the winnerLabel by retrieving the winner of the game
        playerId = gui.getGame().getTurn() + 1;
        winnerLabel = new JLabel("Player " + playerId + " won!");

        // creating a button to allow user to start a new game
        newGame = new JButton("New Game");

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.startGame();
            }
        });

        add(winnerLabel);
        add(newGame);
        setBorder(new EmptyBorder(290, 290, 290, 290));
    }


}
