package player;

import game.AI;


/**
 * Class for human player that inherits from the Player class.
 */
public class HumanPlayer extends Player {

    // player id
    private int id;

    /**
     * Constructor for human player with given parameters.
     * @param id - id of player
     */
    public HumanPlayer(int id) {
        super(id, playerType.HUMAN);

        this.id = id;
    }

}
