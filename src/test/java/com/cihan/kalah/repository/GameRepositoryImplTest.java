package com.cihan.kalah.repository;

import com.cihan.kalah.exception.ExceptionConstant;
import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GameRepositoryImplTest {

    @InjectMocks
    private GameRepositoryImpl gameRepository;

    @Test
    void shouldSaveGame() {
        Game game = MockDataGenerator.generateGame();
        Game savedGame = gameRepository.save(game);
        assertEquals(game, savedGame);
    }

    @Test
    void shouldFindGame() {
        Game game = MockDataGenerator.generateGame();
        gameRepository.save(game);
        Game foundedGame = gameRepository.findById(game.getId());
        assertEquals(game, foundedGame);
    }

    @Test
    void shouldValidateGameNotFound() {
        String gameId = UUID.randomUUID().toString();
        GameException exception = assertThrows(GameException.class, () -> gameRepository.findById(gameId));
        assertEquals(ExceptionConstant.GAME_NOT_FOUND, exception.getMessage());
    }

}