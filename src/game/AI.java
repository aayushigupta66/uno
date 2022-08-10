package game;

import game.Game;
import deck.*;
import gui.Gui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Class that contains the AI algorithm for both baseline and strategic AIs.
 */
public class AI {

    /**
     * Interface that keeps track of what different types of AI players (baseline and strategic).
     */
    public enum aiLevel {
        BASELINE {
            public String toString() {
                return "Baseline";
            }
        },
        STRATEGIC {
            public String toString() {
                return "Strategic";
            }
        }
    };

    private Game game;

    private ArrayList<Card> hand;
    private ArrayList<Integer> validCards;

    private int turn;

    private aiLevel aiType;

    /**
     * Constructor for running the AI algorithm.
     * @param game - the game that is being played
     * @param aiType - type of ai algorithm being used during game
     */
    public AI(Game game, aiLevel aiType) {
        this.aiType = aiType;
        this.game = game;

        // set current player's id
        turn = game.getTurn();

        // set hand of current player
        hand = game.getPlayers().get(turn).getHand();

        runAI();
    }


    /**
     * Run's the AI algorithm.
     */
    private void runAI() {
        findValidCards();

        if (validCards.size() == 0) {
            int cardsToDraw = game.getDrawCount();
            if (cardsToDraw > 0) {
                // skip turn and draw cards
                // accept penalty
                for (int i = 0; i < cardsToDraw; i++) {
                    Card drawnCard = game.getGameCardDeck().draw();
                    game.getPlayers().get(turn).addCard(drawnCard);
                }
                game.resetPenalty();
            } else {
                drawCard();
            }
        } else {
            if (aiType == aiLevel.BASELINE) {
                playBaseline();
            } else {
                playStrategic();
            }
        }

    }

    /**
     * Creates a list of cards indices that are valid to play.
     */
    private void findValidCards() {
        validCards = new ArrayList<Integer>();

        for (int i = 0; i < hand.size(); i++) {
            if (game.isValid(hand.get(i))) {
                validCards.add(i);
            }
        }
    }

    /**
     * AI player draws card and can play card if valid.
     */
    private void drawCard() {
        Card drawnCard = game.getGameCardDeck().draw();

        // check if drawnCard is valid then play
        // add to hand otherwise
        if (game.isValid(drawnCard)) {
            game.getGameCardDeck().discard(drawnCard);
        } else {
            game.getPlayers().get(turn).addCard(drawnCard);
        }
    }

    /**
     * Play next card with baseline strategy. Automatically play a random valid card.
     */
    private void playBaseline() {
        // play random card
        Random rand = new Random();
        int idx = rand.nextInt(validCards.size());

        // play card at idx
        Card card = game.getPlayers().get(turn).getHand().get(validCards.get(idx));
        if (card.getType() == CardInfo.Type.WILD || card.getType() == CardInfo.Type.WILD_DRAW_4) {
            game.getPlayers().get(turn).getHand().get(validCards.get(idx)).setColor(CardInfo.Color.RED);
        }
        game.playCard(validCards.get(idx));

    }

    /**
     * Play next card strategically. Prefer to play a playable card with a popular color in our cards.
     */
    private void playStrategic() {
        // create a hashmap to keep track of valid cards' colors
        Map<CardInfo.Color, ArrayList<Integer>> colorMap = new HashMap<CardInfo.Color, ArrayList<Integer>>();

        // use arrays as values to keep track of indices
        for (CardInfo.Color color : CardInfo.Color.values()) {
            colorMap.put(color, new ArrayList<Integer>());
        }

        // update hashmap
        for (int i = 0; i < validCards.size(); i++) {
            for (CardInfo.Color color : CardInfo.Color.values()) {
                if (hand.get(validCards.get(i)).getColor() == color) {
                    colorMap.get(color).add(i);
                    break;
                }
            }
        }

        // determine which color has the most number of valid cards
        CardInfo.Color maxColor = CardInfo.Color.NONE;
        int max = 0;

        for (CardInfo.Color color : CardInfo.Color.values()) {
            if (colorMap.get(color).size() > max) {
                maxColor = color;
                max = colorMap.get(color).size();
            }
        }

        // play card at first index of max color
        int idx = colorMap.get(maxColor).get(0);
        game.playCard(validCards.get(idx));
    }

}
