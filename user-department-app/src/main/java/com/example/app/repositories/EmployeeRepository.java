package com.example.app.repositories;

import java.util.List;

import com.example.app.models.Employee;

public interface EmployeeRepository {
	public void AddEmployee(Employee newEmployee);
	public void RemoveEmployee(int employeePlace);
	public void EditEmployee(Employee newEmployee, int employeePlace);
	public List<Employee> GetAllEmployees();
	public Employee GetEmployeeById(int employeeId);
}
