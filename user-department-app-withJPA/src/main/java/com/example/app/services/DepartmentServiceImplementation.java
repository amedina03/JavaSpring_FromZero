package com.example.app.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

	private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;
	
	public DepartmentServiceImplementation(DepartmentRepository departmentRepository, EmployeeService employeeService){
		this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
	}
	
	public List<Department> getAllDepartments(){
		try {
			return departmentRepository.getAllDepartments();			
		} catch (JpaSystemException e) {
			return Collections.emptyList();
		}
	}
	
	public List<Employee> getAllDepartmentEmployees(int departmentId){
		try {
			Optional<Department> selectedDepartment = departmentRepository.getDepartmentById(departmentId);
			if(selectedDepartment.isEmpty()) {
				return Collections.emptyList();
			}
			return selectedDepartment.get().getEmployeeList();
			
		} catch (JpaSystemException e) {
			return Collections.emptyList();
		}
	}
	
	public int addDepartment(Department newDepartment) {
		try {
			departmentRepository.addDepartment(newDepartment);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 409;
		} catch (JpaSystemException e) {
			return 500;
		}
	}
	
	public int editDepartment(Department newDepartment, int departmentId) {
		try {
			if(departmentRepository.getDepartmentById(departmentId).isPresent()) {
				departmentRepository.editDepartment(newDepartment, departmentId);
				return 0;
			}
			return 404;
		} catch (DataIntegrityViolationException e) {
			return 409;
		} catch (JpaSystemException e) {
			return 500;
		}
	}
	
	public int removeDepartment(int departmentId) {
		try {
			if(departmentRepository.getDepartmentById(departmentId).isPresent()) {
				departmentRepository.removeDepartment(departmentId);
				return 0;
			}
			return 404;
		} catch (DataIntegrityViolationException e) {
			return 409;
		} catch (JpaSystemException e) {
			return 500;
		}
	}
}
