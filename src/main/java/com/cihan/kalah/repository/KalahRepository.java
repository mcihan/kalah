package com.cihan.kalah.repository;

import com.cihan.kalah.model.Game;

public interface KalahRepository/* extends CrudRepository<Game, String> */ {

    //    @Override
    Game save(Game game);

    //    @Override
    Game findById(String id);


}
