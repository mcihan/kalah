package com.cihan.kalah.model;

import com.cihan.kalah.generator.MockDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

    @Test
    void shouldTurnToOtherPlayer() {
        Integer pitId = 1;
        Game game = MockDataGenerator.generateGame();
        game.initActivePlayerByPitId(pitId);
        PlayerId player = game.getActivePlayer();

        game.turnToOtherPlayer();

        PlayerId turnedPlayer = game.getActivePlayer();
        assertNotEquals(player, turnedPlayer);
    }

    @Test
    void shouldInitActivePlayerByPitId() {
        Integer pitId = 1;
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
        game.getBoard().getHousePit(PlayerId.A).increasePitStone();

        game.determineWinner();

        assertEquals(PlayerId.A, game.getWinner());
    }

    @Test
    void shouldDetermineWinnerWhenItDraw() {
        Game game = MockDataGenerator.generateGame();
        game.determineWinner();
        assertNull(game.getWinner());
    }


    @Test
    void shouldFinish() {
        Game game = MockDataGenerator.generateGame();

        game.finish();

        assertEquals(GameStatus.FINISH, game.getGameStatus());
    }

    @Test
    void shouldCaptureOppositePitStone() {
        Integer pitId = 1;
        Game game = MockDataGenerator.generateGame();
        game.initActivePlayerByPitId(pitId);
        int pitStone = game.getBoard().getPitById(pitId).getStoneCount();
        int oppositePitStone = game.getBoard().getOppositePit(pitId).getStoneCount();
        int houseStone = game.getBoard().getHousePit(PlayerId.A).getStoneCount();
        int sumOfStones = pitStone + oppositePitStone + houseStone;

        game.captureOppositePitStone(pitId);

        int houseStoneAfterCapture = game.getBoard().getHousePit(PlayerId.A).getStoneCount();
        assertEquals(sumOfStones, houseStoneAfterCapture);
    }

    @Test
    void shouldGetPlayer() {
        Game game = MockDataGenerator.generateGame();

        List<Player> players = game.getPlayers();

        assertEquals(2, players.size());
    }
}