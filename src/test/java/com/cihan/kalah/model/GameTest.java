package com.cihan.kalah.model;

import com.cihan.kalah.generator.MockDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

    final private Integer pitId = 1;

    @Test
    void shouldTurnToOtherPlayer() {
        Game game = MockDataGenerator.generateGame();
        game.initActivePlayerByPitId(pitId);
        Player player = game.getActivePlayer();

        game.turnToOtherPlayer();

        Player turnedPlayer = game.getActivePlayer();
        assertNotEquals(player, turnedPlayer);
    }

    @Test
    void shouldInitActivePlayerByPitId() {
        Game game = MockDataGenerator.generateGame();

        game.initActivePlayerByPitId(pitId);

        assertNotNull(game.getActivePlayer());
    }

    @Test
    void shouldIsOver() {
        Game game = MockDataGenerator.generateGame();
        game.getBoard().resetBoardPitsStone();
        assertTrue(game.isOver());
    }

    @Test
    void shouldIsOverWithoutFinish() {
        Game game = MockDataGenerator.generateGame();
        assertFalse(game.isOver());
    }

    @Test
    void shouldDetermineWinner() {
        Game game = MockDataGenerator.generateGame();
        game.getBoard().getHousePit(Player.A).increasePitStone();

        game.determineWinner();

        assertEquals(Player.A, game.getWinner());
    }

    @Test
    void shouldDetermineWinnerWhenItDraw() {
        Game game = MockDataGenerator.generateGame();
        game.determineWinner();
        assertNull(game.getWinner());
    }

    @Test
    void shouldMoveOn() {
        Game game = MockDataGenerator.generateGame();

        Pit currentPit = game.moveOn(pitId);

        assertEquals(currentPit.getId(), pitId);
    }

    @Test
    void shouldCollectStones() {
        int expectedHouseStoneCount = GameConstant.TOTAL_STONE_COUNT / 2;
        Game game = MockDataGenerator.generateGame();

        game.collectStones();

        int housePitStoneCount = game.getBoard().getHousePit(Player.A).getStoneCount();
        assertEquals(expectedHouseStoneCount, housePitStoneCount);

        int boardPitStoneCount = game.getBoard().getPitById(pitId).getStoneCount();
        assertEquals(0, boardPitStoneCount);

    }

    @Test
    void shouldFinish() {
        Game game = MockDataGenerator.generateGame();

        game.finish();

        assertEquals(GameStatus.FINISH, game.getGameStatus());
    }

    @Test
    void shouldCaptureOppositePitStone() {
        Game game = MockDataGenerator.generateGame();
        game.initActivePlayerByPitId(pitId);
        int pitStone = game.getBoard().getPitById(pitId).getStoneCount();
        int oppositePitStone = game.getBoard().getOppositePit(pitId).getStoneCount();
        int houseStone = game.getBoard().getHousePit(Player.A).getStoneCount();
        int sumOfStones = pitStone + oppositePitStone + houseStone;
        int allStones = game.getBoard().getPits().values().stream().mapToInt(Pit::getStoneCount).sum();

        game.captureOppositePitStone(pitId);

        int houseStoneAfterCapture = game.getBoard().getHousePit(Player.A).getStoneCount();
        assertEquals(sumOfStones, houseStoneAfterCapture);

        int allStonesAfterCaptured = game.getBoard().getPits().values().stream().mapToInt(Pit::getStoneCount).sum();
        assertEquals(allStones, allStonesAfterCaptured);

    }

}