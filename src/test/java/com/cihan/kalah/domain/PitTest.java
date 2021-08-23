package com.cihan.kalah.domain;

import com.cihan.kalah.generator.MockDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PitTest {

    private Player activePlayer;
    private Player opponentPlayer;
    private Pit activePlayersPit;
    private Pit houseA;

    @BeforeEach
    void init() {
        Map<Integer, Pit> pits = MockDataGenerator.generatePits();
        activePlayer = Player.A;
        opponentPlayer = Player.B;
        activePlayersPit = pits.get(GameConstant.PIT_START_ID);
        houseA = pits.get(GameConstant.HOUSE_A_PIT_ID);
    }

    @Test
    void isDistributablePit() {
        assertTrue(activePlayersPit.isDistributablePit(activePlayer));
        assertTrue(activePlayersPit.isDistributablePit(opponentPlayer));
        assertTrue(houseA.isDistributablePit(activePlayer));
        assertFalse(houseA.isDistributablePit(opponentPlayer));
    }

    @Test
    void isPitOwner() {
        assertTrue(activePlayersPit.isPitOwner(activePlayer));
        assertFalse(activePlayersPit.isPitOwner(opponentPlayer));
    }

    @Test
    void isHousePit() {
        assertTrue(houseA.isHousePit());
        assertFalse(activePlayersPit.isHousePit());
    }

    @Test
    void isActivePlayersHousePit() {
        assertTrue(houseA.isActivePlayersHousePit(activePlayer));
        assertFalse(houseA.isActivePlayersHousePit(opponentPlayer));
        assertFalse(activePlayersPit.isActivePlayersHousePit(activePlayer));
    }

    @Test
    void isBoardPit() {
        assertTrue(activePlayersPit.isBoardPit());
        assertFalse(houseA.isBoardPit());
    }

    @Test
    void shouldIncreasePitStone() {
        int stoneCount = activePlayersPit.getStoneCount();
        int expectedStoneCount = stoneCount + 1;

        activePlayersPit.increasePitStone();

        assertEquals(expectedStoneCount, activePlayersPit.getStoneCount());
    }

    @Test
    void shouldAddStoneToPit() {
        int stoneCount = activePlayersPit.getStoneCount();
        int addedStone = 5;
        int expectedStoneCount = stoneCount + addedStone;

        activePlayersPit.addStoneToPit(addedStone);

        assertEquals(expectedStoneCount, activePlayersPit.getStoneCount());
    }

    @Test
    void shouldResetPitStone() {
        int expectedStoneCount = 0;

        activePlayersPit.resetPitStone();

        assertEquals(expectedStoneCount, activePlayersPit.getStoneCount());
    }

    @Test
    void shouldGetStoneCount() {
        int stoneCount = activePlayersPit.getStoneCount();
        assertTrue(stoneCount >= 0);
    }

    @Test
    void shouldGetPitType() {
        assertNotNull(activePlayersPit.getPitType());
    }


}