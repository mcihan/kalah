package com.cihan.kalah.service.engine;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cihan dogan  on 24.08.2021
 */

@Slf4j
public class CompleteGameState extends GameState {
    @Override
    public void execute(Game game, Pit pit) {
        if (game.isOver()) {
            game.collectStones();
            game.determineWinner();
            game.finish();
            log.info("Game finished! The winner {}", game.getWinner());
        }
    }
}
