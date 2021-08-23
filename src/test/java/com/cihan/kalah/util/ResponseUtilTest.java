package com.cihan.kalah.util;

import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseUtilTest {

    @Test
    void shouldGenerateMoveGameStatus() {
        int pitId = 1;
        Game game = MockDataGenerator.generateGame();
        Map<Integer, Integer> status = ResponseUtil.generateMoveGameStatus(game);
        Pit pit = game.getBoard().getPitById(pitId);
        assertEquals(pit.getStoneCount(), status.get(pitId));
    }

    @Test
    void shouldGenerateGameUrl() {
        Game game = MockDataGenerator.generateGame();
        String expectedGameUrl = "http://localhost:8080/games/"+game.getId();
        String gameUrl = ResponseUtil.generateGameUrl(game, 8080);
        assertEquals(expectedGameUrl, gameUrl);
    }
}