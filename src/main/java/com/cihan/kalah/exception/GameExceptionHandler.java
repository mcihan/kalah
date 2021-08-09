package com.cihan.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(GameException.class)
    public final ResponseEntity<ExceptionResponse> handleGameException(final GameException e){
        ExceptionResponse response = ExceptionResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST);
        System.err.println("errorMessage = " + e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
