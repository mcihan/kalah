package com.cihan.kalah.generator;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.Pit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

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
        game.finish();
        return game;
    }

    public static Map<Integer, Pit> generatePits() {
        return generateGame().getBoard().getPits();
    }

    public static int getSystemPort() {
        return 8080;
    }

}
