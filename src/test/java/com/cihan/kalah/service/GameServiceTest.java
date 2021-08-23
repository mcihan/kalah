package com.cihan.kalah.service;

import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.domain.Game;
import com.cihan.kalah.repository.GameRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepositoryImpl gameRepository;

    @Mock
    private GameEngine gameEngine;

    @Test
    void create() {
        Game game = MockDataGenerator.generateGame();
        when(gameRepository.save(Mockito.any())).thenReturn(game);

        Game createdGame = gameService.create();

        assertEquals(game.getId(), createdGame.getId());
    }

    @Test
    void move() {
        Game game = MockDataGenerator.generateGame();
        String gameId = game.getId();
        Integer pitId = 1;

        when(gameRepository.findById(gameId)).thenReturn(game);
        doNothing().when(gameEngine).executeGameFlow(game, pitId);
        when(gameRepository.save(Mockito.any())).thenReturn(game);

        Game movedGame = gameService.move(gameId, pitId);
        assertEquals(game.getId(), movedGame.getId());
    }
}