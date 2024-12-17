package com.example.app.repositories;

import java.util.List;

import com.example.app.models.Employee;

public interface EmployeeRepository {
	public int AddEmployee(Employee newEmployee);
	public int RemoveEmployee(int employeePlace);
	public int EditEmployee(Employee newEmployee, int employeePlace);
	public List<Employee> GetAllEmployees();
	public Employee GetEmployeeById(int employeeId);
}
