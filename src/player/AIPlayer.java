package player;

import game.AI;

/**
 * Class for AI player that inherits from the Player class.
 */
public class AIPlayer extends Player {

    // player id
    private int id;

    // ai type
    private AI.aiLevel aiType;

    /**
     * Constructor for AI player with given parameters.
     * @param id - id of player
     * @param aiType - type of AI
     */
    public AIPlayer(int id, AI.aiLevel aiType) {
        super(id, playerType.AI);

        this.id = id;
        this.aiType = aiType;
    }

}