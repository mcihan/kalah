package com.cihan.kalah.service;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.Pit;
import com.cihan.kalah.model.Player;
import org.springframework.stereotype.Service;

@Service
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
    }

    private void distributeStones(Game game, Integer pitId) {
        Pit pit = game.getBoard().getPitById(pitId);
        int currentPitStone = pit.getStoneCount();
        pit.resetPitStone();
        while (currentPitStone-- > 0) {
            Pit currentPit = game.moveOn(++pitId);
            if (currentPit.isDistributablePit(game.getActivePlayer())) {
                currentPit.increasePitStone();
            }
        }
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
        }
    }

    private void captureOppositePitStone(Game game, Pit pit) {
        if (shouldCaptureOppositePitStone(game, pit)) {
            game.captureOppositePitStone(pit.getId());
        }
    }

    private void turnToOtherPlayer(Game game, Pit pit) {
        if (!pit.isActivePlayersHousePit(game.getActivePlayer())) {
            game.turnToOtherPlayer();
        }
    }

    private boolean shouldCaptureOppositePitStone(Game game, Pit pit) {
        Player activePlayer = game.getActivePlayer();
        return pit.isPitOwner(activePlayer) && pit.isBoardPit() && pit.getStoneCount() == 1;
    }

}
