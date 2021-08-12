package com.cihan.kalah.model;

import com.cihan.kalah.exception.ExceptionConstant;
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

    private ConcurrentMap<Integer, Pit> initPits() {
        return IntStream.rangeClosed(GameConstant.PIT_START_ID, GameConstant.PIT_END_ID)
                .boxed().map(Pit::new)
                .collect(Collectors.toConcurrentMap(Pit::getId, Function.identity()));
    }

    public void collectStonesToHouse() {
        Arrays.stream(Player.values()).forEach(player -> {
            int stone = getPlayerPitsStoneSum(player);
            getHousePit(player).addStoneToPit(stone);
        });
    }

    public void resetBoardPitsStone() {
        pits.values().stream().filter(Pit::isBoardPit).forEach(Pit::resetPitStone);
    }

    public Pit advanceToNextPit(Integer pitId) {
        int nextPitId = pitId <= GameConstant.PIT_END_ID ? pitId : pitId % GameConstant.PIT_END_ID;
        latestPit = pits.get(nextPitId);
        return latestPit;
    }

    void captureOppositePitStone(Player activePlayer, Integer pitId) {
        Pit pit = getPitById(pitId);
        Pit oppositePit = getOppositePit(pitId);
        Pit housePit = getHousePit(activePlayer);
        housePit.addStoneToPit(pit.getStoneCount() + oppositePit.getStoneCount());
        pit.resetPitStone();
        oppositePit.resetPitStone();
    }

    public Pit getPitById(Integer pitId) {
        return pits.get(pitId);
    }


    boolean isCompleted() {
        return getPlayerPitsStoneSum(Player.A) == 0 || getPlayerPitsStoneSum(Player.B) == 0;
    }

    Pit getHousePit(Player player) {
        return pits.values().stream()
                .filter(Pit::isHousePit)
                .filter(p -> p.getPlayer() == player)
                .findFirst().orElseThrow(() -> new GameException(ExceptionConstant.PIT_NOT_FOUND));
    }

    Pit getOppositePit(int pitId) {
        return pits.get(GameConstant.PIT_END_ID - pitId);
    }


    private int getPlayerPitsStoneSum(Player player) {
        return pits.values().stream()
                .filter(Pit::isBoardPit)
                .filter(p -> p.getPlayer() == player)
                .mapToInt(Pit::getStoneCount).sum();
    }

}
