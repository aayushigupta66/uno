package gui;

import game.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel of allowing user to specify number of AIs in the game.
 */
public class AddAI extends JPanel {

    private JButton startButton;

    private JLabel numAILabel;
    private JComboBox aiNumSelector;

    private ButtonGroup group;
    private JRadioButton baseline;
    private JRadioButton strategic;

    private String aiNum;
    private AI.aiLevel type;

    private Gui gui;

    private int numTotalPlayers;

    /**
     * AI panel constructor.
     * @param gui - parent gui
     */
    public AddAI(Gui gui) {
        this.gui = gui;

        numTotalPlayers = gui.getNumPlayers();

        selectNumAi();
        selectAiButtons();
        createStartButton();

        setBorder(new EmptyBorder(290, 0, 290, 0));
    }

    /**
     * Create player number selector.
     */
    private void selectNumAi() {
        numAILabel = new JLabel("Enter number of AI players");

        // select number of players from the array below to limit the number of AI players
        // to remain within 2-9 players
        String[] numAIPlayers = new String[numTotalPlayers];
        for (int i = 0; i < numAIPlayers.length; i++) {
            numAIPlayers[i] = Integer.toString(i);
        }

        aiNumSelector = new JComboBox(numAIPlayers);
        aiNumSelector.setSelectedIndex(0);

        add(numAILabel);
        add(aiNumSelector);
    }

    /**
     * Create buttons to select which type of AI to use.
     */
    private void selectAiButtons() {
        group = new ButtonGroup();
        baseline = new JRadioButton("Baseline");
        strategic = new JRadioButton("Strategic");
        group.add(baseline);
        group.add(strategic);

        baseline.setSelected(true);

        add(baseline);
        add(strategic);
    }

    /**
     * Create start button to submit form.
     */
    private void createStartButton() {
        startButton = new JButton("Start");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // set variables of ai type and number of ai players
                if (strategic.isSelected()) {
                    setAiType(strategic.getText());
                } else {
                    setAiType(baseline.getText());
                }
                setAiNum();
                gui.initGame();
            }
        });

        add(startButton);
    }

    /**
     * Setter method for number of AI players.
     */
    private void setAiNum() {
        aiNum = (String) aiNumSelector.getSelectedItem();
    }

    /**
     * Setter method for type of AI.
     * @param typeString - string of AI level
     */
    private void setAiType(String typeString) {
        if (typeString == AI.aiLevel.BASELINE.toString()) {
            type = AI.aiLevel.BASELINE;
        } else {
            type = AI.aiLevel.STRATEGIC;
        }
    }

    /**
     * Getter method for number of AI players.
     * @return number of AI players
     */
    public int getAiNum() {
        return Integer.parseInt(aiNum);
    }

    /**
     * Getter method for type of AI.
     * @return type of AI
     */
    public AI.aiLevel getAiType() {
        return type;
    }

}
