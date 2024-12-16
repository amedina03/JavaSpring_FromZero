package com.example.app.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.app.models.Employee;

@Repository
public class EmployeeRepositoryFromList implements EmployeeRepository{
	List<Employee> employeeList = new LinkedList<Employee>();
	
	public void AddEmployee(Employee newEmployee) {
		employeeList.add(newEmployee);
	}
	
	public void RemoveEmployee(int employeePlace) {
		employeeList.remove(employeePlace);
	}
	
	public void EditEmployee(Employee newEmployee, int employeePlace) {
		employeeList.set(employeePlace, newEmployee);
	}
	
	public List<Employee> GetAllEmployees(){
		return new LinkedList<Employee>(employeeList);
	}
	
	public Employee GetEmployeeById(int employeeId) {
		return employeeList.stream().filter(x -> x.getId() == employeeId).findFirst().orElse(null);
	}
}
