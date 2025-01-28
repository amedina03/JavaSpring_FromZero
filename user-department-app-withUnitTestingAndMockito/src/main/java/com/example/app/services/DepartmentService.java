package com.example.app.services;

import java.util.List;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.models.Department;

public interface DepartmentService {
	public List<DepartmentResponseDTO> getAllDepartments();
	public List<EmployeeResponseDTO> getAllDepartmentEmployees(int departmentId);
	public DepartmentResponseDTO addDepartment(Department newDepartment);
	public DepartmentResponseDTO editDepartment(Department newDepartment, int departmentId);
	public void removeDepartment(int departmentId);
}
