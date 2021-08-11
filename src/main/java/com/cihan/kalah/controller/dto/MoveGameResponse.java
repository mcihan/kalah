package com.cihan.kalah.controller.dto;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Map;

@Getter
@Schema(name = "MoveGameResponse", description = "Example Move Game Response")
public class MoveGameResponse {
    private String id;
    private String url;
    private Map<Integer, Integer> status;

    public MoveGameResponse(Game game, int port) {
        this.id = game.getId();
        this.url = ResponseUtil.generateGameUrl(game, port);
        this.status = ResponseUtil.generateMoveGameStatus(game);
    }

}
