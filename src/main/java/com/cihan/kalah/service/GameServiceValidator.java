package com.cihan.kalah.service;

import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.GameStatus;
import com.cihan.kalah.model.Pit;
import com.cihan.kalah.model.PlayerId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class GameServiceValidator {

    static void validateMove(Game game, Integer pitId) {
        attemptPlayFinishGame(game);
        validateTurn(game, pitId);
        validatePit(game, pitId);
    }

    private static void attemptPlayFinishGame(Game game) {
        if (game.getGameStatus() == GameStatus.FINISH) {
            throw new GameException("Game Finished!  Winner:" + game.getWinner());
        }
    }

    private static void validateTurn(Game game, Integer pitId) {
        Pit pit = game.getBoard().getPitById(pitId);
        PlayerId activePlayer = game.getActivePlayer();
        if (activePlayer != null && !pit.isPitOwner(activePlayer)) {
            throw new GameException("You can't move, It is your opponent's turn!");
        }
    }

    private static void validatePit(Game game, Integer pitId) {
        Pit pit = game.getBoard().getPitById(pitId);

        if (pit.isHousePit()) {
            throw new GameException("You can't distribute house pit!");
        }

        if (pit.getStoneCount() == 0) {
            throw new GameException("You can't distribute empty pit");
        }
    }
}