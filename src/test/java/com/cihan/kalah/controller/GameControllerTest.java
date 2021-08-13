package com.cihan.kalah.controller;

import com.cihan.kalah.exception.GameExceptionHandler;
import com.cihan.kalah.generator.MockDataGenerator;
import com.cihan.kalah.model.Game;
import com.cihan.kalah.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.containsString;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private GameController gameController;
    private GameService gameService;
    private MockMvc mockMvc;

    public GameControllerTest() {
        gameService = Mockito.mock(GameService.class);
        gameController = new GameController(gameService);
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .setControllerAdvice(new GameExceptionHandler())
                .build();
    }

    @Test
    void create() throws Exception {
        Game game = MockDataGenerator.generateGame();

        Mockito.doReturn(game).when(gameService).create();
        mockMvc.perform(MockMvcRequestBuilders.get("/kalah/1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));

        mockMvc.perform(MockMvcRequestBuilders.post("/games"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().string(containsString(game.getId())));

    }

    @Test
    void move() throws Exception {
        Game game = MockDataGenerator.generateGame();
        String gameId = game.getId();
        Integer pitId = 1;

        String urlTemplate = String.format("/games/%s/pits/%s", gameId, pitId);

        Mockito.doReturn(game).when(gameService).move(gameId, pitId);
        mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().string(containsString(game.getId())));
    }


}