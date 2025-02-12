package com.example.app.dtos;


public class EmployeeResponseDTO {
	int id;
	String name;
	String departmentName;
	
    public EmployeeResponseDTO(int id, String name, String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentName = departmentName;
    }
    
    @Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || o.getClass() != this.getClass())
			return false;
		EmployeeResponseDTO castedObject = (EmployeeResponseDTO) o;
		if(castedObject.getId() != this.getId() || !this.getName().equals(castedObject.getName()) || !this.getDepartmentName().equals(castedObject.getDepartmentName()))
			return false;
		return true;
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
