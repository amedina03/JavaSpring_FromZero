package com.example.app.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.example.app.models.Employee;
import com.example.app.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private final EmployeeRepository employeeRepository;
	
	public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public int AddEmployee(Employee newEmployee) {
	    try {
	    	employeeRepository.AddEmployee(newEmployee);
	        return 0;
	    } catch (DataIntegrityViolationException e) {
	        return 409; // No department with such id
	    } catch (JpaSystemException e) {
	        return 500;
	    }
	}
	
	@Override
	public int RemoveEmployee(int employeeId) {
	    try {
	    	if(employeeRepository.GetEmployeeById(employeeId).isPresent()) {
	    		employeeRepository.RemoveEmployee(employeeId);
	    		return 0;	    		
	    	}
	    	return 404; // No employee with such id
	    } catch (JpaSystemException e) {
	        return 500;
	    }
	}
	
	@Override
	public int EditEmployee(Employee newEmployee, int employeeId) {
		try {
			if(employeeRepository.GetEmployeeById(employeeId).isPresent()) {
				employeeRepository.EditEmployee(newEmployee, employeeId);
				return 0;
			}
			return 404; // No employee with such id
		} catch (DataIntegrityViolationException e) {
			return 409; // No department with such id
		} catch(JpaSystemException e) {
			return 500;
		}
	}
	
	@Override
	public List<Employee> GetAllEmployees(){
		try {
			return employeeRepository.GetAllEmployees();			
		} catch (JpaSystemException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public Optional<Employee> GetEmployeeById(int employeeId) {
		try {
			return employeeRepository.GetEmployeeById(employeeId);			
		} catch (JpaSystemException e) {
			return Optional.empty();
		}
	}
}
