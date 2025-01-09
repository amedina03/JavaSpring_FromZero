package com.example.app.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;
import com.example.app.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	
	public EmployeeServiceImplementation(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}
	
	@Override
	public int AddEmployee(EmployeeRequestDTO newEmployee) {
	    try {
	    	Department department = this.departmentRepository.getDepartmentById(newEmployee.getDepartmentId()).orElse(null);
	    	if(department == null) {
	    		return 404;
	    	}
	    	Employee employee = Employee.of(newEmployee, department);
	        employeeRepository.AddEmployee(employee);
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
	public int EditEmployee(EmployeeRequestDTO newEmployee, int employeeId) {
		try {
			if(employeeRepository.GetEmployeeById(employeeId).isPresent()) {
		    	Department department = this.departmentRepository.getDepartmentById(newEmployee.getDepartmentId()).orElse(null);
		    	if(department == null) {
		    		return 404;
		    	}
		    	Employee employee = Employee.of(newEmployee, department);
				employeeRepository.EditEmployee(employee, employeeId);
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
	public List<EmployeeResponseDTO> GetAllEmployees(){
		List<EmployeeResponseDTO> employeeDTOList;
		try {		
			List<Employee> employeeList = employeeRepository.GetAllEmployees();
			employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());
		} catch (JpaSystemException e) {
			return Collections.emptyList();
		}
		return employeeDTOList;
	}
	
	@Override
	public Optional<EmployeeResponseDTO> GetEmployeeById(int employeeId) {
		try {
			Employee employee = employeeRepository.GetEmployeeById(employeeId).orElse(null);
			if(employee == null) {
				return Optional.empty();
			}
			return Optional.of(EmployeeResponseDTO.of(employee));			
		} catch (JpaSystemException e) {
			return Optional.empty();
		}
	}
}
