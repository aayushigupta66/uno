package gui;

import deck.Card;
import deck.CardInfo;
import game.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Create a discard pile where player can see current game state, such as the color, number and
 * symbol of the latest card in the discard pile.
 */
public class Discard extends JPanel {

    private Icon discardCard;
    private JLabel discardLabel;
    private JLabel discardPileLabel;

    private JLabel wildLabel;

    private Gui gui;

    /**
     * Creates a panel for the discard pile.
     */
    public Discard(Gui gui) {
        this.gui = gui;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Card latestCard = gui.getGame().getGameCardDeck().getLatestDiscardCard();

        // gets the discard card's path and creates image icon
        discardCard = new ImageIcon(latestCard.getPath());
        discardLabel = new JLabel(discardCard);
        discardPileLabel = new JLabel("Discard Pile");

        add(discardPileLabel);
        add(discardLabel);

        if (gui.getGame().getGameCardDeck().getLatestDiscardCard().getType() == CardInfo.Type.WILD ||
                gui.getGame().getGameCardDeck().getLatestDiscardCard().getType() == CardInfo.Type.WILD_DRAW_4) {
            addWildLabel();
        }

        setBorder(new EmptyBorder(50, 10, 50, 50));

        this.setVisible(true);
    }

    /**
     * Add info about discard pile stats if latest card is wild.
     */
    private void addWildLabel() {
        int number = gui.getGame().getGameCardDeck().getLatestDiscardCard().getNumber();
        if (number == -1) {
            String color = gui.getGame().getGameCardDeck().getLatestDiscardCard().getColor().toString();
            wildLabel = new JLabel("Play " + color + " next!");
        } else {
            wildLabel = new JLabel("Play " + Integer.toString(number) + " next!");
        }
        add(wildLabel);
    }
}
