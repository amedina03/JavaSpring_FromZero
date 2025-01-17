package com.example.app.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.responses.ApiResponse;
import com.example.app.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/{employeeId}")
	@ResponseBody
	public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployeeById(@PathVariable int employeeId) {
		ApiResponse<EmployeeResponseDTO> apiResponse = employeeService.GetEmployeeById(employeeId);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAllEmployees() {
		ApiResponse<List<EmployeeResponseDTO>> apiResponse = employeeService.GetAllEmployees();
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<ApiResponse<EmployeeResponseDTO>> addEmployee(@RequestBody EmployeeRequestDTO newEmployee) {
		ApiResponse<EmployeeResponseDTO> apiResponse = employeeService.AddEmployee(newEmployee);
		return ResponseEntity.created(URI.create("/employees/" + apiResponse.getData().getId())).body(apiResponse);
	}
	
	@PutMapping("/{employeeId}")
	@ResponseBody
	public ResponseEntity<ApiResponse<EmployeeResponseDTO>> editEmployee(@RequestBody EmployeeRequestDTO newEmployee, @PathVariable int employeeId) {
		ApiResponse<EmployeeResponseDTO> apiResponse = employeeService.EditEmployee(newEmployee, employeeId);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@DeleteMapping("/{employeeId}")
	@ResponseBody
	public ResponseEntity<Void> removeEmployee(@PathVariable int employeeId) {
		employeeService.RemoveEmployee(employeeId);
		return ResponseEntity.noContent().build();
	}
}
