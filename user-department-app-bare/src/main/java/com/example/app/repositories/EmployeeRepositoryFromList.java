package com.example.app.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.app.models.Employee;

@Repository
public class EmployeeRepositoryFromList implements EmployeeRepository{
	List<Employee> employeeList = new LinkedList<Employee>();
	
	public int AddEmployee(Employee newEmployee) {
		return 0;
	}
	
	public int RemoveEmployee(int employeeId) {
		return 0;
	}
	
	public int EditEmployee(Employee newEmployee, int employeeId) {
		return 0;
	}
	
	public List<Employee> GetAllEmployees(){
		return List.of();
	}
	
	public Employee GetEmployeeById(int employeeId) {
		return null;
	}
}
