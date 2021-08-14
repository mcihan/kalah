package com.cihan.kalah.service;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.GameConstant;
import com.cihan.kalah.model.Pit;
import com.cihan.kalah.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameEngine {

    void executeGameFlow(Game game, Integer pitId) {
        determineToActivePlayer(game, pitId);
        distributeStones(game, pitId);
        applyLastPitRules(game);
        completeGame(game);
    }

    private void determineToActivePlayer(Game game, Integer pitId) {
        if (game.getActivePlayer() == null) {
            game.initActivePlayerByPitId(pitId);
        }
        log.info("Player {} is determined as a Active player", game.getActivePlayer());
    }

    private void distributeStones(Game game, Integer pitId) {
        Pit pit = game.getBoard().getPitById(pitId);
        int currentPitStone = pit.getStoneCount();
        pit.resetPitStone();
        while (currentPitStone > 0) {
            Pit currentPit = game.moveOn(pitId);
            pitId = currentPit.getId();
            if (currentPit.isDistributablePit(game.getActivePlayer())) {
                currentPit.increasePitStone();
                currentPitStone--;
            }
        }
        log.info("Stones are distributed for the Player : {}", game.getActivePlayer());
    }

    private void applyLastPitRules(Game game) {
        Pit latestPit = game.getBoard().getLatestPit();
        captureOppositePitStone(game, latestPit);
        turnToOtherPlayer(game, latestPit);
    }

    private void completeGame(Game game) {
        if (game.isOver()) {
            game.collectStones();
            game.determineWinner();
            game.finish();
            log.info("Game finished! The winner {}", game.getWinner());
        }
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
