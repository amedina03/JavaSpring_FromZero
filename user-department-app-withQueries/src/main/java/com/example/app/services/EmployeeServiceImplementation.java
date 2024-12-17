package com.example.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepository;
import com.example.app.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
	
	public EmployeeServiceImplementation(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
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
		employeeRepository.AddEmployee(newEmployee);
		return 0;
	}
	
	@Override
	public int RemoveEmployee(int employeeId) {
		List<Employee> employeeList = employeeRepository.GetAllEmployees();
		for (int i = 0; i < employeeList.size(); i++) {
			if(employeeList.get(i).getId() == employeeId) {
				employeeRepository.RemoveEmployee(i);
				return 0;
			}
		}
		return 404;
	}
	
	@Override
	public int EditEmployee(Employee newEmployee, int employeeId) {
		List<Employee> employeeList = employeeRepository.GetAllEmployees();
		if(newEmployee.getId() != employeeId) {
			return 400;
		}
		if(!departmentRepository.getAllDepartments().stream().anyMatch(department -> department.getId() == newEmployee.getDepartmentId())) {
			return 404;
		}
		if(employeeList.stream().anyMatch(x -> (x.getId() == newEmployee.getId() && x.getId() != employeeId))) {
			return 409;
		}
		for (int i = 0; i < employeeList.size(); i++) {
			if(employeeList.get(i).getId() == employeeId) {
				employeeRepository.EditEmployee(newEmployee, i);
				return 0;
			}
		}
		return 500;
	}
	
	@Override
	public List<Employee> GetAllEmployees(){
		return employeeRepository.GetAllEmployees();
	}
	
	@Override
	public Employee GetEmployeeById(int employeeId) {
		return employeeRepository.GetEmployeeById(employeeId).orElse(null);
	}
}
