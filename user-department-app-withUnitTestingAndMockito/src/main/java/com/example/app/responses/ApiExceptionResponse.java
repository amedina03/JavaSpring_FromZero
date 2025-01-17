package com.example.app.responses;

import java.time.LocalDateTime;

public class ApiExceptionResponse {
	private String message;
	private LocalDateTime dateTime;
	
    public ApiExceptionResponse(String message) {
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
}
