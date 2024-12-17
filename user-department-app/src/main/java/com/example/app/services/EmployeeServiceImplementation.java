package com.example.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;
import com.example.app.repositories.EmployeeRepositoryFromList;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private final EmployeeRepositoryFromList employeeRepository;
    private final DepartmentRepository departmentRepository;
	
	public EmployeeServiceImplementation(EmployeeRepositoryFromList employeeRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}
	
	@Override
	public int AddEmployee(Employee newEmployee) {
		List<Employee> employeeList = employeeRepository.GetAllEmployees();

		if(!departmentRepository.getAllDepartments().stream().anyMatch(department -> department.getId() == newEmployee.getDepartmentId())) {
			return 404;
		}
		if(employeeList.stream().anyMatch(x -> x.getId() == newEmployee.getId())) {
			return 409;
		}
		return employeeRepository.AddEmployee(newEmployee);
	}
	
	@Override
	public int RemoveEmployee(int employeeId) {
		List<Employee> employeeList = employeeRepository.GetAllEmployees();

		if(!employeeList.stream().anyMatch(department -> department.getId() == employeeId)) {
			return 404;
		}
		return employeeRepository.RemoveEmployee(employeeId);
	}
	
	@Override
	public int EditEmployee(Employee newEmployee, int employeeId) {
		List<Employee> employeeList = employeeRepository.GetAllEmployees();

		if(!departmentRepository.getAllDepartments().stream().anyMatch(department -> department.getId() == newEmployee.getDepartmentId())) {
			return 404;
		}
		if(employeeList.stream().anyMatch(x -> (x.getId() == newEmployee.getId() && x.getId() != employeeId))) {
			return 409;
		}
		return employeeRepository.EditEmployee(newEmployee, employeeId);
	}
	
	@Override
	public List<Employee> GetAllEmployees(){
		return employeeRepository.GetAllEmployees();
	}
	
	@Override
	public Employee GetEmployeeById(int employeeId) {
		return employeeRepository.GetEmployeeById(employeeId);
	}
}
