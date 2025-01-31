package com.example.app.controllers;

import java.util.List;

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
	public EmployeeResponseDTO getEmployeeById(@PathVariable int employeeId) {
		return employeeService.GetEmployeeById(employeeId).orElse(null);
	}
	
	@GetMapping
	@ResponseBody
	public List<EmployeeResponseDTO> getAllEmployees() {
		return employeeService.GetAllEmployees();
	}
	
	@PostMapping
	@ResponseBody
	public EmployeeRequestDTO addEmployee(@RequestBody EmployeeRequestDTO newEmployee) {
		if(employeeService.AddEmployee(newEmployee) == 0) {
			return newEmployee;
		}
		return null;
	}
	
	@PutMapping("/{employeeId}")
	@ResponseBody
	public EmployeeRequestDTO editEmployee(@RequestBody EmployeeRequestDTO newEmployee, @PathVariable int employeeId) {
		if(employeeService.EditEmployee(newEmployee, employeeId) == 0) {
			return newEmployee;
		}
		return null;
	}
	
	@DeleteMapping("/{employeeId}")
	@ResponseBody
	public String removeEmployee(@PathVariable int employeeId) {
		if(employeeService.RemoveEmployee(employeeId) == 0) {
			return "Employee removed succesfully";
		}
		return "An error occured while delting user, please try again later";
	}
}
