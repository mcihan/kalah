package com.cihan.kalah.service;

import com.cihan.kalah.model.*;
import lombok.extern.slf4j.Slf4j;
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
        System.out.println("pitId = " + pitId);
        printPits(pits, "before");
        Pit currentPit = pits.get(pitId);
        int stoneOfCurrentPit = currentPit.getStoneCount();
        currentPit.resetPitStone();

        PlayerId activePlayer = game.getActivePlayer();
        boolean shouldTurnToOtherPlayer = true;

        while (stoneOfCurrentPit > 0) {
            Pit pit = game.getBoard().getNextPit(++pitId);
            if (pit.isOppositeHouse(activePlayer)) {
                continue;
            }
            pit.increasePitStone();
            boolean isLastStoneOfCurrentPit = stoneOfCurrentPit == 1;
            if (pit.isPitOwner(activePlayer) && isLastStoneOfCurrentPit) {
                if (pit.isHousePit()) {
                    shouldTurnToOtherPlayer = false;
                } else if (pit.getStoneCount() == 1) {
                    Pit oppositePit = pits.get(GameConstant.PIT_END_ID - pit.getId());
                    Pit housePit = game.getBoard().getHousePit(activePlayer);
                    housePit.setStoneCount(housePit.getStoneCount() + pit.getStoneCount() + oppositePit.getStoneCount());
                    pit.resetPitStone();
                    oppositePit.resetPitStone();
                }
            }
            stoneOfCurrentPit--;
        }
        if (shouldTurnToOtherPlayer) {
            game.turnToOtherPlayer();
        }
        printPits(pits, "-after");
    }

    private void completeGame(Game game) {
        Board board = game.getBoard();
        int sumA = board.getPlayerPitStoneSum(PlayerId.A);
        int sumB = board.getPlayerPitStoneSum(PlayerId.B);

        Pit houseA = board.getHousePit(PlayerId.A);
        houseA.setStoneCount(houseA.getStoneCount() + sumA);

        Pit houseB = board.getHousePit(PlayerId.B);
        houseB.setStoneCount(houseB.getStoneCount() + sumB);

        PlayerId winner = houseA.getStoneCount() > houseB.getStoneCount() ? PlayerId.A : PlayerId.B;
        board.getPits().values().stream().filter(Pit::isBoardPit).forEach(Pit::resetPitStone);

        game.setWinner(winner);
        game.setGameStatus(GameStatus.FINISH);
    }

    // TODO ADD OWN EXCEPTIONS
    // TODO MULTI-THREAD - Atomic Int
    //  Game game = kalahRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game Not Found !"));

// TODO  turnPlayerRule, distirubte rule, last stone rules

    private void printPits(ConcurrentMap<Integer, Pit> pits, String text) {
        Map<Integer, Integer> collect = pits.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getStoneCount()));
        System.out.println(text + " distribute pits = " + collect);
    }
}
