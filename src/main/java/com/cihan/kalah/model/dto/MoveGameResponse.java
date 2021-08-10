package com.cihan.kalah.model.dto;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.util.ResponseUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@Schema(name = "MoveGameResponse", description = "Example Move Game Response")
public class MoveGameResponse {
    private String id;
    private String url;
    private Map<Integer, Integer> status;


    public MoveGameResponse(Game game, int port) {
        this.id = game.getId();
        this.url = ResponseUtil.generateGameUrl(game, port);
        this.status = ResponseUtil.getIntegerIntegerMap(game);
    }


}
