package com.cihan.kalah.controller;

import com.cihan.kalah.model.Game;
import com.cihan.kalah.service.KalahService;
import com.cihan.kalah.util.GameValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ui/games")
@RequiredArgsConstructor
@Validated
public class UIKahalController {


    private final KalahService kalahService;

    @PostMapping()
    public ResponseEntity<Game> createForUI() {
        Game game = kalahService.create();
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @PutMapping("/{gameId}/pits/{pitId}")
    public ResponseEntity moveForUI(@PathVariable final String gameId,
                                    @PathVariable final Integer pitId) {
        GameValidationUtil.validateParameters(gameId, pitId);

        Game game = kalahService.move(gameId, pitId);

        System.out.println("game.getActivePlayer() = " + game.getActivePlayer());
        System.out.println("game.getWinner() = " + game.getWinner());
        System.out.println("game.getGameStatus() = " + game.getGameStatus());
        System.err.println("-----------------------------------------------");

        return ResponseEntity.status(HttpStatus.CREATED).body(game);

    }
}
