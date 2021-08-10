package com.cihan.kalah.controller;


import com.cihan.kalah.model.Game;
import com.cihan.kalah.model.dto.MoveGameResponse;
import com.cihan.kalah.model.dto.StartGameResponse;
import com.cihan.kalah.service.KalahService;
import com.cihan.kalah.util.GameValidationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Validated
@Tag(name = "vehicle", description = "Vehicle controller API")
public class KalahController {

    @Value("${server.port}")
    int port;

    private final KalahService kalahService;
//    private final GameMapper gameMapper;

    @PostMapping()
    public ResponseEntity<StartGameResponse> create() {
        Game game = kalahService.create();

        StartGameResponse response = StartGameResponse.builder()
                .id(game.getId())
                .uri(generateGameUrl(game.getId()))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{gameId}/pits/{pitId}")
    public ResponseEntity<MoveGameResponse> move(@PathVariable final String gameId,
                                                 @PathVariable final Integer pitId) {
        GameValidationUtil.validateParameters(gameId, pitId);

        Game game = game = kalahService.move(gameId, pitId);

        System.out.println("game.getActivePlayer() = " + game.getActivePlayer());
        System.out.println("game.getWinner() = " + game.getWinner());
        System.out.println("game.getGameStatus() = " + game.getGameStatus());
        System.err.println("-----------------------------------------------");

        Map<Integer, Integer> status = game.getBoard().getPits().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getStoneCount()));

        // Belki mapper ???
        MoveGameResponse response = MoveGameResponse.builder()
                .id(game.getId())
                .url(generateGameUrl(game.getId()))
                .status(status)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    private String generateGameUrl(String gameId) {
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(), port, gameId);
    }





}
