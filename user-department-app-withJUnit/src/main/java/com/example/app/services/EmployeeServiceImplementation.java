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
import com.example.app.repositories.DepartmentRepository;
import com.example.app.repositories.EmployeeRepository;
import com.example.app.responses.ApiResponse;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final MessageSource messageSource;
	
	public EmployeeServiceImplementation(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, MessageSource messageSource) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.messageSource = messageSource;
	}

	@Override
	public ApiResponse<List<EmployeeResponseDTO>> GetAllEmployees(){
		List<EmployeeResponseDTO> employeeDTOList;
		List<Employee> employeeList = employeeRepository.GetAllEmployees();
		if(employeeList.isEmpty()) {
			return new ApiResponse<List<EmployeeResponseDTO>>(messageSource.getMessage("employee.empty.list", null, Locale.getDefault()), null);
		}
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());
		return new ApiResponse<List<EmployeeResponseDTO>>(messageSource.getMessage("employee.found", null, Locale.getDefault()), employeeDTOList);
	}
	
	@Override
	public ApiResponse<EmployeeResponseDTO> GetEmployeeById(int employeeId) {
		Employee employee = employeeRepository.GetEmployeeById(employeeId).orElse(null);
		if(employee == null) {
			throw new EmployeeNotFoundException(messageSource.getMessage("employee.not.found", null, Locale.getDefault()));
		}
		return new ApiResponse<EmployeeResponseDTO>(messageSource.getMessage("employee.found", null, Locale.getDefault()), EmployeeResponseDTO.of(employee));
	}
	
	@Override
	public ApiResponse<EmployeeResponseDTO> AddEmployee(EmployeeRequestDTO newEmployee) {
    	Department department = this.departmentRepository.getDepartmentById(newEmployee.getDepartmentId()).orElse(null);
    	if(department == null) {
    		throw new DepartmentNotFoundException(messageSource.getMessage("department.not.found", null, Locale.getDefault()));
    	}
    	Employee employee = Employee.of(newEmployee, department);
        Employee addedEmployee = employeeRepository.AddEmployee(employee);
        EmployeeResponseDTO response = EmployeeResponseDTO.of(addedEmployee);
        return new ApiResponse<EmployeeResponseDTO>(messageSource.getMessage("employee.added", null, Locale.getDefault()), response);
	}
	
	@Override
	public ApiResponse<EmployeeResponseDTO> EditEmployee(EmployeeRequestDTO newEmployee, int employeeId) {
		if(employeeRepository.GetEmployeeById(employeeId).isPresent()) {
	    	Department department = this.departmentRepository.getDepartmentById(newEmployee.getDepartmentId()).orElse(null);
	    	if(department == null) {
	    		throw new DepartmentNotFoundException(messageSource.getMessage("department.not.found", null, Locale.getDefault()));
	    	}
	    	Employee employee = Employee.of(newEmployee, department);
	        Employee addedEmployee = employeeRepository.EditEmployee(employee, employeeId);
	        EmployeeResponseDTO response = EmployeeResponseDTO.of(addedEmployee);   
	        return new ApiResponse<EmployeeResponseDTO>(messageSource.getMessage("employee.edited", null, Locale.getDefault()), response);    
		}
		throw new EmployeeNotFoundException(messageSource.getMessage("employee.not.found", null, Locale.getDefault()));
	}
	
	@Override
	public ApiResponse<Void> RemoveEmployee(int employeeId) {
		if(employeeRepository.GetEmployeeById(employeeId).isPresent()) {
			employeeRepository.RemoveEmployee(employeeId);
			return new ApiResponse<Void>("employee.removed", null);	    		
		}
		throw new EmployeeNotFoundException(messageSource.getMessage("employee.not.found", null, Locale.getDefault()));
	}
}
