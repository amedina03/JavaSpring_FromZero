package com.example.app.repositories;

import java.util.List;

import com.example.app.models.Department;

public interface DepartmentRepository {
	public List<Department> getAllDepartments();
	public int addDepartment(Department newDepartment);
	public int editDepartment(Department newDepartment, int departmentId);
	public int removeDepartment(int departmentId);
}
