package com.cihan.kalah.service;

import com.cihan.kalah.exception.ExceptionConstant;
import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.GameConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceValidatorTest {

    @Test
    void shouldValidatePlayFinishedGame() {
        Integer pitId = 1;
        Game finishedGame = MockDataGenerator.generateFinishedGame();

        GameException exception = assertThrows(GameException.class,
                () -> GameServiceValidator.validateMove(finishedGame, pitId));

        assertEquals(ExceptionConstant.CANT_PLAY_FINISHED_GAME, exception.getMessage());
    }

    @Test
    void shouldValidateTurn() {
        Integer pitId = 1;
        Game game = MockDataGenerator.generateGame();
        game.initActivePlayerByPitId(pitId);
        game.turnToOtherPlayer();

        GameException exception = assertThrows(GameException.class,
                () -> GameServiceValidator.validateMove(game, pitId));

        assertEquals(ExceptionConstant.IT_IS_OPPONENT_TURN, exception.getMessage());
    }

    @Test
    void shouldValidateMoveHousePit() {
        Integer pitId = GameConstant.HOUSE_A_PIT_ID;
        Game game = MockDataGenerator.generateGame();

        GameException exception = assertThrows(GameException.class,
                () -> GameServiceValidator.validateMove(game, pitId));

        assertEquals(ExceptionConstant.CANT_MOVE_HOUSE, exception.getMessage());
    }

    @Test
    void shouldValidateEmptyPit() {
        Integer pitId = 1;
        Game game = MockDataGenerator.generateGame();
        game.getBoard().getPitById(pitId).setStoneCount(0);

        GameException exception = assertThrows(GameException.class,
                () -> GameServiceValidator.validateMove(game, pitId));

        assertEquals(ExceptionConstant.CANT_MOVE_EMPTY_PIT, exception.getMessage());
    }

    @Test
    void shouldValidMoveWorkProperly() {
        Integer pitId = 1;
        Game game = MockDataGenerator.generateGame();
        assertDoesNotThrow(() -> GameServiceValidator.validateMove(game, pitId));
    }

}