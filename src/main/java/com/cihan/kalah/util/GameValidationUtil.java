package com.cihan.kalah.util;

import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameValidationUtil {
    public static void validateParameters(String gameId, Integer pitId) {
        if (StringUtils.isBlank(gameId)) {
            throw new GameException("Parameters couldn't null");
        }
        Optional<Integer> anyInvalidInt = Stream.of(null, 14, 7).filter(i -> pitId == i).findAny();
        if (anyInvalidInt.isPresent()) {
            throw new GameException("Invalid pits");
        }
    }

    public static int getHousePitId(PlayerId playerId) {
        return PlayerId.A == playerId ? GameConstant.PIT_MEDIAN_ID : GameConstant.PIT_END_ID;
    }

    public static PlayerId getOppositePlayerId(PlayerId playerId) {
        return playerId == PlayerId.A ? PlayerId.B : PlayerId.A;
    }


    public static void moveValidation(Game game, Integer pitId) {
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
