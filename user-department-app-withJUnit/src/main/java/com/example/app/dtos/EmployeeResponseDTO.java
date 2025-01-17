package com.example.app.dtos;

import com.example.app.models.Employee;

public class EmployeeResponseDTO {
	int id;
	String name;
	String departmentName;
	
    public EmployeeResponseDTO(int id, String name, String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentName = departmentName;
    }
    
    public static EmployeeResponseDTO of(Employee employee) {
    	return new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getDepartment().getName());
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
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
