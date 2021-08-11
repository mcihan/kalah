package com.cihan.kalah.service;

import com.cihan.kalah.model.Board;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.Pit;
import com.cihan.kalah.model.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class GameEngine {

    void executeGameFlow(Game game, Integer pitId) {
        determineToActivePlayer(game, pitId);
        distributeStones(game, pitId);
        applyLastPitRules(game, pitId);
        completeGame(game);
    }

    private void determineToActivePlayer(Game game, Integer pitId) {
        if (game.getActivePlayer() == null) {
            game.initActivePlayerByPitId(pitId);
        }
    }

    private void distributeStones(Game game, Integer pitId) {
        Board board = game.getBoard();
        Pit currentPit = board.getPitById(pitId);
        int currentPitStone = currentPit.getStoneCount();
        currentPit.resetPitStone();
        while (currentPitStone-- > 0) {
            Pit pit = board.getNextPit(++pitId);
            if (pit.isDistributablePit(game.getActivePlayer())) {
                pit.increasePitStone();
            }
        }
    }

    private void applyLastPitRules(Game game, Integer pitId) {
        Pit latestPit = game.getBoard().getNextPit(pitId);
        captureOppositePitStone(game, latestPit);
        turnToOtherPlayer(game, latestPit);
    }

    private void completeGame(Game game) {
        if (game.isOver()) {
            Board board = game.getBoard();
            board.collectStonesToHouse();
            board.resetBoardPitsStone();
            game.decideWinner();
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
        PlayerId activePlayer = game.getActivePlayer();
        return pit.isPitOwner(activePlayer) && pit.isBoardPit() && pit.getStoneCount() == 1;
    }

}
