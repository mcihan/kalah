package com.cihan.kalah.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
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
        activePlayer = activePlayer == PlayerId.A ? PlayerId.B : PlayerId.A;
    }

    public void initActivePlayerByPitId(Integer pitId) {
        setActivePlayer(this.board.getPitById(pitId).getPlayerId());
    }

    public boolean isOver() {
        return board.isCompleted();
    }

    public void decideWinner() {
        int houseAStones = board.getHousePit(PlayerId.A).getStoneCount();
        int houseBStones = board.getHousePit(PlayerId.B).getStoneCount();
        if (houseAStones == houseBStones) {
            log.info("Game ended in a draw!");
            return;
        }
        PlayerId winner = houseAStones > houseBStones ? PlayerId.A : PlayerId.B;
        setWinner(winner);
    }

    public void finish() {
        setGameStatus(GameStatus.FINISH);
    }

    public void captureOppositePitStone(Integer pitId) {
        board.captureOppositePitStone(activePlayer, pitId);
    }
}
