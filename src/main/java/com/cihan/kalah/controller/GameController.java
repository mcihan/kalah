package com.cihan.kalah.controller;


import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.dto.MoveGameResponse;
import com.cihan.kalah.model.dto.StartGameResponse;
import com.cihan.kalah.service.GameService;
import com.cihan.kalah.util.GameValidationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Validated
@Tag(name = "game", description = "Game Controller API")
public class GameController {

    @Value("${server.port}")
    int port;

    private final GameService gameService;

    // TODO
    // Controller   - Service   - Repository
    // Dto          - Model     - Entity
    // GameRequest  - Game      - GameEntity
    // Immutable
    //

    // TODO NOTE Leaky Abstraction

    @PostMapping
    public ResponseEntity<StartGameResponse> create() {
        Game game = gameService.create();
        StartGameResponse response = new StartGameResponse(game, port);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{gameId}/pits/{pitId}")
    public ResponseEntity<MoveGameResponse> move(@PathVariable final String gameId, @PathVariable final Integer pitId) {
        GameValidationUtil.validateParameters(gameId, pitId);
        Game game = gameService.move(gameId, pitId);
        printGameInfo(game);
        MoveGameResponse response = new MoveGameResponse(game, port);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    private void printGameInfo(Game game) {
        System.out.println("game.getActivePlayer() = " + game.getActivePlayer());
        System.out.println("game.getWinner() = " + game.getWinner());
        System.out.println("game.getGameStatus() = " + game.getGameStatus());
        System.err.println("-----------------------------------------------");
    }


}
