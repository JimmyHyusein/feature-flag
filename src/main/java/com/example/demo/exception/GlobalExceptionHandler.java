package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FlagNotFoundException.class)
    public ResponseEntity<String> handleNotFound(FlagNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateFlagException.class)
    public ResponseEntity<String> handleDuplicate(DuplicateFlagException ex) {
        return ResponseEntity.status(409).body(ex.getMessage()); 
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex){
        return ResponseEntity.status(500).body("Something went wrong: " + ex.getMessage());
    }
    
}
