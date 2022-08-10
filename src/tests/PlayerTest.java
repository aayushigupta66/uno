package tests;

import deck.Card;
import deck.CardDeck;
import deck.CardInfo;
import game.AI;

import org.junit.jupiter.api.Test;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    // test HumanPlayer class
    Player testPlayer = new HumanPlayer(3);
    Card testCard = new Card(CardInfo.Color.NONE, CardInfo.Type.WILD_DRAW_4, -1);

    @Test
    void addCard() {
        testPlayer.addCard(testCard);
        assertEquals(testCard, testPlayer.getHand().get(0));
    }

    @Test
    void getHand() {
        testPlayer.addCard(testCard);
        assertEquals(1, testPlayer.getHand().size());
        assertEquals(testCard, testPlayer.getHand().get(0));
    }

    @Test
    void removeCard() {
        testPlayer.addCard(testCard);
        assertEquals(testCard, testPlayer.removeCard(0));
    }

    @Test
    void getId() {
        assertEquals(3, testPlayer.getId());
    }

    // test AIPlayer class
    Player testAIPlayer = new AIPlayer(1, AI.aiLevel.BASELINE);

    @Test
    void getIdAI() {
        assertEquals(1, testAIPlayer.getId());
    }

    @Test
    void getPlayerType() {
        assertEquals(Player.playerType.AI, testAIPlayer.getPlayerType());
    }

    // test playerType enum
    @Test
    void testToStringPlayerType() {
        assertEquals("human", Player.playerType.HUMAN.toString());
        assertEquals("ai", Player.playerType.AI.toString());
    }

}