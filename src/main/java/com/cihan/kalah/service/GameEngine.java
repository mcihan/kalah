package com.cihan.kalah.service;

import com.cihan.kalah.model.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class GameEngine {

    void move(Game game, Integer pitId) {
        if (game.getActivePlayer() == null) {
            game.initActivePlayerByPitId(pitId);
        }
        distributeStones(pitId, game);
        if (game.isOver()) {
            completeGame(game);
        }
    }

    private void distributeStones(Integer pitId, Game game) {
        ConcurrentMap<Integer, Pit> pits = game.getBoard().getPits();
/*        System.out.println("pitId = " + pitId);
        printPits(pits, "before");*/
        Pit currentPit = pits.get(pitId);
        int currentPitStone = currentPit.getStoneCount();
        currentPit.resetPitStone();

        while (currentPitStone-- > 1) {
            Pit pit = game.getBoard().getNextPit(++pitId);
            if (pit.isDistributablePit(game.getActivePlayer())) {
                pit.increasePitStone();
            }
        }

        Pit latestPit = pits.get(pitId);
        captureOppositePitStone(game, latestPit);
        turnToOtherPlayer(game, latestPit);
        printPits(pits, "-after");
    }

    private void captureOppositePitStone(Game game, Pit pit) {
        if (shouldCaptureOppositePitStone(game, pit)) {
            game.captureOppositePitStone(pit.getId());
        }
    }



    private void turnToOtherPlayer(Game game, Pit pit) {
        if (pit.isActivePlayersHousePit(game.getActivePlayer())) {
            pit.increasePitStone();
        }else {
            game.turnToOtherPlayer();
        }
    }

    private void completeGame(Game game) {
        Board board = game.getBoard();
        board.collectStonesToHouse();
        board.resetBoardPitsStone();

        game.decideWinner();
        game.finish();
    }

    private boolean shouldCaptureOppositePitStone(Game game, Pit pit) {
        PlayerId activePlayer = game.getActivePlayer();
        return pit.isPitOwner(activePlayer) && pit.isBoardPit() && pit.getStoneCount() == 1;
    }


    // TODO MULTI-THREAD - Atomic Int
    //  Game game = kalahRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game Not Found !"));

// TODO  turnPlayerRule, distirubte rule, last stone rules

    private void printPits(ConcurrentMap<Integer, Pit> pits, String text) {
        Map<Integer, Integer> collect = pits.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getStoneCount()));
        System.out.println(text + " distribute pits = " + collect);
    }
}
