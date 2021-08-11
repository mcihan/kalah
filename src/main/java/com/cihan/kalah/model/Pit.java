package com.cihan.kalah.model;

import lombok.Data;

@Data
public class Pit {
    private Integer id;
    private int stoneCount;
    private PitType pitType;
    private PlayerId playerId;

    public Pit(int id) {
        this.id = id;
        this.pitType = initPitType();
        this.playerId = initPlayerId();
        this.stoneCount = initStoneCount();
    }

    private PitType initPitType() {
        return this.id % GameConstant.PIT_MEDIAN_ID == 0 ? PitType.HOUSE : PitType.BOARD;
    }

    private PlayerId initPlayerId() {
        return this.id <= GameConstant.PIT_MEDIAN_ID ? PlayerId.A : PlayerId.B;
    }

    private int initStoneCount() {
        return isHousePit() ? GameConstant.HOUSE_STONE_COUNT : GameConstant.DEFAULT_STONE_COUNT;
    }

    public boolean isDistributablePit(PlayerId playerId) {
        return !isOppositeHouse(playerId);
    }

    public boolean isOppositeHouse(PlayerId playerId) {
        return isHousePit() && !isPitOwner(playerId);
    }

    public boolean isPitOwner(PlayerId playerId) {
        return playerId == this.playerId;
    }

    public boolean isHousePit() {
        return pitType == PitType.HOUSE;
    }

    public boolean isActivePlayersHousePit(PlayerId playerId) {
        return isPitOwner(playerId) && isHousePit();
    }

    public boolean isBoardPit() {
        return !isHousePit();
    }

    public void increasePitStone() {
        addStoneToPit(1);
    }

    public void addStoneToPit(int stone) {
        setStoneCount(getStoneCount() + stone);
    }

    public Pit resetPitStone() {
        setStoneCount(0);
        return this;
    }

}
