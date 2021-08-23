package com.cihan.kalah.service;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;
import com.cihan.kalah.repository.GameRepository;
import com.cihan.kalah.service.engine.GameStateExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameStateExecutor gameStateExecutor;

    public Game create() {
        return gameRepository.save(new Game());
    }

    public Game move(String gameId, Integer pitId) {
        Game game = gameRepository.findById(gameId);
        GameServiceValidator.validateMove(game, pitId);
        Pit pit = game.getBoard().getPitById(pitId);
        gameStateExecutor.executeFlow(game, pit);
        return gameRepository.save(game);
    }


}
