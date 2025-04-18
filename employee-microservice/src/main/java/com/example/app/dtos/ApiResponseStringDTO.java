package com.example.app.dtos;

public class ApiResponseStringDTO {
	private String message;
	private String data;
	
	public ApiResponseStringDTO(String message, String data) {
		this.message = message;
		this.data = data;
	}	
	public ApiResponseStringDTO(String message) {
		this.message = message;
	}	
	public ApiResponseStringDTO() {
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
