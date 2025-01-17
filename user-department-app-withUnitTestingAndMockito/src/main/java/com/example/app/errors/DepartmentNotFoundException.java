package com.example.app.errors;
public class DepartmentNotFoundException extends RuntimeException{
	public DepartmentNotFoundException(String message) {
		super(message);
	}
}
