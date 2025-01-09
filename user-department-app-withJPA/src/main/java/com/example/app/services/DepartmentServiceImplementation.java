package com.example.app.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

	private final DepartmentRepository departmentRepository;
	
	public DepartmentServiceImplementation(DepartmentRepository departmentRepository){
		this.departmentRepository = departmentRepository;
	}
	
	public List<DepartmentResponseDTO> getAllDepartments(){
		List<DepartmentResponseDTO> departmentDTOList;
		try {
			List<Department> departmentList = departmentRepository.getAllDepartments();
			departmentDTOList = departmentList.stream().map(department -> DepartmentResponseDTO.of(department)).collect(Collectors.toList());
			return departmentDTOList;
		} catch (JpaSystemException e) {
			return Collections.emptyList();
		}
	}
	
	public List<EmployeeResponseDTO> getAllDepartmentEmployees(int departmentId){
		List<EmployeeResponseDTO> employeeDTOList;
		try {
			Optional<Department> selectedDepartment = departmentRepository.getDepartmentById(departmentId);
			if(selectedDepartment.isEmpty()) {
				return Collections.emptyList();
			}
			List<Employee> employeeList = selectedDepartment.get().getEmployeeList();
			employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());
			return employeeDTOList;
			
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
