package com.cihan.kalah.service;

import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.GameStatus;
import com.cihan.kalah.model.Pit;

public class GameServiceValidator {

        public static void validateMove(Game game, Integer pitId) {
            attemptPlayFinishGame(game);
            currentPitValidation(game, pitId);
        }

        private static void attemptPlayFinishGame(Game game) {
            if (game.getGameStatus() == GameStatus.FINISH) {
                throw new GameException("Game Finished!  Winner:" + game.getWinner());
            }
        }

        private static void currentPitValidation(Game game, Integer pitId) {
            Pit currentPit = game.getBoard().getPits().get(pitId);

            if (currentPit.getStoneCount() == 0) {
                throw new GameException("You can't distribute this pit");
            }

            if (game.getActivePlayer() != null && game.getActivePlayer() != currentPit.getPlayerId()) {
                throw new GameException("Turned Player !!!");
            }
        }
    }