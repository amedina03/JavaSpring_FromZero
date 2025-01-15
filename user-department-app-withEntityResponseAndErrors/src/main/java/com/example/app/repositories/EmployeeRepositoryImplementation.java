package com.example.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.models.Employee;

@Repository
public class EmployeeRepositoryImplementation implements EmployeeRepository {

	private final EmployeeRepositoryJPA employeeRepositoryJPA;

	public EmployeeRepositoryImplementation(EmployeeRepositoryJPA employeeRepositoryJPA) {
		this.employeeRepositoryJPA = employeeRepositoryJPA;
	}
	
	@Override
	public Optional<Employee> GetEmployeeById(int employeeId) {
		return employeeRepositoryJPA.findById(employeeId);
	}

	@Override
	public List<Employee> GetAllEmployees() {
		return employeeRepositoryJPA.findAll();
	}
	
	@Override
	public Employee AddEmployee(Employee newEmployee) {
		return this.employeeRepositoryJPA.save(newEmployee);
	}

	@Override
	public Employee EditEmployee(Employee newEmployee, int employeeId) {
		newEmployee.setId(employeeId);
		return employeeRepositoryJPA.save(newEmployee);
	}

	@Override
	public void RemoveEmployee(int employeeId) {
		this.employeeRepositoryJPA.deleteById(employeeId);
	}
}
