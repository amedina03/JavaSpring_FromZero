package com.example.app.errors;

public class IncorrectEmployeeParametersException extends RuntimeException{
    public IncorrectEmployeeParametersException(String message) {
        super(message);
    }
}