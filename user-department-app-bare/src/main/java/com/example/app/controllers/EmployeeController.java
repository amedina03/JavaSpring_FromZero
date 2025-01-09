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

import com.example.app.models.Employee;
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
	public Employee getEmployeeById(@PathVariable int employeeId) {
		return null;
	}
	
	@GetMapping
	@ResponseBody
	public List<Employee> getAllEmployees() {
		return List.of();
	}
	
	@PostMapping
	@ResponseBody
	public Employee addEmployee(@RequestBody Employee newEmployee) {
		return null;
	}
	
	@PutMapping("/{employeeId}")
	@ResponseBody
	public Employee editEmployee(@RequestBody Employee newEmployee, @PathVariable int employeeId) {
		return null;
	}
	
	@DeleteMapping("/{employeeId}")
	@ResponseBody
	public String removeEmployee(@PathVariable int employeeId) {
		return "";
	}
}
