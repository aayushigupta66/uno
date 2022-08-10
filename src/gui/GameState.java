package gui;

import deck.Card;
import game.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Controls features that change the game's state.
 */
public class GameState extends JPanel {

    private JLabel playerTurn;
    private int playerId;

    private JLabel numCardsStack;
    private int stack;

    private JLabel gameDirection;
    private Game.Direction direction;

    private JButton skip;

    private JButton reveal;
    private int clickCount;

    private Box container;

    private Gui gui;

    /**
     * Creates a panel for the game state controls - such as the Skip button, Reveal/Hide button,
     * and current player's turn.
     */
    public GameState(Gui gui) {
        this.gui = gui;

        // create container for game state labels
        container = new Box(BoxLayout.Y_AXIS);

        displayTurn();
        displayStackCount();
        displayDirection();

        container.setBorder(new EmptyBorder(10, 10, 10, 10));

        createSkipButton();
        createRevealButton();

        add(container);

        setBorder(new EmptyBorder(50, 20, 0, 20));
        this.setVisible(true);
    }

    /**
     * Gets the current player's turn and creates a label to display to the user.
     */
    public void displayTurn() {
        playerId = gui.getGame().getTurn() + 1;
        playerTurn = new JLabel("Player " + playerId + "'s turn!");
        container.add(playerTurn);
    }

    /**
     * Gets the number of cards to stack and creates a label to display to the user.
     */
    public void displayStackCount() {
        stack = gui.getGame().getDrawCount();
        numCardsStack = new JLabel("Number of cards to stack: " + stack);
        numCardsStack.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(numCardsStack);
    }

    /**
     * Gets the game direction and creates a label to display to the user.
     */
    public void displayDirection() {
        direction = gui.getGame().getDirection();
        gameDirection = new JLabel("Game direction: " + direction.toString());
        container.add(gameDirection);
    }

    /**
     * Creates a button to move on to the next player's turn.
     */
    public void createSkipButton() {
        skip = new JButton("Skip");

        skip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clickCount == 0) {
                    JOptionPane.showMessageDialog(gui, "Reveal cards before skip.");
                } else if (gui.getGame().getDrawCount() > 0) {
                    // accept penalty
                    int cardsToDraw = gui.getGame().getDrawCount();
                    int turn = gui.getGame().getTurn();
                    for (int i = 0; i < cardsToDraw; i++) {
                        Card drawnCard = gui.getGame().getGameCardDeck().draw();
                        gui.getGame().getPlayers().get(turn).addCard(drawnCard);
                    }
                    gui.getGame().resetPenalty();
                    gui.getGame().updateTurn();
                    gui.nextTurn();
                } else if (gui.getGame().getCardsDrawn() < 1) {
                    JOptionPane.showMessageDialog(gui, "First draw a card, then skip your turn.");
                } else {
                    gui.getGame().updateTurn();
                    gui.nextTurn();
                }
            }
        });

        add(skip);
    }

    /**
     * Creates a button to reveal/hide cards of the player of the current turn
     * (to prevent opponents see current player's cards).
     */
    public void createRevealButton() {
        reveal = new JButton("Reveal Cards");

        reveal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                if (clickCount < 2) {
                    gui.revealCards();
                }
            }
        });

        add(reveal);
    }


}
