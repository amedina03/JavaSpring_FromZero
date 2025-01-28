package com.example.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.errors.EmployeeNotFoundException;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepositoryJPA;
import com.example.app.repositories.EmployeeRepositoryJPA;
import com.example.app.utils.MessageUtil;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private static final String EMPLOYEE_NOT_FOUND = "employee.not.found";
	
	private static final String DEPARTMENT_NOT_FOUND = "department.not.found";
	
	private final EmployeeRepositoryJPA employeeRepository;
	private final DepartmentRepositoryJPA departmentRepository;
	private final MessageUtil messageUtil;
	
	public EmployeeServiceImplementation(EmployeeRepositoryJPA employeeRepository, DepartmentRepositoryJPA departmentRepository, MessageUtil messageUtil) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.messageUtil = messageUtil;
	}

	@Override
	public List<EmployeeResponseDTO> getAllEmployees(){
		List<EmployeeResponseDTO> employeeDTOList;	
		List<Employee> employeeList = employeeRepository.findAll();
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());

		return employeeDTOList;
	}
	
	@Override
	public EmployeeResponseDTO getEmployeeById(int employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(messageUtil.getMessage(EMPLOYEE_NOT_FOUND)));
		return EmployeeResponseDTO.of(employee);
	}
	
	@Override
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO newEmployee) {
    	Department department = this.departmentRepository.findById(newEmployee.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException(messageUtil.getMessage(DEPARTMENT_NOT_FOUND)));
    	Employee employee = Employee.of(newEmployee, department);
        Employee addedEmployee = employeeRepository.save(employee);
        return EmployeeResponseDTO.of(addedEmployee);
	}
	
	@Override
	public EmployeeResponseDTO editEmployee(EmployeeRequestDTO newEmployee, int employeeId) {
		Employee existingEmployee = employeeRepository.findById(employeeId)
		        .orElseThrow(() -> new EmployeeNotFoundException(messageUtil.getMessage(EMPLOYEE_NOT_FOUND)));
		Department department = this.departmentRepository.findById(newEmployee.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException(messageUtil.getMessage(DEPARTMENT_NOT_FOUND)));

		existingEmployee.setId(employeeId);
		existingEmployee.setDepartment(department);
        Employee addedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeResponseDTO.of(addedEmployee);
	}
	
	@Override
	public void removeEmployee(int employeeId) {
    	if(employeeRepository.existsById(employeeId)) {
    		employeeRepository.deleteById(employeeId);	    		
    	}
    	throw new EmployeeNotFoundException(messageUtil.getMessage(EMPLOYEE_NOT_FOUND));
	}
}
