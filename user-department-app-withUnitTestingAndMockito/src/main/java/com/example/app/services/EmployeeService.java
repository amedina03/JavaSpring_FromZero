package com.example.app.services;

import java.util.List;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;

public interface EmployeeService {
	public EmployeeResponseDTO getEmployeeById(int employeeId);
	public List<EmployeeResponseDTO> getAllEmployees();
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO newEmployee);
	public EmployeeResponseDTO editEmployee(EmployeeRequestDTO newEmployee, int employeeId);
	public void removeEmployee(int employeeId);
}
