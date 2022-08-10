package gui;

import game.*;

import javax.swing.*;
import java.awt.*;

/**
 * Main GUI class that compiles and controls all game screens.
 */
public class Gui extends JFrame {

    private Start startFrame;
    private AddAI AIFrame;

    private GameState state;
    private Draw drawPile;
    private Discard discardPile;
    private PlayerHand currHand;
    private JPanel blank;

    private End endFrame;

    private int numPlayers;
    private int numAIPlayers;
    private AI.aiLevel aiType;
    private Game game;

    /**
     * Gui constructor that initiates game gui with start frame.
     */
    public Gui() {
        startFrame = new Start(this);
        add(startFrame, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setTitle("Game of UNO");
        setVisible(true);
    }

    /**
     * Switches to frame that selects the number of AI players in the game.
     */
    public void selectAI() {
        remove(startFrame);

        numPlayers = startFrame.getNumPlayers();

        AIFrame = new AddAI(this);
        add(AIFrame, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates game with given information: number of players, number of AI players, and type of AI.
     */
    public void initGame() {
        remove(AIFrame);

        numAIPlayers = AIFrame.getAiNum();
        aiType = AIFrame.getAiType();

        game = new Game(numPlayers, numAIPlayers, aiType);

        currentTurn();
    }

    /**
     * Creates UI for current player's turn by compiling different panels:
     * game state, draw pile, hidden hand, and discard pile.
     */
    public void currentTurn() {
        // add game state controls panel on the top of the GUI
        state = new GameState(this);
        add(state, BorderLayout.NORTH);

        // add draw pile panel on the left of the GUI
        drawPile = new Draw(this);
        add(drawPile, BorderLayout.WEST);

        // add discard pile panel on the right of the GUI
        discardPile = new Discard(this);
        add(discardPile, BorderLayout.EAST);

        // add blank panel to cover
        blank = new JPanel();
        add(blank, BorderLayout.CENTER);

        setVisible(true);
    }


    /**
     * Initiates the next player's turn by removing panels and recreating UI for next player.
     */
    public void nextTurn() {
        remove(state);
        remove(drawPile);
        remove(discardPile);
        remove(currHand);

        currentTurn();
    }

    /**
     * Reveals cards of current player's hand by creating and adding PlayerHand panel.
     */
    public void revealCards() {
        remove(blank);

        // add current player's hand panel in the middle of the GUI
        currHand = new PlayerHand(this);
        add(currHand, BorderLayout.CENTER);

        setVisible(true);
    }


    /**
     * Updates current player's hand by creating new PlayerHand panel.
     */
    public void updateGame() {
        remove(currHand);

        // add current player's hand panel in the middle of the GUI
        currHand = new PlayerHand(this);
        add(currHand, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates and sets end game scene.
     */
    public void endGame() {
        remove(state);
        remove(drawPile);
        remove(discardPile);
        remove(currHand);

        endFrame = new End(this);
        add(endFrame, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates a new game after end game scene.
     */
    public void startGame() {
        remove(endFrame);

        startFrame = new Start(this);
        add(startFrame, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Getter method for game variable.
     * @return current game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Getter method for the number of players.
     * @return number of players
     */
    public int getNumPlayers() {
        return numPlayers;
    }

}
