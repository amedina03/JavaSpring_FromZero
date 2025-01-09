package com.example.app.services;

import java.util.List;
import java.util.Optional;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;

public interface EmployeeService {
	public int AddEmployee(EmployeeRequestDTO newEmployee);
	public int RemoveEmployee(int employeeId);
	public int EditEmployee(EmployeeRequestDTO newEmployee, int employeeId);
	public List<EmployeeResponseDTO> GetAllEmployees();
	public Optional<EmployeeResponseDTO> GetEmployeeById(int employeeId);
}
