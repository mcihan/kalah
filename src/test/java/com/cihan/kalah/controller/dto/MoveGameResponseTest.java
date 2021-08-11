package com.cihan.kalah.controller.dto;

import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.util.ResponseUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveGameResponseTest {

    @Test
    void shouldCreateMoveGameResponseProperly() {
        int port = MockDataGenerator.getSystemPort();
        Game game = MockDataGenerator.generateGame();
        String gameUrl = ResponseUtil.generateGameUrl(game, port);
        Map<Integer, Integer> status = ResponseUtil.generateMoveGameStatus(game);

        MoveGameResponse response = new MoveGameResponse(game, port);

        assertEquals(game.getId(), response.getId());
        assertEquals(gameUrl, response.getUrl());
        assertEquals(status, response.getStatus());
    }
}