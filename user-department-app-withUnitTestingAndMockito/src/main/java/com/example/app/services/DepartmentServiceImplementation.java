package com.example.app.services;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepositoryJPA;
import com.example.app.responses.ApiResponse;

@Service
public class DepartmentServiceImplementation implements DepartmentService{
	private static final String DEPARTMENT_EMPTY_LIST = "department.empty.list";
	private static final String DEPARTMENT_NOT_FOUND = "department.not.found";
	private static final String DEPARTMENT_FOUND = "department.found";
	private static final String DEPARTMENT_ADDED = "department.added";
	private static final String DEPARTMENT_EDITED = "department.edited";
	private static final String DEPARTMENT_REMOVED = "department.removed";

	private static final String EMPLOYEE_EMPTY_LIST = "employee.empty.list";
	private static final String EMPLOYEE_NOT_FOUND = "employee.not.found";
	
	private final DepartmentRepositoryJPA departmentRepository;
	private final MessageSource messageSource;
	
	public DepartmentServiceImplementation(DepartmentRepositoryJPA departmentRepository, MessageSource messageSource){
		this.departmentRepository = departmentRepository;
		this.messageSource = messageSource;
	}
	
	public ApiResponse<List<DepartmentResponseDTO>> getAllDepartments(){
		List<DepartmentResponseDTO> departmentDTOList;
		List<Department> departmentList = departmentRepository.findAll();
		departmentDTOList = departmentList.stream().map(department -> DepartmentResponseDTO.of(department)).collect(Collectors.toList());
		

		return departmentDTOList.isEmpty()
			    ? new ApiResponse<>(getMessage(DEPARTMENT_EMPTY_LIST), null)
			    : new ApiResponse<>(getMessage(DEPARTMENT_FOUND), departmentDTOList);
	}
	
	public ApiResponse<List<EmployeeResponseDTO>> getAllDepartmentEmployees(int departmentId){
		List<EmployeeResponseDTO> employeeDTOList;
		Department selectedDepartment = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(messageSource.getMessage(DEPARTMENT_NOT_FOUND, null, Locale.getDefault())));
		List<Employee> employeeList = selectedDepartment.getEmployeeList();
		if(employeeList.isEmpty()) {
			return new ApiResponse<List<EmployeeResponseDTO>>(getMessage(EMPLOYEE_EMPTY_LIST), Collections.emptyList());
		}
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());
		return new ApiResponse<List<EmployeeResponseDTO>>(getMessage(EMPLOYEE_NOT_FOUND), employeeDTOList);
	}
	
	public ApiResponse<DepartmentResponseDTO> addDepartment(Department newDepartment) {
		Department addedDepartment = departmentRepository.save(newDepartment);
		return new ApiResponse<DepartmentResponseDTO>(getMessage(DEPARTMENT_ADDED), DepartmentResponseDTO.of(addedDepartment));

	}
	
	public ApiResponse<DepartmentResponseDTO> editDepartment(Department newDepartment, int departmentId) {
		if(departmentRepository.findById(departmentId).isPresent()) {
			newDepartment.setId(departmentId);
			Department addedDepartment = departmentRepository.save(newDepartment);
			return new ApiResponse<DepartmentResponseDTO>(getMessage(DEPARTMENT_EDITED), DepartmentResponseDTO.of(addedDepartment));
		}
		throw new DepartmentNotFoundException(getMessage(DEPARTMENT_NOT_FOUND));
	}
	
	public ApiResponse<Void> removeDepartment(int departmentId) {
		if(departmentRepository.findById(departmentId).isPresent()) {
			departmentRepository.deleteById(departmentId);
			return new ApiResponse<Void>(getMessage(DEPARTMENT_REMOVED), null);
		}
		throw new DepartmentNotFoundException(getMessage(DEPARTMENT_NOT_FOUND));
	}
	
	private String getMessage(String code) {
		return this.messageSource.getMessage(code, null, Locale.getDefault());
	}
}
