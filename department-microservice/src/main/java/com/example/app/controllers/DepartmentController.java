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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.models.Department;
import com.example.app.responses.ApiStandardResponse;
import com.example.app.services.DepartmentService;
import com.example.app.utils.MessageUtil;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

	private static final String DEPARTMENT_EMPTY_LIST = "department.empty.list";
	private static final String DEPARTMENT_FOUND = "department.found";
	private static final String DEPARTMENT_ADDED = "department.added";
	private static final String DEPARTMENT_EDITED = "department.edited";
	
	private static final String EMPLOYEE_FOUND = "department.found";
	
	DepartmentService departmentService;
	private final MessageUtil messageUtil;
	
	public DepartmentController(DepartmentService departmentService, MessageUtil messageUtil) {
		this.departmentService = departmentService;
		this.messageUtil = messageUtil;
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<List<DepartmentResponseDTO>>> getAllDepartments(){
		List<DepartmentResponseDTO> departmentResponseDTO = departmentService.getAllDepartments();
		ApiStandardResponse<List<DepartmentResponseDTO>> apiResponse = departmentResponseDTO.isEmpty() ? new ApiStandardResponse<>(messageUtil.getMessage(DEPARTMENT_EMPTY_LIST), null) : new ApiStandardResponse<>(messageUtil.getMessage(DEPARTMENT_FOUND), departmentResponseDTO);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@RequestMapping(value = "/{departmentId}", method = RequestMethod.HEAD)
	@ResponseBody
	public ResponseEntity<Void> checkDepartmentExistsById(@PathVariable int departmentId){
		return departmentService.checkDepartmentExistsById(departmentId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{departmentId}/name")
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<String>> getDepartmentName(@PathVariable int departmentId){
		String employeeName = departmentService.getDepartmentName(departmentId);
		ApiStandardResponse<String> apiResponse = new ApiStandardResponse<>(messageUtil.getMessage(EMPLOYEE_FOUND), employeeName);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<DepartmentResponseDTO>> addDepartment(@RequestBody @Valid Department newDepartment){
		DepartmentResponseDTO departmentResponseDTO = departmentService.addDepartment(newDepartment);
		ApiStandardResponse<DepartmentResponseDTO> apiResponse = new ApiStandardResponse<>(messageUtil.getMessage(DEPARTMENT_ADDED), departmentResponseDTO);
		return ResponseEntity.created(URI.create("/departments/" + apiResponse.getData().getId())).body(apiResponse);
	}
	
	@PutMapping("/{departmentId}")
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<DepartmentResponseDTO>> editDepartment(@RequestBody @Valid Department newDepartment, @PathVariable int departmentId) {
		DepartmentResponseDTO departmentResponseDTO = departmentService.editDepartment(newDepartment, departmentId);
		ApiStandardResponse<DepartmentResponseDTO> apiResponse = new ApiStandardResponse<>(messageUtil.getMessage(DEPARTMENT_EDITED), departmentResponseDTO);
		return ResponseEntity.ok().body(apiResponse);
	
	}
	
	@DeleteMapping("/{departmentId}")
	@ResponseBody
	public ResponseEntity<ApiStandardResponse<Void>> removeDepartment(@PathVariable int departmentId) {
		departmentService.removeDepartment(departmentId);
		return ResponseEntity.noContent().build();
	}
}
