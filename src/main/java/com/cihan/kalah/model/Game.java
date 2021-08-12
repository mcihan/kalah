package com.cihan.kalah.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Slf4j
public class Game {
    private String id;
    private List<Player> players;
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
        this.activePlayer = this.activePlayer == PlayerId.A ? PlayerId.B : PlayerId.A;
    }

    public void initActivePlayerByPitId(Integer pitId) {
        this.activePlayer = this.board.getPitById(pitId).getPlayerId();
    }

    public boolean isOver() {
        return this.board.isCompleted();
    }

    public void determineWinner() {
        int houseAStones = this.board.getHousePit(PlayerId.A).getStoneCount();
        int houseBStones = this.board.getHousePit(PlayerId.B).getStoneCount();
        if (houseAStones == houseBStones) {
            log.info("Game ended in a draw!");
            return;
        }
        this.winner = houseAStones > houseBStones ? PlayerId.A : PlayerId.B;
    }

    public void finish() {
        this.gameStatus = GameStatus.FINISH;
    }

    public void captureOppositePitStone(Integer pitId) {
        this.board.captureOppositePitStone(this.activePlayer, pitId);
    }
}
