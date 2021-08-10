package com.cihan.kalah.service;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.repository.KalahRepository;
import com.cihan.kalah.util.GameValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final KalahRepository kalahRepository;
    private final GameEngine gameEngine;

    public Game create() {
        return kalahRepository.save(new Game());
    }

    public Game move(String gameId, Integer pitId) {
        Game game = kalahRepository.findById(gameId);
        GameValidationUtil.moveValidation(game, pitId);
        gameEngine.move(game, pitId);
        return kalahRepository.save(game);
    }


}
