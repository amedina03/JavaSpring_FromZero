package com.example.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.app.dtos.ApiResponseDTO;
import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.errors.EmployeeNotFoundException;
import com.example.app.models.Employee;
import com.example.app.repositories.EmployeeRepositoryJPA;
import com.example.app.utils.MessageUtil;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private static final String EMPLOYEE_NOT_FOUND = "employee.not.found";
	
	private static final String DEPARTMENT_NOT_FOUND = "department.not.found";
	
	private final EmployeeRepositoryJPA employeeRepository;
	private final MessageUtil messageUtil;
	private final WebClient webClient;

	private final String departmentUri;
	private final String departmentNameUri;
	
	public EmployeeServiceImplementation(EmployeeRepositoryJPA employeeRepository, MessageUtil messageUtil, WebClient.Builder webClientBuilder,
			@Value("{departments.service.url}") String departmentBaseUrl, @Value("{department.uri}") String departmentUri, @Value("{department.name.uri}") String departmentNameUri) {
		this.employeeRepository = employeeRepository;
		this.messageUtil = messageUtil;
		this.webClient = webClientBuilder.baseUrl(departmentBaseUrl).build();
		this.departmentUri = departmentUri;
		this.departmentNameUri = departmentNameUri;
	}

	@Override
	public List<EmployeeResponseDTO> getAllEmployees(){
		List<EmployeeResponseDTO> employeeDTOList;	
		List<Employee> employeeList = employeeRepository.findAll();
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee, getDepartmentName(employee.getDepartmentId()))).collect(Collectors.toList());

		return employeeDTOList;
	}
	
	@Override
	public EmployeeResponseDTO getEmployeeById(int employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(messageUtil.getMessage(EMPLOYEE_NOT_FOUND)));
		String departmentName = getDepartmentName(employee.getDepartmentId());
		
		return EmployeeResponseDTO.of(employee, departmentName);
	}
	
	@Override
	public List<EmployeeResponseDTO> getEmployeesByDepartmentId(int departmentId) {
		String departmentName = getDepartmentName(departmentId);
		
		List<EmployeeResponseDTO> employeeDTOList;	
		List<Employee> employeeList = employeeRepository.findAllByDepartmentId(departmentId);
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee,departmentName)).collect(Collectors.toList());

		return employeeDTOList;
	}
	
	@Override
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO newEmployee) {
		String departmentName = getDepartmentName(newEmployee.getDepartmentId());
		
    	Employee employee = Employee.of(newEmployee);
        Employee addedEmployee = employeeRepository.save(employee);
        return EmployeeResponseDTO.of(addedEmployee, departmentName);
	}
	
	@Override
	public EmployeeResponseDTO editEmployee(EmployeeRequestDTO newEmployee, int employeeId) {
		int departmentId = newEmployee.getDepartmentId();
		Employee existingEmployee = employeeRepository.findById(employeeId)
		        .orElseThrow(() -> new EmployeeNotFoundException(messageUtil.getMessage(EMPLOYEE_NOT_FOUND)));
		String departmentName = getDepartmentName(departmentId);
		
		existingEmployee.setName(newEmployee.getName());
		existingEmployee.setDepartmentId(departmentId);
        Employee addedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeResponseDTO.of(addedEmployee, departmentName);
	}
	
	@Override
	public void removeEmployee(int employeeId) {
    	if(!employeeRepository.existsById(employeeId)) {
        	throw new EmployeeNotFoundException(messageUtil.getMessage(EMPLOYEE_NOT_FOUND)); 		
    	}
		employeeRepository.deleteById(employeeId);	   
	}
	
	private String getDepartmentName(int departmentId) {
		checkIfDepartmentExists(departmentId);
		ApiResponseDTO<String> response = webClient.
				get().
				uri(departmentNameUri, departmentId).
				retrieve().
				bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<String>>() {}).
				block();

	    if (response == null || response.getData() == null || response.getData().isEmpty()) {
	        throw new IllegalStateException();
	    }
	    
		return response.getData().get(0);
	}
	
	private void checkIfDepartmentExists(int departmentId) {
		ResponseEntity<Void> response = webClient.
				head().
				uri(departmentUri, departmentId).
				retrieve().
				toBodilessEntity().
				block();
		
		if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
			throw new DepartmentNotFoundException(messageUtil.getMessage(DEPARTMENT_NOT_FOUND));
		}
	}
}
