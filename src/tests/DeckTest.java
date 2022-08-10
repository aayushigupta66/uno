package tests;

import deck.Card;
import deck.CardDeck;
import deck.CardInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    // test Card class
    Card testCard = new Card(CardInfo.Color.GREEN, CardInfo.Type.NORMAL, 3);

    @org.junit.jupiter.api.Test
    void getColor() {
        assertEquals(CardInfo.Color.GREEN, testCard.getColor());
    }

    @org.junit.jupiter.api.Test
    void getType() {
        assertEquals(CardInfo.Type.NORMAL, testCard.getType());
    }

    @org.junit.jupiter.api.Test
    void getNumber() {
        assertEquals(3, testCard.getNumber());
    }

    @org.junit.jupiter.api.Test
    void setColor() {
        testCard.setColor(CardInfo.Color.YELLOW);
        assertEquals(CardInfo.Color.YELLOW, testCard.getColor());
    }

    @org.junit.jupiter.api.Test
    void setColorStringRed() {
        testCard.setColor("red");
        assertEquals(CardInfo.Color.RED, testCard.getColor());
    }

    @org.junit.jupiter.api.Test
    void setColorStringGreen() {
        testCard.setColor("green");
        assertEquals(CardInfo.Color.GREEN, testCard.getColor());
    }

    @org.junit.jupiter.api.Test
    void setColorStringYellow() {
        testCard.setColor("yellow");
        assertEquals(CardInfo.Color.YELLOW, testCard.getColor());
    }

    @org.junit.jupiter.api.Test
    void setColorStringBlue() {
        testCard.setColor("blue");
        assertEquals(CardInfo.Color.BLUE, testCard.getColor());
    }

    @org.junit.jupiter.api.Test
    void setNumber() {
        testCard.setNumber(5);
        assertEquals(5, testCard.getNumber());
    }

    @org.junit.jupiter.api.Test
    void setNumberString() {
        testCard.setNumber("9");
        assertEquals(9, testCard.getNumber());
    }

    @org.junit.jupiter.api.Test
    void setNumberStringInvalid() {
        testCard.setNumber("green");
        assertEquals(3, testCard.getNumber());
    }

    @org.junit.jupiter.api.Test
    void getPathNormalCard() {
        assertEquals("src/images/green_3.png", testCard.getPath());
    }

    @org.junit.jupiter.api.Test
    void getPathSpecialCard() {
        testCard = new Card(CardInfo.Color.BLUE, CardInfo.Type.REVERSE, -1);
        assertEquals("src/images/blue_reverse.png", testCard.getPath());
    }

    @org.junit.jupiter.api.Test
    void getPathWildCard() {
        testCard = new Card(CardInfo.Color.NONE, CardInfo.Type.WILD, -1);
        assertEquals("src/images/wild.png", testCard.getPath());
    }


    // test CardDeck class
    CardDeck testDeck = new CardDeck();

    @Test
    void testDrawCard() {
        Card card = testDeck.draw();
        assertNotEquals(card, testDeck.draw());
    }

    @Test
    void reuseDiscardPile() {
        while (testDeck.getDrawPile().size() > 1) {
            Card temp = testDeck.draw();
            testDeck.discard(temp);
        }
        Card temp = testDeck.draw();
        testDeck.discard(temp);
        assertNotEquals(0, testDeck.getDrawPile().size());
    }

    @Test
    void maintainCardCount() {
        assertEquals(108, testDeck.getDrawPile().size() +
                testDeck.getDiscardPile().size());
    }

    @Test
    void testDiscardCard() {
        Card testCard = new Card(CardInfo.Color.GREEN, CardInfo.Type.NORMAL, 3);
        testDeck.discard(testCard);
        assertEquals(testCard, testDeck.getLatestDiscardCard());
    }

    @Test
    void checkTopCard() {
        Card testCard = new Card(CardInfo.Color.RED, CardInfo.Type.NORMAL, 3);
        testDeck.discard(testCard);
        assertEquals(testCard, testDeck.getLatestDiscardCard());
    }

    // test CardInfo enum
    @Test
    void testToStringColor() {
        assertEquals("red", CardInfo.Color.RED.toString());
        assertEquals("blue", CardInfo.Color.BLUE.toString());
        assertEquals("green", CardInfo.Color.GREEN.toString());
        assertEquals("yellow", CardInfo.Color.YELLOW.toString());
        assertEquals("", CardInfo.Color.NONE.toString());
    }

    @Test
    void testToStringType() {
        assertEquals("wild", CardInfo.Type.WILD.toString());
        assertEquals("wild_4", CardInfo.Type.WILD_DRAW_4.toString());
        assertEquals("normal", CardInfo.Type.NORMAL.toString());
        assertEquals("skip", CardInfo.Type.SKIP.toString());
        assertEquals("reverse", CardInfo.Type.REVERSE.toString());
        assertEquals("plus2", CardInfo.Type.DRAW_2.toString());
    }

}