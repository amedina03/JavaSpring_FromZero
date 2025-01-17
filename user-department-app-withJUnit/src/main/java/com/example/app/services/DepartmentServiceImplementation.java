package com.example.app.services;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;
import com.example.app.responses.ApiResponse;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

	private final DepartmentRepository departmentRepository;
	private final MessageSource messageSource;
	
	public DepartmentServiceImplementation(DepartmentRepository departmentRepository, MessageSource messageSource){
		this.departmentRepository = departmentRepository;
		this.messageSource = messageSource;
	}
	
	public ApiResponse<List<DepartmentResponseDTO>> getAllDepartments(){
		List<DepartmentResponseDTO> departmentDTOList;
		List<Department> departmentList = departmentRepository.getAllDepartments();
		if(departmentList.isEmpty()) {
			return new ApiResponse<List<DepartmentResponseDTO>>(messageSource.getMessage("department.empty.list", null, Locale.getDefault()), null);
		}
		departmentDTOList = departmentList.stream().map(department -> DepartmentResponseDTO.of(department)).collect(Collectors.toList());
		return new ApiResponse<List<DepartmentResponseDTO>>(messageSource.getMessage("department.found", null, Locale.getDefault()), departmentDTOList);
	}
	
	public ApiResponse<List<EmployeeResponseDTO>> getAllDepartmentEmployees(int departmentId){
		List<EmployeeResponseDTO> employeeDTOList;
		Optional<Department> selectedDepartment = departmentRepository.getDepartmentById(departmentId);
		if(selectedDepartment.isEmpty()) {
			throw new DepartmentNotFoundException(messageSource.getMessage("department.not.found", null, Locale.getDefault()));
		}
		List<Employee> employeeList = selectedDepartment.get().getEmployeeList();
		if(employeeList.isEmpty()) {
			return new ApiResponse<List<EmployeeResponseDTO>>(messageSource.getMessage("employee.empty.list", null, Locale.getDefault()), null);
		}
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());
		return new ApiResponse<List<EmployeeResponseDTO>>(messageSource.getMessage("employee.found", null, Locale.getDefault()), employeeDTOList);
	}
	
	public ApiResponse<DepartmentResponseDTO> addDepartment(Department newDepartment) {
		Department addedDepartment = departmentRepository.addDepartment(newDepartment);
		return new ApiResponse<DepartmentResponseDTO>(messageSource.getMessage("department.added", null, Locale.getDefault()), DepartmentResponseDTO.of(addedDepartment));
	}
	
	public ApiResponse<DepartmentResponseDTO> editDepartment(Department newDepartment, int departmentId) {
		if(departmentRepository.getDepartmentById(departmentId).isPresent()) {
			Department addedDepartment = departmentRepository.editDepartment(newDepartment, departmentId);
			return new ApiResponse<DepartmentResponseDTO>(messageSource.getMessage("department.edited", null, Locale.getDefault()), DepartmentResponseDTO.of(addedDepartment));
		}
		throw new DepartmentNotFoundException(messageSource.getMessage("department.not.found", null, Locale.getDefault()));
	}
	
	public ApiResponse<Void> removeDepartment(int departmentId) {
		if(departmentRepository.getDepartmentById(departmentId).isPresent()) {
			departmentRepository.removeDepartment(departmentId);
			return new ApiResponse<Void>(messageSource.getMessage("department.removed", null, Locale.getDefault()), null);
		}
		throw new DepartmentNotFoundException(messageSource.getMessage("department.not.found", null, Locale.getDefault()));
	}
}
