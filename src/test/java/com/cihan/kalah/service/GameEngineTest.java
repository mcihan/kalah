package com.cihan.kalah.service;

import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.model.Board;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.GameConstant;
import com.cihan.kalah.model.Pit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GameEngineTest {

    @InjectMocks
    private GameEngine gameEngine;

    @Test
    void shouldDetermineToActivePlayerWhenExecute() {
        Game game = MockDataGenerator.generateGame();
        Integer pitId = 1;

        gameEngine.executeGameFlow(game, pitId);

        assertNotNull(game.getActivePlayer());
    }

    @Test
    void shouldDistributeStonesWhenExecute() {
        Game game = MockDataGenerator.generateGame();
        Board board = game.getBoard();
        Integer pitId = 1;
        Integer oppositePitId = GameConstant.PIT_END_ID - pitId - 1;

        board.resetBoardPitsStone();
        Pit currentPit = board.getPitById(pitId);
        currentPit.increasePitStone();
        Pit oppositePit = board.getPitById(oppositePitId);
        oppositePit.increasePitStone();
        int expectedHouseStoneCount = currentPit.getStoneCount() + oppositePit.getStoneCount();

        Pit houseA = board.getPitById(GameConstant.HOUSE_A_PIT_ID);
        houseA.resetPitStone();

        gameEngine.executeGameFlow(game, pitId);

        assertEquals(expectedHouseStoneCount, houseA.getStoneCount());
    }


}