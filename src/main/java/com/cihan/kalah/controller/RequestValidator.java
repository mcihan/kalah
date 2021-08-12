package com.cihan.kalah.controller;

import com.cihan.kalah.exception.ExceptionConstant;
import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.GameConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class RequestValidator {
    static void validateParameters(String gameId, Integer pitId) {
        if (StringUtils.isBlank(gameId)) {
            throw new GameException(ExceptionConstant.EMPTY_GAME_ID);
        }

        if (pitId == null) {
            throw new GameException(ExceptionConstant.EMPTY_PIT_ID);
        }

        if (pitId == GameConstant.HOUSE_A_PIT_ID || pitId == GameConstant.HOUSE_B_PIT_ID) {
            throw new GameException(ExceptionConstant.CANT_MOVE_HOUSE);
        }

        if (pitId < GameConstant.PIT_START_ID || pitId > GameConstant.PIT_END_ID) {
            throw new GameException(ExceptionConstant.INVALID_PIT_ID);
        }
    }
}



