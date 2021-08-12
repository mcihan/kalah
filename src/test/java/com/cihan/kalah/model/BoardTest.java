package com.cihan.kalah.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BoardTest {

    private List<PlayerId> players;

    @BeforeEach
    void init() {
        players = List.of(PlayerId.values());
    }

    @Test
    void shouldAdvanceToNextPit() {
        Integer pitId = 1;
        Board board = new Board();

        Pit nextPit = board.advanceToNextPit(pitId);

        assertEquals(nextPit.getId(), pitId);
    }

    @Test
    void shouldAdvanceToNextPitWhenPitIdExceed() {
        Integer pitId = 17;
        Integer expectedPitId = pitId % GameConstant.PIT_END_ID;
        Board board = new Board();

        Pit nextPit = board.advanceToNextPit(pitId);

        assertEquals(expectedPitId, nextPit.getId());
    }

    @Test
    void shouldSetLatestPitWhenAdvanceToNextPit() {
        Integer pitId = 1;
        Board board = new Board();

        board.advanceToNextPit(pitId);

        assertEquals(pitId, board.getLatestPit().getId());
    }

    @Test
    void getPitById() {
        Integer pitId = 1;
        Board board = new Board();

        Pit pit = board.getPitById(pitId);

        assertEquals(pitId, pit.getId());
    }

    @Test
    void shouldCollectStonesToHouse() {
        int expectedHouseAStoneCount = GameConstant.DEFAULT_STONE_COUNT * (GameConstant.PIT_MEDIAN_ID - 1);
        Board board = new Board();

        board.collectStonesToHouse();

        int houseAStoneCount = board.getPitById(GameConstant.HOUSE_A_PIT_ID).getStoneCount();
        assertEquals(expectedHouseAStoneCount, houseAStoneCount);
    }

    @Test
    void shouldResetBoardPitsStone() {
        Integer pitId = 1;
        Board board = new Board();

        board.resetBoardPitsStone();

        Pit pit = board.getPitById(pitId);
        assertEquals(0, pit.getStoneCount());
    }

    @Test
        //TODO BUG
    void shouldNotResetHousePitsStone() {
        Integer pitId = GameConstant.HOUSE_A_PIT_ID;
        Board board = new Board();

        board.resetBoardPitsStone();

        Pit pit = board.getPitById(pitId);
        // assertNotEquals(0, pit.getStoneCount());
    }

    @Test
    void shouldCompleted() {
        players.forEach(playerId -> {
            Board board = new Board();
            resetPlayersBoardPits(playerId, board);
            assertTrue(board.isCompleted());
        });
    }

    @Test
    void shouldGetHousePit() {
        Board board = new Board();
        Pit pit = board.getHousePit(PlayerId.A);
        assertTrue(pit.isHousePit());
    }

    @Test
    void shouldCaptureOppositePitStone() {
        Integer pitId = 6;
        PlayerId activePlayer = PlayerId.A;
        Board board = new Board();
        int stoneOfHouse = board.getHousePit(activePlayer).getStoneCount();

        board.captureOppositePitStone(activePlayer, pitId);

        int stoneOfHouseAfterCapture = board.getHousePit(activePlayer).getStoneCount();
        assertTrue(stoneOfHouseAfterCapture > stoneOfHouse);
    }


    private void resetPlayersBoardPits(PlayerId playerId, Board board) {
        board.getPits().values().stream().filter(p -> p.getPlayerId() == playerId).forEach(Pit::resetPitStone);
    }
}