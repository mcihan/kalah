package com.cihan.kalah.controller;

import com.cihan.kalah.exception.ExceptionConstant;
import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.model.GameConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestValidatorTest {

    @Test
    void shouldValidateEmptyGameId() {
        GameException exception = Assertions.assertThrows(GameException.class,
                () -> RequestValidator.validateParameters(null, 1));
        assertEquals(ExceptionConstant.EMPTY_GAME_ID, exception.getMessage());
    }

    @Test
    void shouldValidateEmptyPitId() {
        String gameId = UUID.randomUUID().toString();
        Integer pitId = null;

        GameException exception = Assertions.assertThrows(GameException.class,
                () -> RequestValidator.validateParameters(gameId, pitId));
        assertEquals(ExceptionConstant.EMPTY_PIT_ID, exception.getMessage());
    }

    @Test
    void shouldValidateStartFromHouse() {
        String gameId = UUID.randomUUID().toString();

        List.of(GameConstant.HOUSE_A_PIT_ID, GameConstant.HOUSE_B_PIT_ID)
                .stream()
                .forEach(housePitId -> {
                    GameException exception = Assertions.assertThrows(GameException.class,
                            () -> RequestValidator.validateParameters(gameId, housePitId));
                    assertEquals(ExceptionConstant.NOT_START_FROM_HOUSE, exception.getMessage());
                });
    }

    @Test
    void shouldValidateInvalidPitId() {
        String gameId = UUID.randomUUID().toString();

        List.of(GameConstant.PIT_START_ID - 1, GameConstant.PIT_END_ID + 1)
                .stream()
                .forEach(housePitId -> {
                    GameException exception = Assertions.assertThrows(GameException.class,
                            () -> RequestValidator.validateParameters(gameId, housePitId));
                    assertEquals(ExceptionConstant.INVALID_PIT_ID, exception.getMessage());
                });
    }
}