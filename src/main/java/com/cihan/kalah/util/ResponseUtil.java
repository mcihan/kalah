package com.cihan.kalah.util;

import com.cihan.kalah.model.Game;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtil {
    public static Map<Integer, Integer> generateMoveGameStatus(Game game) {
        return game.getBoard().getPits().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getStoneCount()));
    }

    public static String generateGameUrl(Game game, int port) {
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(), port, game.getId());
    }
}
