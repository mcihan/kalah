package com.cihan.kalah.generator;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.GameStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author cihan dogan  on 11.08.2021
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockDataGenerator {


    public static Game generateGame() {
        return new Game();
    }

    public static Game generateFinishedGame() {
        Game game = generateGame();
        game.setGameStatus(GameStatus.FINISH);
        return game;
    }

    public static int getSystemPort(){
        return 8080;
    }

}
