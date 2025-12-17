package com.shakil1473.dailydairy.controller;

import com.shakil1473.dailydairy.domain.dto.ApiErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                .message("An unexpected error occured")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponseDto> handleException(ApiErrorResponseDto apiErrorResponseDto) {
        log.error("Caught illegal argument exception . " + apiErrorResponseDto.getMessage(), apiErrorResponseDto);

        ApiErrorResponseDto error = ApiErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(apiErrorResponseDto.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
