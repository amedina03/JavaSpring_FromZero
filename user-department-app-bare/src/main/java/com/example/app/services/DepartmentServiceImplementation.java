package com.example.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

	private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;
	
	public DepartmentServiceImplementation(DepartmentRepository departmentRepository, EmployeeService employeeService){
		this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
	}
	
	public List<Department> getAllDepartments(){
		return List.of();
	}
	
	public List<Employee> getAllDepartmentEmployees(int departmentId){
		return List.of();
	}
	
	public int addDepartment(Department newDepartment) {
		return 0;
	}
	
	public int editDepartment(Department newDepartment, int departmentId) {
		return 0;
	}
	
	public int removeDepartment(int departmentId) {
		return 0;
	}
}
