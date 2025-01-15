package com.example.app.services;

import java.util.List;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.responses.ApiResponse;

public interface EmployeeService {
	public ApiResponse<EmployeeResponseDTO> GetEmployeeById(int employeeId);
	public ApiResponse<List<EmployeeResponseDTO>> GetAllEmployees();
	public ApiResponse<EmployeeResponseDTO> AddEmployee(EmployeeRequestDTO newEmployee);
	public ApiResponse<EmployeeResponseDTO> EditEmployee(EmployeeRequestDTO newEmployee, int employeeId);
	public ApiResponse<Void> RemoveEmployee(int employeeId);
}
