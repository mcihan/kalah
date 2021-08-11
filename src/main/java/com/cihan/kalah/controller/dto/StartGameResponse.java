package com.cihan.kalah.controller.dto;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "StartGameResponse", description = "Example Start Game Response")
public class StartGameResponse {
    private final String id;
    private final String uri;

    public StartGameResponse(final Game game, int port) {
        this.id = game.getId();
        this.uri = ResponseUtil.generateGameUrl(game, port);
    }
}
