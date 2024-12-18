package com.example.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;
import com.example.app.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
	
	public EmployeeServiceImplementation(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}
	
	@Override
	public int AddEmployee(Employee newEmployee) {
		if(departmentRepository.getDepartmentById(newEmployee.getDepartmentId()).isEmpty()) {
			return 404;
		}
		if(employeeRepository.GetEmployeeById(newEmployee.getId()).isPresent()) {
			return 409;
		}
		employeeRepository.AddEmployee(newEmployee);
		
		return 0;
	}
	
	@Override
	public int RemoveEmployee(int employeeId) {
		if(employeeRepository.GetEmployeeById(employeeId).isEmpty()) {
			return 404;
		} 
		employeeRepository.RemoveEmployee(employeeId);
		return 0;
	}
	
	@Override
	public int EditEmployee(Employee newEmployee, int employeeId) {
		if(employeeRepository.GetEmployeeById(employeeId).isEmpty()) {
			return 404;
		}
		if(newEmployee.getId() != 0) {
			return 400;
		}
		return employeeRepository.EditEmployee(newEmployee, employeeId);
	}
	
	@Override
	public List<Employee> GetAllEmployees(){
		return employeeRepository.GetAllEmployees();
	}
	
	@Override
	public Employee GetEmployeeById(int employeeId) {
		return employeeRepository.GetEmployeeById(employeeId).orElse(null);
	}
}
