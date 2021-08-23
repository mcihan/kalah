package com.cihan.kalah.service.engine;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cihan dogan  on 24.08.2021
 */

@Slf4j
public class DistributeStonesState extends GameState {
    @Override
    public void execute(Game game, Pit pit) {
        Integer pitId = pit.getId();
        int currentPitStone = pit.getStoneCount();
        pit.resetPitStone();
        Pit currentPit = pit;
        while (currentPitStone > 0) {
            currentPit = game.moveOn(pitId);
            pitId = currentPit.getId();
            if (currentPit.isDistributablePit(game.getActivePlayer())) {
                currentPit.increasePitStone();
                currentPitStone--;
            }
        }
        log.info("Stones are distributed for the Player : {}", game.getActivePlayer());
        this.nextState.execute(game, currentPit);
    }
}
