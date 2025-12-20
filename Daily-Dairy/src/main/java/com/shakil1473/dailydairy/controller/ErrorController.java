package com.shakil1473.dailydairy.controller;

import com.shakil1473.dailydairy.domain.dto.ApiErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseDto> handleException(Exception exception) {
        log.error("Caught exception. " + exception.getMessage(), exception);

        ApiErrorResponseDto error = ApiErrorResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        log.error("Caught illegal argument exception. " + illegalArgumentException.getMessage(), illegalArgumentException);

        ApiErrorResponseDto error = ApiErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(illegalArgumentException.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponseDto> handleIllegalArgumentException(IllegalStateException illegalStateException) {
        log.error("Caught illegal argument exception. " + illegalStateException.getMessage(), illegalStateException);

        ApiErrorResponseDto error = ApiErrorResponseDto.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(illegalStateException.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponseDto> handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        log.error("Caught illegal argument exception. " + badCredentialsException.getMessage(), badCredentialsException);

        ApiErrorResponseDto error = ApiErrorResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Incorrect username or password.")
                .build();

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        log.error("Caught entity not found exception. " + entityNotFoundException.getMessage(), entityNotFoundException);

        ApiErrorResponseDto error = ApiErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(entityNotFoundException.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalIdentifierException.class)
    public ResponseEntity<ApiErrorResponseDto> handleIllegalIdentifierException(IllegalIdentifierException illegalIdentifierException) {
        log.error("Caught illegal identifier exception. " + illegalIdentifierException.getMessage(), illegalIdentifierException);

        ApiErrorResponseDto error = ApiErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(illegalIdentifierException.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
