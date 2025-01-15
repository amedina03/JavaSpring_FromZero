package com.example.app.dtos;

public class EmployeeRequestDTO {
	String name;
	int departmentId;
	
	public EmployeeRequestDTO() {}
	
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
