package com.example.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;
import com.example.app.repositories.EmployeeRepositoryFromList;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private final EmployeeRepositoryFromList employeeRepository;
    private final DepartmentRepository departmentRepository;
	
	public EmployeeServiceImplementation(EmployeeRepositoryFromList employeeRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}
	
	@Override
	public int AddEmployee(Employee newEmployee) {
		return 0;
	}
	
	@Override
	public int RemoveEmployee(int employeeId) {
		return 0;
	}
	
	@Override
	public int EditEmployee(Employee newEmployee, int employeeId) {
		return 0;
	}
	
	@Override
	public List<Employee> GetAllEmployees(){
		return List.of();
	}
	
	@Override
	public Employee GetEmployeeById(int employeeId) {
		return null;
	}
}
