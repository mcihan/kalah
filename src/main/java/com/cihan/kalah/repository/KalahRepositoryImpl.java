package com.cihan.kalah.repository;

import com.cihan.kalah.model.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KalahRepositoryImpl implements KalahRepository {

    private final Map<String, Game> repository = new HashMap<>();

    @Override
    public Game save(Game game) {
        repository.put(game.getId(), game);
        return findById(game.getId());
    }

    @Override
    public Game findById(String id) {
        return repository.get(id);
    }


}
