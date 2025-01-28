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
import com.example.app.responses.ApiStandardResponse;
import com.example.app.services.EmployeeService;
import com.example.app.utils.MessageUtil;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	private static final String EMPLOYEE_FOUND = "employee.found";
	private static final String EMPLOYEE_EMPTY_LIST = "employee.empty.list";
	private static final String EMPLOYEE_ADDED = "employee.added";
	private static final String EMPLOYEE_EDITED = "employee.edited";

	private final EmployeeService employeeService;
	private final MessageUtil messageUtil;
	
	public EmployeeController(EmployeeService employeeService, MessageUtil messageUtil) {
		this.employeeService = employeeService;
		this.messageUtil = messageUtil;
	}
	
	@GetMapping("/{employeeId}")
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<EmployeeResponseDTO>> getEmployeeById(@PathVariable int employeeId) {
		EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeById(employeeId);
		ApiStandardResponse<EmployeeResponseDTO> apiResponse = new ApiStandardResponse<>(messageUtil.getMessage(EMPLOYEE_FOUND),employeeResponseDTO);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<List<EmployeeResponseDTO>>> getAllEmployees() {
		List<EmployeeResponseDTO> employeeResponseDTO = employeeService.getAllEmployees();
		ApiStandardResponse<List<EmployeeResponseDTO>> apiResponse = employeeResponseDTO.isEmpty() ? new ApiStandardResponse<>(messageUtil.getMessage(EMPLOYEE_EMPTY_LIST), null) : new ApiStandardResponse<>(messageUtil.getMessage(EMPLOYEE_FOUND), employeeResponseDTO);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<EmployeeResponseDTO>> addEmployee(@RequestBody EmployeeRequestDTO newEmployee) {
		EmployeeResponseDTO employeeResponseDTO = employeeService.addEmployee(newEmployee);
		ApiStandardResponse<EmployeeResponseDTO> apiResponse = new ApiStandardResponse<>(messageUtil.getMessage(EMPLOYEE_ADDED), employeeResponseDTO);
		return ResponseEntity.created(URI.create("/employees/" + apiResponse.getData().getId())).body(apiResponse);
	}
	
	@PutMapping("/{employeeId}")
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<EmployeeResponseDTO>> editEmployee(@RequestBody EmployeeRequestDTO newEmployee, @PathVariable int employeeId) {
		EmployeeResponseDTO employeeResponseDTO = employeeService.editEmployee(newEmployee, employeeId);
		ApiStandardResponse<EmployeeResponseDTO> apiResponse = new ApiStandardResponse<>(messageUtil.getMessage(EMPLOYEE_EDITED), employeeResponseDTO);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@DeleteMapping("/{employeeId}")
	@ResponseBody
	public ResponseEntity<Void> removeEmployee(@PathVariable int employeeId) {
		employeeService.removeEmployee(employeeId);
		return ResponseEntity.noContent().build();
	}
}
