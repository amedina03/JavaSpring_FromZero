package com.example.app.dtos;

import com.example.app.models.Department;

public class DepartmentResponseDTO {
	int id;
	String name;
	
	public DepartmentResponseDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static DepartmentResponseDTO of(Department department) {
		return new DepartmentResponseDTO(department.getId(), department.getName());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}