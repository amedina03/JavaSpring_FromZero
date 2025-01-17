package com.example.app.repositories;

import java.util.List;
import java.util.Optional;

import com.example.app.models.Employee;

public interface EmployeeRepository {
	public Optional<Employee> GetEmployeeById(int employeeId);
	public List<Employee> GetAllEmployees();
	public Employee AddEmployee(Employee newEmployee);
	public Employee EditEmployee(Employee newEmployee, int employeeId);
	public void RemoveEmployee(int employeeId);
}
