package com.example.app.services;

import java.util.List;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.models.Department;

public interface DepartmentService {
	public List<DepartmentResponseDTO> getAllDepartments();
	public DepartmentResponseDTO addDepartment(Department newDepartment);
	public DepartmentResponseDTO editDepartment(Department newDepartment, int departmentId);
	public void removeDepartment(int departmentId);
	public boolean checkDepartmentExistsById(int departmentId);
	public String getDepartmentName(int departmentId);
}
