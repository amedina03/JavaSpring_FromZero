package com.example.app.repositories;

import java.util.List;
import java.util.Optional;

import com.example.app.models.Employee;

public interface EmployeeRepository {
	public void AddEmployee(Employee newEmployee);
	public void RemoveEmployee(int employeeId);
	public void EditEmployee(Employee newEmployee, int employeeId);
	public List<Employee> GetAllEmployees();
	public Optional<Employee> GetEmployeeById(int employeeId);
}
