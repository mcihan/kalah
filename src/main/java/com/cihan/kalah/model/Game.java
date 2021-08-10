package com.cihan.kalah.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Game {
    private String id;
    private List<Player> players; //TODO Player A, PlayerB
    private PlayerId winner;
    private PlayerId activePlayer;
    private GameStatus gameStatus;
    private Board board;

    public Game() {
        this.id = "1234"; //UUID.randomUUID().toString();
        this.players = Stream.of(new Player("Player A", PlayerId.A), new Player("Player B", PlayerId.B)).collect(Collectors.toList());
        this.gameStatus = GameStatus.START;
        this.board = new Board();
    }

    public void turnToOtherPlayer() {
        activePlayer = activePlayer ==  PlayerId.A ? PlayerId.B : PlayerId.A;
    }

    public void initActivePlayerByPitId(Integer pitId) {
        setActivePlayer(this.board.getCurrentPit(pitId).getPlayerId());
    }

    public boolean isOver() {
        return board.isCompleted();
    }
}
