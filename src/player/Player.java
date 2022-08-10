package player; 

import deck.*;
import game.*;

import java.util.*;

/**
 * Keeps track of each player's hand and is given an id to differentiate amongst players.
 */
public abstract class Player {


    /**
     * Player type enum that keeps track of the player's type (human vs AI).
     */
    public enum playerType {
        HUMAN {
            public String toString() {
                return "human";
            }
        },
        AI {
            public String toString() {
                return "ai";
            }
        }
    };

    // player hand
    private ArrayList<Card> playerHand = new ArrayList<Card>();

    // player id 
    private int id;

    // player type
    private playerType playerType;

    /**
     * Constructor for new player.
     * @param id - unique id
     */
    public Player(int id, playerType playerType) {
        this.id = id;
        this.playerType = playerType;
    }

    /**
     * Adds given card to player's hand.
     * @param card - card that will be added to the player's hand
     */
    public void addCard(Card card) {
        playerHand.add(card);
    }

    /**
     * Removes specific card from player's hand.
     * @param index - index of the specific card
     * @return card that was removed
     */
    public Card removeCard(int index) {
        return playerHand.remove(index);
    }

    /**
     * Returns the player's hand of cards.
     * @return player's hand
     */
    public ArrayList<Card> getHand() {
        return playerHand;
    }

    /**
     * Getter method for the player's unique id.
     * @return id of player
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for player's type (human or ai).
     * @return type of player
     */
    public playerType getPlayerType() {
        return playerType;
    }


}