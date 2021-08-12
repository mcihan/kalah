package com.cihan.kalah.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GameExceptionHandlerTest {

    @InjectMocks
    private GameExceptionHandler gameExceptionHandler;

    @Test
    void shouldHandleGameException() {
        GameException gameException = new GameException(ExceptionConstant.GAME_NOT_FOUND);

        ResponseEntity<ExceptionResponse> exceptionResponse = gameExceptionHandler.handleGameException(gameException);

        ExceptionResponse body = exceptionResponse.getBody();
        assertEquals(ExceptionConstant.GAME_NOT_FOUND, body.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, body.getStatus());

    }
}