package com.cihan.kalah.service.engine;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;
import com.cihan.kalah.domain.Player;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cihan dogan  on 24.08.2021
 */

@Slf4j
public class LastPitRuleState extends GameState {
    @Override
    public void execute(Game game, Pit pit) {
        captureOppositePitStone(game, pit);
        turnToOtherPlayer(game, pit);
        this.nextState.execute(game, pit);
    }

    private void captureOppositePitStone(Game game, Pit pit) {
        if (shouldCaptureOppositePitStone(game, pit)) {
            game.captureOppositePitStone(pit.getId());
            log.info("Player {} captured opponent's pit", game.getActivePlayer());
        }
    }

    private void turnToOtherPlayer(Game game, Pit pit) {
        if (!pit.isActivePlayersHousePit(game.getActivePlayer())) {
            game.turnToOtherPlayer();
            log.info("Turn to Player {}", game.getActivePlayer());
        }
    }

    private boolean shouldCaptureOppositePitStone(Game game, Pit pit) {
        Player activePlayer = game.getActivePlayer();
        return pit.isPitOwner(activePlayer) && pit.isBoardPit() && pit.getStoneCount() == 1;
    }
}
