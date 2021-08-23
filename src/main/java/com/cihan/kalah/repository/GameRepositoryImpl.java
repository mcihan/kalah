package com.cihan.kalah.repository;

import com.cihan.kalah.exception.ExceptionConstant;
import com.cihan.kalah.exception.GameException;
import com.cihan.kalah.domain.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameRepositoryImpl implements GameRepository {

    private final Map<String, Game> repository = new HashMap<>();

    @Override
    public Game save(Game game) {
        repository.put(game.getId(), game);
        return findById(game.getId());
    }

    @Override
    public Game findById(String id) {
        Game game = repository.get(id);
        if (game == null) {
            throw new GameException(ExceptionConstant.GAME_NOT_FOUND);
        }
        return game;
    }


}
