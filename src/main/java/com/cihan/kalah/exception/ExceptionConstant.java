package com.cihan.kalah.exception;

import com.cihan.kalah.model.GameConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author cihan dogan  on 11.08.2021
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstant {
    public static final String EMPTY_GAME_ID = "gameId can not be empty!";
    public static final String EMPTY_PIT_ID = "pitId can not be empty!";
    public static final String NOT_START_FROM_HOUSE = "You can not start from house";
    public static final String INVALID_PIT_ID = String.format("pitId should be between %d and %d", GameConstant.PIT_START_ID, GameConstant.PIT_END_ID);

    public static final String CANT_PLAY_FINISHED_GAME = "Game Finished!, You can not play finished game!";
    public static final String IT_IS_OPPONENT_TURN = "You can't move, It is your opponent's turn!";
    public static final String CANT_MOVE_HOUSE = "You can't distribute house pit!";
    public static final String CANT_MOVE_EMPTY_PIT = "You can't distribute empty pit!";

    public static final String GAME_NOT_FOUND = "Game is not found!";
}
