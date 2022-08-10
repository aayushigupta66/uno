package gui;

import game.*;
import deck.*;
import player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Displays the current player's hand.
 */
public class PlayerHand extends JPanel {

    private ArrayList<Card> playerHand;

    private int turn;

    private Gui gui;

    /**
     * Creates a panel of the current player's cards allowing the player to select a card to play.
     */
    public PlayerHand(Gui gui) {
        this.gui = gui;

        // call displayHand to add buttons to the panel
        displayHand();

        setBorder(new EmptyBorder(50, 0, 20, 0));

        this.setVisible(true);
    }

    /**
     * Creates and adds a list of buttons with card icons to the panel.
     */
    private void displayHand() {

        // retrieve the current player's hand
        turn = gui.getGame().getTurn();
        playerHand = gui.getGame().getPlayers().get(turn).getHand();

        // iterate through the list of cards to create separate buttons
        for (int i = 0; i < playerHand.size(); i++) {
            // uses the corresponding png to create a card button
            ImageIcon curCard = new ImageIcon(playerHand.get(i).getPath());
            JButton cardButton = new JButton(curCard);
            this.add(cardButton);
            int j = i;
            cardButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // check validity of card
                    if (!gui.getGame().isValid(playerHand.get(j))) {
                        JOptionPane.showMessageDialog(gui, "Invalid play. " +
                                "Please try another card or draw card from draw pile.");
                        System.out.println(playerHand.get(j).getType().toString());
                        System.out.println(playerHand.get(j).getColor().toString());
                    } else {
                        if (playerHand.get(j).getType() != CardInfo.Type.WILD &&
                                playerHand.get(j).getType() != CardInfo.Type.WILD_DRAW_4) {
                            gui.getGame().playCard(j);
                            updateGameStatus();
                        } else {
                            createWildChoiceForm(j);
                        }

                    }
                }
            });

        }
    }

    /**
     * Based on game status update GUI.
     */
    private void updateGameStatus() {
        if (gui.getGame().checkGameStatus()) {
            gui.endGame();
        } else {
            gui.nextTurn();
        }
    }

    /**
     * Creates a form allowing players to select the between a choice of numbers and colors and
     * and reverse the game direction upon selecting to play a wild card.
     * @param i - index of card to play
     */
    private void createWildChoiceForm(int i) {
        JPanel panel = new JPanel();

        // choice of color or number for custom rules
        String[] choices = { "red", "yellow", "green", "blue",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        JComboBox wildChoice = new JComboBox(choices);
        wildChoice.setSelectedIndex(0);
        panel.add(wildChoice);

        // choice to reverse the order
        JCheckBox reverseOption = new JCheckBox("Reverse Order");
        reverseOption.setSelected(false);
        panel.add(reverseOption);

        int result = JOptionPane.showConfirmDialog(gui, panel, "Select Color/Number & Direction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // change order
            if (reverseOption.isSelected()) {
                gui.getGame().changeDirection();
            }

            String selected = (String) wildChoice.getSelectedItem();
            gui.getGame().getPlayers().get(turn).getHand().get(i).setColor(selected);
            gui.getGame().getPlayers().get(turn).getHand().get(i).setNumber(selected);
            gui.getGame().playCard(i);
            updateGameStatus();
        }
    }


}
