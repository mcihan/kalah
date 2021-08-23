package com.cihan.kalah.controller.dto;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.util.ResponseUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartGameResponseTest {

    @Test
    void shouldCreateStartGameResponseProperly() {
        int port = MockDataGenerator.getSystemPort();
        Game game = MockDataGenerator.generateGame();
        String gameUrl = ResponseUtil.generateGameUrl(game, port);

        StartGameResponse response = new StartGameResponse(game, port);

        assertEquals(game.getId(), response.getId());
        assertEquals(gameUrl, response.getUri());
    }

}