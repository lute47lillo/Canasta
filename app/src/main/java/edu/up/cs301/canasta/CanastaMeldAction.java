package edu.up.cs301.canasta;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class CanastaMeldAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CanastaMeldAction(GamePlayer player) {
        super(player);
    }
}
