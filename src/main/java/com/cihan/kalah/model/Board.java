package com.cihan.kalah.model;

import com.cihan.kalah.exception.GameException;
import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Board {
    private ConcurrentMap<Integer, Pit> pits;
    private Pit latestPit;

    Board() {
        this.pits = initPits();
    }

    public Pit getNextPit(Integer pitId) {
        int nextPitId = pitId <= GameConstant.PIT_END_ID ? pitId : pitId % GameConstant.PIT_END_ID;
        latestPit = pits.get(nextPitId);
        return latestPit;
    }

    public Pit getPitById(Integer pitId) {
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

    boolean isCompleted() {
        return getPlayerPitsStoneSum(PlayerId.A) == 0 || getPlayerPitsStoneSum(PlayerId.B) == 0;
    }

    Pit getHousePit(PlayerId playerId) {
        return pits.values().stream()
                .filter(p -> p.getPitType() == PitType.HOUSE)
                .filter(p -> p.getPlayerId() == playerId)
                .findFirst().orElseThrow(() -> new GameException("Pit is not found!"));
    }

    void captureOppositePitStone(PlayerId activePlayer, Integer pitId) {
        Pit pit = getPitById(pitId);
        Pit oppositePit = getOppositePit(pitId);
        Pit housePit = getHousePit(activePlayer);
        housePit.addStoneToPit(pit.getStoneCount() + oppositePit.getStoneCount());
        pit.resetPitStone();
        oppositePit.resetPitStone();
    }

    private ConcurrentMap<Integer, Pit> initPits() {
        return IntStream.rangeClosed(GameConstant.PIT_START_ID, GameConstant.PIT_END_ID)
                .boxed().map(Pit::new)
                .collect(Collectors.toConcurrentMap(Pit::getId, Function.identity()));
    }

    private int getPlayerPitsStoneSum(PlayerId playerId) {
        return pits.values().stream()
                .filter(p -> p.getPitType() == PitType.BOARD)
                .filter(p -> p.getPlayerId() == playerId)
                .mapToInt(Pit::getStoneCount).sum();
    }

    private Pit getOppositePit(int pitId) {
        return pits.get(GameConstant.PIT_END_ID - pitId);
    }
}
