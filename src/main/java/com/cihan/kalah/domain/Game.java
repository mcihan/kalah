package com.cihan.kalah.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Getter
@Slf4j
public class Game {
    private String id;
    private Player winner;
    private Player activePlayer;
    private GameStatus gameStatus;
    private Board board;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.gameStatus = GameStatus.START;
        this.board = new Board();
    }

    public void turnToOtherPlayer() {
        this.activePlayer = this.activePlayer == Player.A ? Player.B : Player.A;
    }

    public void initActivePlayerByPitId(Integer pitId) {
        this.activePlayer = this.board.getPitById(pitId).getPlayer();
    }

    public Pit moveOn(Integer pitId) {
        return this.board.advanceToNextPit(pitId);
    }

    public void determineWinner() {
        int houseAStones = this.board.getHousePit(Player.A).getStoneCount();
        int houseBStones = this.board.getHousePit(Player.B).getStoneCount();
        if (houseAStones == houseBStones) {
            log.info("Game ended in a draw!");
            return;
        }
        this.winner = houseAStones > houseBStones ? Player.A : Player.B;
    }

    public boolean isOver() {
        return this.board.isCompleted();
    }

    public void finish() {
        this.gameStatus = GameStatus.FINISH;
    }

    public void captureOppositePitStone(Integer pitId) {
        this.board.captureOppositePitStone(this.activePlayer, pitId);
    }

    public void collectStones() {
        board.collectStonesToHouse();
        board.resetBoardPitsStone();
    }
}
