package com.cihan.kalah.model;

import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Board {
    private ConcurrentMap<Integer, Pit> pits;

    Board() {
        this.pits = initPits();
    }

    public boolean isCompleted() {
        return getPlayerPitsStoneSum(PlayerId.A) == 0 || getPlayerPitsStoneSum(PlayerId.B) == 0;
    }

    private ConcurrentMap<Integer, Pit> initPits() {
        return IntStream.rangeClosed(GameConstant.PIT_START_ID, GameConstant.PIT_END_ID)
                .boxed().map(Pit::new)
                .collect(Collectors.toConcurrentMap(Pit::getId, Function.identity()));
    }

    public int getPlayerPitsStoneSum(PlayerId playerId) {
        return pits.values().stream()
                .filter(p -> p.getPitType() == PitType.BOARD)
                .filter(p -> p.getPlayerId() == playerId)
                .mapToInt(Pit::getStoneCount).sum();
    }

    public Pit getHousePit(PlayerId playerId) {
        return pits.values().stream()
                .filter(p -> p.getPitType() == PitType.HOUSE)
                .filter(p -> p.getPlayerId() == playerId)
                .findFirst().get(); // TODO throw

    }

    public Pit getNextPit(Integer pitId) {
        int nextPitId = pitId <= GameConstant.PIT_END_ID ? pitId : pitId % GameConstant.PIT_END_ID;
        return pits.get(nextPitId);
    }


    Pit getCurrentPit(Integer pitId) {
        return pits.get(pitId);
    }

    public void collectStonesToHouse() {
        Arrays.stream(PlayerId.values()).forEach(playerId -> {
            int stone = getPlayerPitsStoneSum(playerId);
             getHousePit(playerId).addStoneToPit(stone);
        });
    }

    public void resetBoardPitsStone() {
        pits.values().stream().filter(Pit::isBoardPit).forEach(Pit::resetPitStone);
    }
}
