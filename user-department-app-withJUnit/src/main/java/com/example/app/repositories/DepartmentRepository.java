package com.example.app.repositories;

import java.util.List;
import java.util.Optional;

import com.example.app.models.Department;

public interface DepartmentRepository {
	public Optional<Department> getDepartmentById(int departmentId);
	public List<Department> getAllDepartments();
	public Department addDepartment(Department newDepartment);
	public Department editDepartment(Department newDepartment, int departmentId);
	public void removeDepartment(int departmentId);
}
