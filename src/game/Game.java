package game;

import java.awt.desktop.SystemEventListener;
import java.util.*;

import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import deck.*;

/**
 * Runs a game and keeps track of the game state.
 */
public class Game {

    /**
     * Direction enum that keeps track of what direction the game is being played (forward vs backward).
     */
    public enum Direction {
        FORWARD {
            public String toString() {
                return "forward";
            }
        },
        BACKWARD {
            public String toString() {
                return "backward";
            }
        }
    };

    private CardDeck gameCardDeck;

    private ArrayList<Player> players = new ArrayList<Player>();
    private int numPlayers;
    public static final int HAND_SIZE = 7;
    private int numHuman;
    private int numAi;
    private AI.aiLevel aiType;

    private int turn;

    private int cardsDrawn;

    private Direction direction;

    private int drawCount;
    public static final int DRAW_2_COUNT = 2;
    public static final int DRAW_4_COUNT = 4;

    /**
     * Constructs and runs the game based on the given number of players.
     * @param numPlayers - number of players in the game
     */
    public Game(int numPlayers, int numAi, AI.aiLevel aiType) {
        
        // initialize card deck
        gameCardDeck = new CardDeck();
        
        // set number of players
        this.numPlayers = numPlayers;
        this.numAi = numAi;
        this.aiType = aiType;
        numHuman = numPlayers - numAi;

        // create new human players
        for (int i = 1; i <= numHuman; i++) {
            players.add(new HumanPlayer(i));
        }

        // create new ai players
        for (int i = numHuman + 1; i <= numPlayers; i++) {
            players.add(new AIPlayer(i, aiType));
        }

        // deal cards
        for (int i = 0; i < HAND_SIZE; i++) {
            for (int j = 0; j < numPlayers; j++) {
                Card drawnCard = gameCardDeck.draw();
                players.get(j).addCard(drawnCard);
            }
        }

        // sets first card
        Card firstCard = gameCardDeck.draw();
        gameCardDeck.discard(firstCard);

        // make sure the card is a NORMAL card
        while (firstCard.getType() != CardInfo.Type.NORMAL) {
            firstCard = gameCardDeck.draw();
            gameCardDeck.discard(firstCard);
        }

        // starts game off with first player's turn
        this.turn = 0;

        this.cardsDrawn = 0;

        // set direction of game to be forward
        this.direction = Direction.FORWARD;

        // set draw count to be 0
        drawCount = 0;

    }


    /**
     * Update the decks and game state based on what card the player plays.
     * @param handIndex - index of the card the player wants to play
     */
    public void playCard(int handIndex) {
        // remove card located at handIndex
        Card playedCard = players.get(turn).removeCard(handIndex);

        // update game state accordingly
        CardInfo.Type playedCardType = playedCard.getType();
        if (playedCardType == CardInfo.Type.SKIP) {
            // update turn and skip next player
            updateTurn();
        } else if (playedCardType == CardInfo.Type.REVERSE) {
            // change the direction of the game
            changeDirection();
        } else if (playedCardType == CardInfo.Type.DRAW_2) {
            // add 2 to the draw count
            drawCount += DRAW_2_COUNT;
        } else if (playedCardType == CardInfo.Type.WILD_DRAW_4) {
            // add 4 to the draw count
            drawCount += DRAW_4_COUNT;
        }

        // add playedCard to discard pile
        gameCardDeck.discard(playedCard);

        // check if player had cards left
        if (checkGameStatus()) {
            return;
        }

        // update turn
        updateTurn();
    }

    /**
     * Method that changes/reverses the direction of the game.
     */
    public void changeDirection() {
        direction = (direction == Direction.FORWARD) ? Direction.BACKWARD : Direction.FORWARD;
    }

    /**
     * Checks if the card the player wants to play is valid, based on the game's rules.
     * @param card - card the player wants to play
     * @return true if the given card is a valid play
     */
    public boolean isValid(Card card) {

        CardInfo.Type cardType = card.getType();
        CardInfo.Color cardColor = card.getColor();
        int cardNumber = card.getNumber();

        Card latestDiscardCard = gameCardDeck.getLatestDiscardCard();
        CardInfo.Type latestType = latestDiscardCard.getType();
        CardInfo.Color latestColor = latestDiscardCard.getColor();
        int latestNumber = latestDiscardCard.getNumber();

        // set of rules to check if play is valid
        if ((latestType == CardInfo.Type.WILD_DRAW_4 || latestType == CardInfo.Type.DRAW_2) && drawCount != 0) {
            // rules for stacking cards
            if (cardType == latestType) {
                return true;
            } else {
                return false;
            }
        } else {
            // rules for non stacking cards
            if (cardType == CardInfo.Type.WILD || cardType == CardInfo.Type.WILD_DRAW_4) {
                return true;
            } else if (cardType == CardInfo.Type.NORMAL && cardNumber == latestNumber) {
                return true;
            } else if (cardColor == latestColor) {
                return true;
            } else if (cardType != CardInfo.Type.NORMAL && cardType == latestType) {
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * Updates the players turn based on the direction of the game.
     */
    public void updateTurn() {
        turn = (direction == Direction.FORWARD) ? turn + 1 : turn - 1;
        turn %= numPlayers;
        if (turn < 0) turn += numPlayers;

        // reset number of cards drawn
        cardsDrawn = 0;

        // check if it's ai turn to play
        if (players.get(turn).getPlayerType() == Player.playerType.AI) {
            new AI(this, aiType);
            updateTurn();
        }
    }


    /**
     * Checks if the game is over based on the number of cards in the current player's hand.
     * @return true if the current player has no cards left in their hand
     */
    public boolean checkGameStatus() {
        return players.get(turn).getHand().size() == 0;
    }


    /**
     * Updates the number of cards the player has drawn from the draw pile.
     */
    public void incrementDrawCount() {
        cardsDrawn++;
    }

    /**
     * Resets the draw count to 0 once the player receives the penalty from stacking.
     */
    public void resetPenalty() {
        drawCount = 0;
    }


    // getter methods

    /**
     * @return id of current player
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @return list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return game's card decks
     */
    public CardDeck getGameCardDeck() {
        return gameCardDeck;
    }

    /**
     * @return stack count
     */
    public int getDrawCount() {
        return drawCount;
    }

    /**
     * @return direction of game
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return number of cards player has drawn from draw pile
     */
    public int getCardsDrawn() {
        return cardsDrawn;
    }
}

