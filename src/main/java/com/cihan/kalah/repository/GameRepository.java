package com.cihan.kalah.repository;

import com.cihan.kalah.domain.Game;

public interface GameRepository {

    Game save(Game game);

    Game findById(String id);

}
