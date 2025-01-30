package com.example.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.errors.DuplicateDepartmentEntryException;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepositoryJPA;
import com.example.app.utils.MessageUtil;

@Service
public class DepartmentServiceImplementation implements DepartmentService{
	private static final String DEPARTMENT_NOT_FOUND = "department.not.found";
	private static final String DEPARTMENT_DUPLICATE_ENTRY = "department.duplicate.entry";
	
	private final DepartmentRepositoryJPA departmentRepository;
	private final MessageUtil messageUtil;
	
	public DepartmentServiceImplementation(DepartmentRepositoryJPA departmentRepository, MessageUtil messageUtil){
		this.departmentRepository = departmentRepository;
		this.messageUtil = messageUtil;
	}
	
	public List<DepartmentResponseDTO> getAllDepartments(){
		List<DepartmentResponseDTO> departmentDTOList;
		List<Department> departmentList = departmentRepository.findAll();
		departmentDTOList = departmentList.stream().map(department -> DepartmentResponseDTO.of(department)).collect(Collectors.toList());
		
		return departmentDTOList;
	}
	
	public List<EmployeeResponseDTO> getAllDepartmentEmployees(int departmentId){
		List<EmployeeResponseDTO> employeeDTOList;
		Department selectedDepartment = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(messageUtil.getMessage(DEPARTMENT_NOT_FOUND)));
		List<Employee> employeeList = selectedDepartment.getEmployeeList();
		employeeDTOList = employeeList.stream().map(employee -> EmployeeResponseDTO.of(employee)).collect(Collectors.toList());
		return employeeDTOList;
	}
	
	public DepartmentResponseDTO addDepartment(Department newDepartment) {    
		if(departmentRepository.existsByName(newDepartment.getName())) {
			throw new DuplicateDepartmentEntryException(DEPARTMENT_DUPLICATE_ENTRY);
		}
		Department addedDepartment = departmentRepository.save(newDepartment);
		return DepartmentResponseDTO.of(addedDepartment);

	}
	
	public DepartmentResponseDTO editDepartment(Department newDepartment, int departmentId) {
		if(departmentRepository.existsByName(newDepartment.getName())) {
			throw new DuplicateDepartmentEntryException(DEPARTMENT_DUPLICATE_ENTRY);
		}
		Department existingDepartment = departmentRepository.findById(departmentId)
		        .orElseThrow(() -> new DepartmentNotFoundException(messageUtil.getMessage(DEPARTMENT_NOT_FOUND)));
		existingDepartment.setName(newDepartment.getName());
		Department updatedDepartment = departmentRepository.save(existingDepartment);
		return DepartmentResponseDTO.of(updatedDepartment);
	}
	
	public void removeDepartment(int departmentId) {
		if(!departmentRepository.existsById(departmentId)) {
			throw new DepartmentNotFoundException(messageUtil.getMessage(DEPARTMENT_NOT_FOUND));
		}
		departmentRepository.deleteById(departmentId);
	}
}
