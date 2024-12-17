package com.example.app.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.app.models.Employee;

@Repository
public class EmployeeRepositoryFromList implements EmployeeRepository{
	List<Employee> employeeList = new LinkedList<Employee>();
	
	public int AddEmployee(Employee newEmployee) {
		employeeList.add(newEmployee);
		return 0;
	}
	
	public int RemoveEmployee(int employeeId) {
		List<Employee> employeeList = this.GetAllEmployees();
		for (int i = 0; i < employeeList.size(); i++) {
			if(employeeList.get(i).getId() == employeeId) {
				employeeList.remove(i);
				return 0;
			}
		}
		return 404;
	}
	
	public int EditEmployee(Employee newEmployee, int employeeId) {
		List<Employee> employeeList = this.GetAllEmployees();
		for (int i = 0; i < employeeList.size(); i++) {
			if(employeeList.get(i).getId() == employeeId) {
				employeeList.set(i, newEmployee);
				return 0;
			}
		}
		return 500;
	}
	
	public List<Employee> GetAllEmployees(){
		return new LinkedList<Employee>(employeeList);
	}
	
	public Employee GetEmployeeById(int employeeId) {
		return employeeList.stream().filter(x -> x.getId() == employeeId).findFirst().orElse(null);
	}
}
