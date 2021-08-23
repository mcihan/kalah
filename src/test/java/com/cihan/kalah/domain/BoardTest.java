package com.cihan.kalah.domain;

import com.cihan.kalah.generator.MockDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardTest {

    private List<Player> players;

    @BeforeEach
    void init() {
        players = List.of(Player.values());
    }

    @Test
    void shouldAdvanceToNextPit() {
        Integer pitId = GameConstant.PIT_START_ID;
        Board board = new Board();
        Integer expectedPitId = pitId + 1;
        Pit nextPit = board.advanceToNextPit(pitId);

        assertEquals(expectedPitId, nextPit.getId());
    }

    @Test
    void shouldAdvanceToNextPitWhenPitIdExceed() {
        Integer pitId = 17;
        Integer expectedPitId = pitId % GameConstant.PIT_END_ID + 1;
        Board board = new Board();

        Pit nextPit = board.advanceToNextPit(pitId);

        assertEquals(expectedPitId, nextPit.getId());
    }

    @Test
    void shouldAdvanceToNextPitWhenEndPit() {
        Integer pitId = GameConstant.PIT_END_ID;
        Integer expectedPitId = GameConstant.PIT_START_ID;
        Board board = new Board();

        Pit nextPit = board.advanceToNextPit(pitId);

        assertEquals(expectedPitId, nextPit.getId());
    }

    @Test
    void shouldSetLatestPitWhenAdvanceToNextPit() {
        Integer pitId = GameConstant.PIT_START_ID;
        Integer expectedPitId = pitId + 1;
        Board board = new Board();

        board.advanceToNextPit(pitId);

        assertEquals(expectedPitId, board.getLatestPit().getId());
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
    void shouldNotResetHousePitsStone() {
        Integer pitId = GameConstant.HOUSE_A_PIT_ID;
        Game game = MockDataGenerator.generateGame();
        Pit houseA = game.getBoard().getPitById(pitId);
        houseA.increasePitStone();

        game.getBoard().resetBoardPitsStone();

        assertNotEquals(0, houseA.getStoneCount());
    }

    @Test
    void shouldCompleted() {
        players.forEach(player -> {
            Board board = new Board();
            resetPlayersBoardPits(player, board);
            assertTrue(board.isCompleted());
        });
    }

    @Test
    void shouldGetHousePit() {
        Board board = new Board();
        Pit pit = board.getHousePit(Player.A);
        assertTrue(pit.isHousePit());
    }

    @Test
    void shouldCaptureOppositePitStone() {
        Integer pitId = 6;
        Player activePlayer = Player.A;
        Board board = new Board();
        int stoneOfHouse = board.getHousePit(activePlayer).getStoneCount();

        board.captureOppositePitStone(activePlayer, pitId);

        int stoneOfHouseAfterCapture = board.getHousePit(activePlayer).getStoneCount();
        assertTrue(stoneOfHouseAfterCapture > stoneOfHouse);
    }


    private void resetPlayersBoardPits(Player player, Board board) {
        board.getPits().values().stream().filter(p -> p.getPlayer() == player).forEach(Pit::resetPitStone);
    }
}