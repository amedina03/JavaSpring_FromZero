package com.example.app.errors;

public class IncorrectDepartmentParametersException extends RuntimeException{
    public IncorrectDepartmentParametersException(String message) {
        super(message);
    }
}