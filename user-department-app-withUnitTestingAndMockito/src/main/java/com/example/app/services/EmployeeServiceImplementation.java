package com.example.app.services;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.errors.EmployeeNotFoundException;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepositoryJPA;
import com.example.app.repositories.EmployeeRepositoryJPA;
import com.example.app.responses.ApiResponse;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private static final String EMPLOYEE_EMPTY_LIST = "employee.empty.list";
	private static final String EMPLOYEE_NOT_FOUND = "employee.not.found";
	private static final String EMPLOYEE_FOUND = "employee.found";
	private static final String EMPLOYEE_ADDED = "employee.added";
	private static final String EMPLOYEE_EDITED = "employee.edited";
	private static final String EMPLOYEE_REMOVED = "employee.removed";
	
	private static final String DEPARTMENT_NOT_FOUND = "department.not.found";
	
	private final EmployeeRepositoryJPA employeeRepository;
	private final DepartmentRepositoryJPA departmentRepository;
	private final MessageSource messageSource; 
	
	public EmployeeServiceImplementation(EmployeeRepositoryJPA employeeRepository, DepartmentRepositoryJPA departmentRepository, MessageSource messageSource) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.messageSource = messageSource;
	}

	@Override
	public ApiResponse<List<EmployeeResponseDTO>> GetAllEmployees(){
		List<EmployeeResponseDTO> employeeDTOList;	
		List<Employee> employeeList = employeeRepository.findAll();
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());

		return employeeDTOList.isEmpty()
			    ? new ApiResponse<>(getMessage(EMPLOYEE_EMPTY_LIST), null)
			    : new ApiResponse<>(getMessage(EMPLOYEE_FOUND), employeeDTOList);}
	
	@Override
	public ApiResponse<EmployeeResponseDTO> GetEmployeeById(int employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(messageSource.getMessage(EMPLOYEE_NOT_FOUND, null, Locale.getDefault())));
		return new ApiResponse<EmployeeResponseDTO>(getMessage(EMPLOYEE_FOUND), EmployeeResponseDTO.of(employee));
	}
	
	@Override
	public ApiResponse<EmployeeResponseDTO> AddEmployee(EmployeeRequestDTO newEmployee) {
    	Department department = this.departmentRepository.findById(newEmployee.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException(messageSource.getMessage(DEPARTMENT_NOT_FOUND, null, Locale.getDefault())));
    	Employee employee = Employee.of(newEmployee, department);
        Employee addedEmployee = employeeRepository.save(employee);
        EmployeeResponseDTO response = EmployeeResponseDTO.of(addedEmployee);
        return new ApiResponse<EmployeeResponseDTO>(getMessage(EMPLOYEE_ADDED), response);
	}
	
	@Override
	public ApiResponse<EmployeeResponseDTO> EditEmployee(EmployeeRequestDTO newEmployee, int employeeId) {
		if(employeeRepository.findById(employeeId).isPresent()) {
			Department department = this.departmentRepository.findById(newEmployee.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException(messageSource.getMessage(DEPARTMENT_NOT_FOUND, null, Locale.getDefault())));
	    	Employee employee = Employee.of(newEmployee, department);
	    	employee.setId(employeeId);
	        Employee addedEmployee = employeeRepository.save(employee);
	        EmployeeResponseDTO response = EmployeeResponseDTO.of(addedEmployee);   
	        return new ApiResponse<EmployeeResponseDTO>(getMessage(EMPLOYEE_EDITED), response);    
		}
		throw new EmployeeNotFoundException(getMessage(EMPLOYEE_NOT_FOUND));
	}
	
	@Override
	public ApiResponse<Void> RemoveEmployee(int employeeId) {
    	if(employeeRepository.findById(employeeId).isPresent()) {
    		employeeRepository.deleteById(employeeId);
    		return new ApiResponse<Void>(getMessage(EMPLOYEE_REMOVED), null);	    		
    	}
    	throw new EmployeeNotFoundException(getMessage(EMPLOYEE_NOT_FOUND));
	}
	
	private String getMessage(String code) {
		return this.messageSource.getMessage(code, null, Locale.getDefault());
	}
}
