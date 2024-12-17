package com.example.app.services;

import java.util.List;

import com.example.app.models.Department;
import com.example.app.models.Employee;

public interface DepartmentService {
	public List<Department> getAllDepartments();
	public List<Employee> getAllDepartmentEmployees(int departmentId);
	public int addDepartment(Department newDepartment);
	public int editDepartment(Department newDepartment, int departmentId);
	public int removeDepartment(int departmentId);
}
