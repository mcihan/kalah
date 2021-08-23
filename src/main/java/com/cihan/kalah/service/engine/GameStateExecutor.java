package com.cihan.kalah.service.engine;

import com.cihan.kalah.domain.Game;
import com.cihan.kalah.domain.Pit;
import org.springframework.stereotype.Service;

/**
 * @author cihan dogan  on 24.08.2021
 */

@Service
public class GameStateExecutor {
    private final GameState state;

    public GameStateExecutor() {
        this.state = new DetermineActivePlayerState();
        state.setNextState(new DistributeStonesState())
                .setNextState(new LastPitRuleState())
                .setNextState(new CompleteGameState());

    }
    public void executeFlow(Game game, Pit pit) {
        state.execute(game, pit);
    }
}
