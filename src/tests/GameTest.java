package tests;

import deck.Card;
import deck.CardInfo;
import game.AI;
import game.Game;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    // test Game class
    Game testGame = new Game(6, 0, AI.aiLevel.BASELINE);

    @Test
    void testUpdateTurn() {
        assertEquals(0, testGame.getTurn());
        testGame.updateTurn();
        assertEquals(1, testGame.getTurn());
    }

    @Test
    void testReverse() {
        assertEquals(0, testGame.getTurn());
        testGame.getPlayers().get(testGame.getTurn()).
                addCard(new Card(CardInfo.Color.GREEN, CardInfo.Type.REVERSE, -1));
        testGame.playCard(testGame.getPlayers().get(testGame.getTurn()).getHand().size()-1);
        assertEquals(5, testGame.getTurn());
        assertEquals(Game.Direction.BACKWARD, testGame.getDirection());
    }

    @Test
    void testSkip() {
        assertEquals(0, testGame.getTurn());
        testGame.getPlayers().get(testGame.getTurn()).
                addCard(new Card(CardInfo.Color.GREEN, CardInfo.Type.SKIP, -1));
        testGame.playCard(testGame.getPlayers().get(testGame.getTurn()).getHand().size()-1);
        assertEquals(2, testGame.getTurn());
    }


    @Test
    void testDraw2() {
        testGame.getPlayers().get(testGame.getTurn()).
                addCard(new Card(CardInfo.Color.RED, CardInfo.Type.DRAW_2, -1));
        testGame.playCard(testGame.getPlayers().get(testGame.getTurn()).getHand().size()-1);
        assertEquals(2, testGame.getDrawCount());
    }

    @Test
    void testDraw4() {
        testGame.getPlayers().get(testGame.getTurn()).
                addCard(new Card(CardInfo.Color.NONE, CardInfo.Type.WILD_DRAW_4, -1));
        testGame.playCard(testGame.getPlayers().get(testGame.getTurn()).getHand().size()-1);
        assertEquals(4, testGame.getDrawCount());

        // check if reset penalty count works too
        testGame.resetPenalty();
        assertEquals(0, testGame.getDrawCount());
    }

    @Test
    void testGameStatus() {
        assertEquals(false, testGame.checkGameStatus());
    }

    @Test
    void testIncrementDrawCount() {
        testGame.incrementDrawCount();
        assertEquals(1, testGame.getCardsDrawn());
    }


    @Test
    void testValidTopCard() {
        assertEquals(CardInfo.Type.NORMAL, testGame.getGameCardDeck().getLatestDiscardCard().getType());
    }

    @Test
    void isValidWild() {
        assertEquals(true, testGame.isValid(new Card(CardInfo.Color.NONE,
                CardInfo.Type.WILD, -1)));
    }

    @Test
    void isValidNormal() {
        CardInfo.Color topColor = testGame.getGameCardDeck().getLatestDiscardCard().getColor();
        int topNumber = testGame.getGameCardDeck().getLatestDiscardCard().getNumber();
        assertEquals(true, testGame.isValid(new Card(CardInfo.Color.NONE, CardInfo.Type.NORMAL, topNumber)));
        assertEquals(true, testGame.isValid(new Card(topColor, CardInfo.Type.NORMAL, -1)));

    }

    @Test
    void isValidSpecial() {
        testGame.getGameCardDeck().discard(new Card(CardInfo.Color.RED, CardInfo.Type.SKIP, -1));
        assertEquals(true, testGame.isValid(new Card(CardInfo.Color.YELLOW, CardInfo.Type.SKIP, -1)));
    }

    @Test
    void isValidDraw() {
        testGame.getGameCardDeck().discard(new Card(CardInfo.Color.RED, CardInfo.Type.WILD_DRAW_4, -1));
        assertEquals(true, testGame.isValid(new Card(CardInfo.Color.NONE,
                CardInfo.Type.WILD_DRAW_4, -1)));
    }

    @Test
    void isNotValid() {
        testGame.getGameCardDeck().discard(new Card(CardInfo.Color.RED, CardInfo.Type.SKIP, -1));
        assertEquals(false, testGame.isValid(new Card(CardInfo.Color.YELLOW,
                CardInfo.Type.REVERSE, -1)));
    }


    // test Direction enum
    @Test
    void testToStringDirection() {
        assertEquals("forward", Game.Direction.FORWARD.toString());
        assertEquals("backward", Game.Direction.BACKWARD.toString());
    }

    // test aiLevel enum
    @Test
    void testToStringAiLevel() {
        assertEquals("Baseline", AI.aiLevel.BASELINE.toString());
        assertEquals("Strategic", AI.aiLevel.STRATEGIC.toString());
    }

    // test AI class
    Game aiGame = new Game(2, 1, AI.aiLevel.BASELINE);
    AI testAIBaseline = new AI(aiGame, AI.aiLevel.BASELINE);
    AI testAIStrategic = new AI(aiGame, AI.aiLevel.STRATEGIC);

    

}