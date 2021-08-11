package com.cihan.kalah.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionResponse {

    private final String message;
    private final HttpStatus status;

    private ExceptionResponse(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }

    static ExceptionResponse of(final String message, final HttpStatus status) {
        return new ExceptionResponse(message, status);
    }

}
