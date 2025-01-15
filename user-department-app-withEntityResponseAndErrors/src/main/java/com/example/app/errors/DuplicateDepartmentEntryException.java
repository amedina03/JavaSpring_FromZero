package com.example.app.errors;

public class DuplicateDepartmentEntryException extends RuntimeException{
    public DuplicateDepartmentEntryException(String message) {
        super(message);
    }
}