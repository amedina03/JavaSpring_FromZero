package com.example.app.errors;

public class DuplicateEmployeeEntryException extends RuntimeException{
    public DuplicateEmployeeEntryException(String message) {
        super(message);
    }
}