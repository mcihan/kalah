package com.cihan.kalah.service.engine;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cihan dogan  on 24.08.2021
 */

@Slf4j
public class DetermineActivePlayerState extends GameState {

    @Override
    public void execute(Game game, Pit pit) {
        if (game.getActivePlayer() == null) {
            game.initActivePlayerByPitId(pit.getId());
        }
        log.info("Player {} is determined as a Active player", game.getActivePlayer());
        this.nextState.execute(game, pit);
    }
}
