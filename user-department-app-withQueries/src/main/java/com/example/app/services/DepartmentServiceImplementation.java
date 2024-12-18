package com.example.app.services;

import java.util.List;
import java.util.stream.Collectors;

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
		return departmentRepository.getAllDepartments();
	}
	
	public List<Employee> getAllDepartmentEmployees(int departmentId){
		if(!departmentRepository.getAllDepartments().stream().anyMatch(department -> department.getId() == departmentId)){
			return null;
		}
		return employeeService.GetAllEmployees().stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .collect(Collectors.toList());
	}
	
	public int addDepartment(Department newDepartment) {
		if(departmentRepository.getDepartmentById(newDepartment.getId()).isPresent()) {
			return 409;
		}
		return departmentRepository.addDepartment(newDepartment);
	}
	
	public int editDepartment(Department newDepartment, int departmentId) {
		if(departmentRepository.getDepartmentById(departmentId).isEmpty()) {
			return 404;
		} else if(newDepartment.getId() != 0) {
			return 400;
		} 
		return departmentRepository.editDepartment(newDepartment, departmentId);
	}
	
	public int removeDepartment(int departmentId) {
		if(departmentRepository.getDepartmentById(departmentId).isEmpty()) {
			return 404;
		}
		if(employeeService.GetAllEmployees().stream().anyMatch(employee -> employee.getDepartmentId() == departmentId)) {
			return 409;
		}
		return departmentRepository.removeDepartment(departmentId);
	}
}
