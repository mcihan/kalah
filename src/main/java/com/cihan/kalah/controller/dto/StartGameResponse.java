package com.cihan.kalah.controller.dto;

import com.cihan.kalah.model.Game;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.net.InetAddress;

@Data
@Schema(name = "StartGameResponse", description = "Example Start Game Response")
public class StartGameResponse {
    private final String id;
    private final String uri;

    public StartGameResponse(final Game game, int port) {
        this.id = game.getId();
        this.uri = generateGameUrl(game, port);
    }

    private static String generateGameUrl(Game game, int port) {
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(), port, game.getId());
    }

}
