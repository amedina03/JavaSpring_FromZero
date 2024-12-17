package com.example.app.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.services.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
	DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@GetMapping
	@ResponseBody
	public List<Department> getAllDepartments(){
		return departmentService.getAllDepartments();
	}
	
	@GetMapping("/{departmentId}/employees")
	@ResponseBody
	public List<Employee> getAllDepartmentEmployees(@PathVariable int departmentId){
		return departmentService.getAllDepartmentEmployees(departmentId);
	}
	
	@PostMapping
	@ResponseBody
	public Department addDepartment(@RequestBody Department newDepartment){
		if(departmentService.addDepartment(newDepartment) == 0) {
			return newDepartment;
		}
		return null;
	}
	
	@PutMapping("/{departmentId}")
	@ResponseBody
	public Department editDepartment(@RequestBody Department newDepartment, @PathVariable int departmentId) {
		if(departmentService.editDepartment(newDepartment, departmentId) == 0){
			return newDepartment;
		}
		return null;
	}
	
	@DeleteMapping("/{departmentId}")
	@ResponseBody
	public String removeDepartment(@PathVariable int departmentId) {
		if(departmentService.removeDepartment(departmentId) == 0) {
			return "Department removed correctly";
		}
		return "There was an error removing the department, please try again later";
	}
}
