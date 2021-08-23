package com.cihan.kalah.controller;


import com.cihan.kalah.config.GameProperties;
import com.cihan.kalah.controller.dto.MoveGameResponse;
import com.cihan.kalah.controller.dto.StartGameResponse;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Validated
@Tag(name = "game", description = "Game Controller API")
@Slf4j
public class GameController {

    @Value("${server.port}")
    private int port;

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<StartGameResponse> create() {
        log.info("Game Started !");
        Game game = gameService.create();
        StartGameResponse response = new StartGameResponse(game, port);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{gameId}/pits/{pitId}")
    public ResponseEntity<MoveGameResponse> move(@PathVariable final String gameId, @PathVariable final Integer pitId) {
        log.info("Moving Started !");
        RequestValidator.validateParameters(gameId, pitId);
        Game game = gameService.move(gameId, pitId);
        MoveGameResponse response = new MoveGameResponse(game, port);
        log.info("status {} for pitId {}", response.getStatus(), pitId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("pitCount", GameProperties.DEFAULT_PIT_COUNT);
        model.addAttribute("stoneCount", GameProperties.DEFAULT_STONE_COUNT);

        return "index"; //view
    }

}
