package com.example.app.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.models.Department;
import com.example.app.responses.ApiResponse;
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
	public ResponseEntity<ApiResponse<List<DepartmentResponseDTO>>> getAllDepartments(){
		ApiResponse<List<DepartmentResponseDTO>> apiResponse = departmentService.getAllDepartments();
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@GetMapping("/{departmentId}/employees")
	@ResponseBody
	public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAllDepartmentEmployees(@PathVariable int departmentId){
		ApiResponse<List<EmployeeResponseDTO>> apiResponse = departmentService.getAllDepartmentEmployees(departmentId);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<ApiResponse<DepartmentResponseDTO>> addDepartment(@RequestBody Department newDepartment){
		ApiResponse<DepartmentResponseDTO> apiResponse = departmentService.addDepartment(newDepartment);
		return ResponseEntity.created(URI.create("/departments/" + apiResponse.getData().getId())).body(apiResponse);
	}
	
	@PutMapping("/{departmentId}")
	@ResponseBody
	public ResponseEntity<ApiResponse<DepartmentResponseDTO>> editDepartment(@RequestBody Department newDepartment, @PathVariable int departmentId) {
		ApiResponse<DepartmentResponseDTO> apiResponse = departmentService.editDepartment(newDepartment, departmentId);
		return ResponseEntity.ok().body(apiResponse);
	
	}
	
	@DeleteMapping("/{departmentId}")
	@ResponseBody
	public ResponseEntity<ApiResponse<Void>> removeDepartment(@PathVariable int departmentId) {
		departmentService.removeDepartment(departmentId);
		return ResponseEntity.noContent().build();
	}
}
