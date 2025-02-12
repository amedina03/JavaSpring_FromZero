package com.example.app.dtos;

import java.util.List;

public class ApiResponseDTO<T> {
	private String message;
	private List<T> data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
