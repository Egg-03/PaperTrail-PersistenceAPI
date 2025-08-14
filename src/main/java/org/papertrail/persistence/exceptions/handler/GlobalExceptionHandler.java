package org.papertrail.persistence.exceptions.handler;

import org.papertrail.persistence.exceptions.GuildAlreadyRegisteredException;
import org.papertrail.persistence.exceptions.GuildNotFoundException;
import org.papertrail.persistence.exceptions.MessageAlreadyLoggedException;
import org.papertrail.persistence.exceptions.MessageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GuildNotFoundException.class)
    public ResponseEntity<ErrorResponse> informGuildNotFound (GuildNotFoundException e) {

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorResponse> informMessageNotFound (MessageNotFoundException e) {

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(GuildAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> informGuildAlreadyRegistered (GuildAlreadyRegisteredException e) {

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MessageAlreadyLoggedException.class)
    public ResponseEntity<ErrorResponse> informMessageAlreadyLogged (MessageAlreadyLoggedException e) {

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
