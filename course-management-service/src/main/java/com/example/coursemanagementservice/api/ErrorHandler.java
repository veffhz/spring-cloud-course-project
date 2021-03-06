package com.example.coursemanagementservice.api;

import com.example.coursemanagementservice.exception.CourseNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = CourseNotFoundException.class)
    public ResponseEntity<String> handle(RuntimeException ex) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
