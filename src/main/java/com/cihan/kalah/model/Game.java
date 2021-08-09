package com.cihan.kalah.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class Game {
    private String id;
    private String url;
    private List<Player> players;
    private PlayerId winner;
    private PlayerId activePlayer;
    private GameStatus gameStatus;
    private Board board;

    public Game(int port) {
        this.id = "1234"; //UUID.randomUUID().toString();
        this.players = Stream.of(new Player("Player A", PlayerId.A), new Player("Player B", PlayerId.B))
                .collect(Collectors.toList());
        this.gameStatus = GameStatus.START;
        this.board = new Board();
        this.url = generateGameUrl(port);
    }

    private String generateGameUrl(int port) {
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(), port, this.id);
    }

    public void turnToOtherPlayer() {
        PlayerId playerId = activePlayer == PlayerId.A ? PlayerId.B : PlayerId.A;
        setActivePlayer(playerId);
    }
}
