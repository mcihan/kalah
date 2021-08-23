package com.cihan.kalah.service.engine;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;

/**
 * @author cihan dogan  on 24.08.2021
 */


public abstract class GameState {
    GameState nextState;

    public abstract void execute(Game game, Pit pit);

    GameState setNextState(GameState gameState) {
        this.nextState = gameState;
        return nextState;
    }

}
