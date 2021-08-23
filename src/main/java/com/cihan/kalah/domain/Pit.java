package com.cihan.kalah.domain;

import lombok.Getter;

@Getter
public class Pit {
    private Integer id;
    private int stoneCount;
    private PitType pitType;
    private Player player;

    Pit(int id) {
        this.id = id;
        this.pitType = initPitType();
        this.player = initPlayer();
        this.stoneCount = initStoneCount();
    }

    private PitType initPitType() {
        return this.id % GameConstant.PIT_MEDIAN_ID == 0 ? PitType.HOUSE : PitType.BOARD;
    }

    private Player initPlayer() {
        return this.id <= GameConstant.PIT_MEDIAN_ID ? Player.A : Player.B;
    }

    private int initStoneCount() {
        return isHousePit() ? GameConstant.HOUSE_STONE_COUNT : GameConstant.DEFAULT_STONE_COUNT;
    }

    public boolean isDistributablePit(Player player) {
        return !isOpponentsHouse(player);
    }

    private boolean isOpponentsHouse(Player player) {
        return isHousePit() && !isPitOwner(player);
    }

    public boolean isPitOwner(Player player) {
        return player == this.player;
    }

    public boolean isHousePit() {
        return pitType == PitType.HOUSE;
    }

    public boolean isActivePlayersHousePit(Player player) {
        return isPitOwner(player) && isHousePit();
    }

    public boolean isBoardPit() {
        return !isHousePit();
    }

    public void increasePitStone() {
        addStoneToPit(1);
    }

    void addStoneToPit(int stone) {
        this.stoneCount = this.stoneCount + stone;
    }

    public void resetPitStone() {
        this.stoneCount = 0;
    }

    private enum PitType {
        BOARD,
        HOUSE
    }

}
