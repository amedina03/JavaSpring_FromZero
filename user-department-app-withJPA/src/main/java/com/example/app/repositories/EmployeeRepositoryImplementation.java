package com.example.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.models.Employee;

@Repository
public class EmployeeRepositoryImplementation implements EmployeeRepository {

	@Override
	public int AddEmployee(Employee newEmployee) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int RemoveEmployee(int employeeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int EditEmployee(Employee newEmployee, int employeeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Employee> GetAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Employee> GetEmployeeById(int employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
