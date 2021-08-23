package com.cihan.kalah.domain;

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
    private final ConcurrentMap<Integer, Pit> pits;

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
        Integer nextPitId = getNextPitId(pitId);
        return pits.get(nextPitId);
    }

    private Integer getNextPitId(Integer pitId) {
        if (pitId != GameConstant.PIT_END_ID) {
            return pitId % GameConstant.PIT_END_ID + 1;
        }
        return 1;
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
