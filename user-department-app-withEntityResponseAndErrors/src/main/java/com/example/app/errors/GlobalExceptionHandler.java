package com.example.app.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.app.responses.ApiExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	//Internal server exception
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ApiExceptionResponse> handleInternalServerException(InternalServerException e){
		ApiExceptionResponse apiResponse = new ApiExceptionResponse(e.getMessage());
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
	//EmployeeExceptions
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ApiExceptionResponse> handleEmployeeNotFound(EmployeeNotFoundException e){
		ApiExceptionResponse apiResponse = new ApiExceptionResponse(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
	}
	
	@ExceptionHandler(DuplicateEmployeeEntryException.class)
	public ResponseEntity<ApiExceptionResponse> handleDuplicateEmployeeEntry(DuplicateEmployeeEntryException e){
		ApiExceptionResponse apiResponse = new ApiExceptionResponse(e.getMessage());
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
	@ExceptionHandler(IncorrectEmployeeParametersException.class)
	public ResponseEntity<ApiExceptionResponse> handleIncorrectEmployeeParameters(IncorrectEmployeeParametersException e){
		ApiExceptionResponse apiResponse = new ApiExceptionResponse(e.getMessage());
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
	//DepartmentExceptions
	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<ApiExceptionResponse> handleDepartmentNotFound(DepartmentNotFoundException e){
		ApiExceptionResponse apiResponse = new ApiExceptionResponse(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
	}

	@ExceptionHandler(DuplicateDepartmentEntryException.class)
	public ResponseEntity<ApiExceptionResponse> handleDuplicateDepartmentEntry(DuplicateDepartmentEntryException e){
		ApiExceptionResponse apiResponse = new ApiExceptionResponse(e.getMessage());
		return ResponseEntity.badRequest().body(apiResponse);
	}

	@ExceptionHandler(IncorrectDepartmentParametersException.class)
	public ResponseEntity<ApiExceptionResponse> handleIncorrectDepartmentParameters(IncorrectDepartmentParametersException e){
		ApiExceptionResponse apiResponse = new ApiExceptionResponse(e.getMessage());
		return ResponseEntity.badRequest().body(apiResponse);
	}
}
