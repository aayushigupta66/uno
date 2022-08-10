package gui;

import deck.Card;
import game.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create a draw pile -- deck of cards where players can draw cards.
 */
public class Draw extends JPanel {

    private Icon drawCard;
    private JButton drawButton;
    private JLabel drawPileLabel;

    private Gui gui;

    private int numberDrawn;

    /**
     * Creates a panel for the draw pile.
     */
    public Draw(Gui gui) {
        this.gui = gui;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // uses the draw png to create a draw card button
        drawCard = new ImageIcon("src/images/draw.png");
        drawButton = new JButton(drawCard);
        drawPileLabel = new JLabel("Draw Pile");

        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gui.getGame().getDrawCount() > 0) {
                    JOptionPane.showMessageDialog(gui, "Cannot draw cards. " +
                            "Select card to stack or skip turn to accept penalty.");
                } else if (gui.getGame().getCardsDrawn() < 1) {
                    gui.getGame().incrementDrawCount();

                    Card drawnCard = gui.getGame().getGameCardDeck().draw();
                    int turn = gui.getGame().getTurn();
                    gui.getGame().getPlayers().get(turn).addCard(drawnCard);

                    gui.updateGame();
                } else {
                    JOptionPane.showMessageDialog(gui, "Already drawn a card. Play card or skip turn.");
                }
            }
        });

        add(drawPileLabel);
        add(drawButton);

        setBorder(new EmptyBorder(50, 50, 50, 10));

        this.setVisible(true);
    }



}
