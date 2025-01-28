package com.example.app.responses;

public class ApiStandardResponse<T> {
	private String message;
	private T data;
	
    public ApiStandardResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
}
