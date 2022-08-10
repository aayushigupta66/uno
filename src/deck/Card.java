package deck; 

import java.util.*;

/**
 * Constructs different types of cards based on 3 parameters - color, type, and number.
 */
public class Card {

    private CardInfo.Color color; 
    private CardInfo.Type type; 
    private int number;

    /**
     * Construct a card with the given values.
     * @param color
     * @param type
     * @param number
     */
    public Card(CardInfo.Color color, CardInfo.Type type, int number) {
        this.color = color;
        this.type = type;
        this.number = number;
    }

    /**
     * @return card color
     */
    public CardInfo.Color getColor() {
        return color;
    }

    /**
     * @return card type
     */
    public CardInfo.Type getType() {
        return type;
    }

    /**
     * @return card number
     */
    public int getNumber() {
        return number;
    }


    /**
     * @param color - color that you want to change the card to
     */
    public void setColor(CardInfo.Color color) {
        this.color = color;
    }

    /**
     * Method overloading with String instead of CardInfo.Color.
     * @param color - color that you want to change the card to
     */
    public void setColor(String color) {
        if (color == "red") {
            this.color = CardInfo.Color.RED;
        } else if (color == "blue") {
            this.color = CardInfo.Color.BLUE;
        } else if (color == "yellow") {
            this.color = CardInfo.Color.YELLOW;
        } else if (color == "green") {
            this.color = CardInfo.Color.GREEN;
        }
    }

    /**
     * @param number - number that you want to change the card to
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Method overloading with String instead of int.
     * @param number - number that you want to change the card to
     */
    public void setNumber(String number) {
        try {
            int numberChoice = Integer.parseInt(number);
            this.number = numberChoice;
        } catch (NumberFormatException nfe) {
            // do nothing
        }
    }

    /**
     * Determines the corresponding image path of the given card.
     * @return the string corresponding to the correct image path
     */
    public String getPath() {
        String path = "src/images/";

        // add to the path based on the card's details
        if (type == CardInfo.Type.NORMAL) {
            path += color.toString() + "_" + number + ".png";
        } else if (type == CardInfo.Type.SKIP || type == CardInfo.Type.REVERSE || type == CardInfo.Type.DRAW_2) {
            path += color.toString() + "_" + type.toString() + ".png";
        } else if (type == CardInfo.Type.WILD_DRAW_4 || type == CardInfo.Type.WILD) {
            path += type.toString() + ".png";
        }

        return path;
    }


}