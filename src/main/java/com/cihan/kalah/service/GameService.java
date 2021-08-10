package com.cihan.kalah.service;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameEngine gameEngine;

    public Game create() {
        return gameRepository.save(new Game());
    }

    public Game move(String gameId, Integer pitId) {
        Game game = gameRepository.findById(gameId);
        GameServiceValidator.validateMove(game, pitId);
        gameEngine.move(game, pitId);
        return gameRepository.save(game);
    }

}
