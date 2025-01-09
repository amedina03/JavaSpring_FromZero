package com.example.app.services;

import java.util.List;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.models.Department;

public interface DepartmentService {
	public List<DepartmentResponseDTO> getAllDepartments();
	public List<EmployeeResponseDTO> getAllDepartmentEmployees(int departmentId);
	public int addDepartment(Department newDepartment);
	public int editDepartment(Department newDepartment, int departmentId);
	public int removeDepartment(int departmentId);
}
