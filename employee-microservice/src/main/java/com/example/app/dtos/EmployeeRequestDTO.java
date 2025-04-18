package com.example.app.dtos;

import jakarta.validation.constraints.NotNull;

public class EmployeeRequestDTO {
	@NotNull(message = "Employee name cannot be null")
	String name;

    @NotNull(message = "Department ID is required")
	int departmentId;

	public EmployeeRequestDTO() {}
	
	public EmployeeRequestDTO(String name, int departmentId) {
		this.name = name;
		this.departmentId = departmentId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
}
