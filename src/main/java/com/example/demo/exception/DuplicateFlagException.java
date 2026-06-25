package com.example.demo.exception;

public class DuplicateFlagException extends RuntimeException {
    public DuplicateFlagException(String message) {
        super(message);
    }
}