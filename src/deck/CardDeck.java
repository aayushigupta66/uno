package deck; 

import player.Player;

import java.util.*;

/**
 * Initializes the draw and discard piles and allows modifications to both decks.
 */
public class CardDeck {

    // card piles
    private ArrayList<Card> drawPile = new ArrayList<Card>();
    private ArrayList<Card> discardPile = new ArrayList<Card>();

    /**
     * Constructor for new shuffled draw pile.
     */
    public CardDeck() {
        // create deck
        initDeck();
        // shuffle deck
        shuffleDrawPile();
    }

    /**
     * Initializing the draw pile with the 108 cards.
     */
    private void initDeck() {

        // list of colors to iterate over for sets of cards
        CardInfo.Color[] colors = new CardInfo.Color[]{ CardInfo.Color.RED, CardInfo.Color.YELLOW,
                CardInfo.Color.GREEN, CardInfo.Color.BLUE };

        for (int i = 0; i < colors.length; i++) {
            // 4 “Wild”, “Wild Draw Four” cards
            drawPile.add(new Card(CardInfo.Color.NONE, CardInfo.Type.WILD, -1));
            drawPile.add(new Card(CardInfo.Color.NONE, CardInfo.Type.WILD_DRAW_4, -1));

            // 1 set of “0” card
            drawPile.add(new Card(colors[i], CardInfo.Type.NORMAL, 0));

            for (int j = 0; j < 2; j++) {
                // 2 sets of “Skip”, "Reverse", "Draw Two" cards
                drawPile.add(new Card(colors[i], CardInfo.Type.SKIP, -1));
                drawPile.add(new Card(colors[i], CardInfo.Type.REVERSE, -1));
                drawPile.add(new Card(colors[i], CardInfo.Type.DRAW_2, -1));

                // two sets of “1” - “9” cards
                for (int k = 1; k <= 9; k++) {
                    drawPile.add(new Card(colors[i], CardInfo.Type.NORMAL, k));
                }
            }
        }

    }

    /**
     * Shuffle draw pile using Random.
     */
    private void shuffleDrawPile() {
        Collections.shuffle(drawPile, new Random());
    }

    /**
     * Draw the top card from the draw pile.
     * @return card that has been drawn
     */
    public Card draw() {
        if (drawPile.size() == 1) {
            // reusing discard pile when no card is left in the draw pile
            int n = discardPile.size()-1;
            for (int i = 0; i < n; i++) {
                drawPile.add(discardPile.remove(0));
            }
            shuffleDrawPile();
        }

        if (drawPile.get(0).getType() == CardInfo.Type.WILD || drawPile.get(0).getType() == CardInfo.Type.WILD_DRAW_4) {
            // reset wild card
            drawPile.get(0).setColor(CardInfo.Color.NONE);
            drawPile.get(0).setNumber(-1);
        }

        return drawPile.remove(0);
    }

    /**
     * Add card to discard pile.
     * @param card card that needs to be discarded
     */
    public void discard(Card card) {
        discardPile.add(card);
    }

    /**
     * Retrieve the latest card from the discard pile.
     * @return last card from discard pile
     */
    public Card getLatestDiscardCard() {
        return discardPile.get(discardPile.size()-1);
    }


    /**
     * @return draw pile
     */
    public ArrayList<Card> getDrawPile() {
        return drawPile;
    }

    /**
     * @return discard pile
     */
    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }
}