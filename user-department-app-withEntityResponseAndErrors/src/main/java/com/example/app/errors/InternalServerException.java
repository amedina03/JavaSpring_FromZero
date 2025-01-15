package com.example.app.errors;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message) {
        super(message);
    }
}