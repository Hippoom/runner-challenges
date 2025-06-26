package com.github.hippoom.runner.challenges.http.support;

import com.github.hippoom.runner.challenges.domain.challenge.availability.ChallengeUnavailableException;
import com.github.hippoom.runner.challenges.domain.challenge.NoSuchChallengeException;
import com.github.hippoom.runner.challenges.http.representation.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ChallengeUnavailableException.class)
    public ResponseEntity<ErrorResponse> handle(ChallengeUnavailableException ex) {
        log.info("Challenge unavailable: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(NoSuchChallengeException.class)
    public ResponseEntity<ErrorResponse> handle(NoSuchChallengeException ex) {
        log.info("Challenge not found: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }
} 