package com.example.app.services;

import java.util.List;
import java.util.Optional;

import com.example.app.models.Employee;

public interface EmployeeService {
	public int AddEmployee(Employee newEmployee);
	public int RemoveEmployee(int employeeId);
	public int EditEmployee(Employee newEmployee, int employeeId);
	public List<Employee> GetAllEmployees();
	public Optional<Employee> GetEmployeeById(int employeeId);
}
