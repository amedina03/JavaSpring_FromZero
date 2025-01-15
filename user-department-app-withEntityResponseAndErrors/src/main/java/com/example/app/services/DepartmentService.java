package com.example.app.services;

import java.util.List;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.models.Department;
import com.example.app.responses.ApiResponse;

public interface DepartmentService {
	public ApiResponse<List<DepartmentResponseDTO>> getAllDepartments();
	public ApiResponse<List<EmployeeResponseDTO>> getAllDepartmentEmployees(int departmentId);
	public ApiResponse<DepartmentResponseDTO> addDepartment(Department newDepartment);
	public ApiResponse<DepartmentResponseDTO> editDepartment(Department newDepartment, int departmentId);
	public ApiResponse<Void> removeDepartment(int departmentId);
}
