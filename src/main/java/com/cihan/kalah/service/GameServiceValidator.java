package com.cihan.kalah.service;

import com.cihan.kalah.exception.ExceptionConstant;
import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.GameStatus;
import com.cihan.kalah.model.Pit;
import com.cihan.kalah.model.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class GameServiceValidator {

    static void validateMove(Game game, Integer pitId) {
        validatePlayFinishedGame(game);
        validateTurn(game, pitId);
        validatePit(game, pitId);
    }

    private static void validatePlayFinishedGame(Game game) {
        if (game.getGameStatus() == GameStatus.FINISH) {
            throw new GameException(ExceptionConstant.CANT_PLAY_FINISHED_GAME);
        }
    }

    private static void validateTurn(Game game, Integer pitId) {
        Pit pit = game.getBoard().getPitById(pitId);
        Player activePlayer = game.getActivePlayer();
        if (activePlayer != null && !pit.isPitOwner(activePlayer)) {
            throw new GameException(ExceptionConstant.IT_IS_OPPONENT_TURN);
        }
    }

    private static void validatePit(Game game, Integer pitId) {
        Pit pit = game.getBoard().getPitById(pitId);

        if (pit.isHousePit()) {
            throw new GameException(ExceptionConstant.CANT_MOVE_HOUSE);
        }

        if (pit.getStoneCount() == 0) {
            throw new GameException(ExceptionConstant.CANT_MOVE_EMPTY_PIT);
        }
    }
}