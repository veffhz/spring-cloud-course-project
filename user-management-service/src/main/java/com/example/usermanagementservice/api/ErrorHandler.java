package com.example.usermanagementservice.api;

import com.example.usermanagementservice.exceptions.InvalidJwtAuthenticationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<?> vehicleNotFound(UsernameNotFoundException ex) {
        log.debug("handling UsernameNotFoundException...");
        return notFound().build();
    }

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity<?> invalidJwtAuthentication(InvalidJwtAuthenticationException ex) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return status(UNAUTHORIZED).build();
    }
}
