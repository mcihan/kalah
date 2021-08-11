package com.cihan.kalah.controller;

import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.GameConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cihan dogan  on 11.08.2021
 */

public class RequestValidator {
    public static void validateParameters(String gameId, Integer pitId) {
        if (StringUtils.isBlank(gameId)) {
            throw new GameException("gameId can not be empty!");
        }

        if (pitId == null) {
            throw new GameException("pitId can not be empty!");
        }

        if (pitId % GameConstant.PIT_MEDIAN_ID == 0) {
            throw new GameException("You can not start from house");
        }

        if (pitId < GameConstant.PIT_START_ID && pitId > GameConstant.PIT_END_ID) {
            throw new GameException(String.format("pitId should be between %d and %d", GameConstant.PIT_START_ID, GameConstant.PIT_END_ID));
        }
    }
}



