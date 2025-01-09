package com.example.app.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.app.models.Department;

@Repository
public class DepartmentRepositoryFromList implements DepartmentRepository{

	private final List<Department> departmentList = new LinkedList<Department>();
	
	public List<Department> getAllDepartments(){
		return List.of();
	}
	
	public int addDepartment(Department newDepartment) {
		return 0;
	}
	
	public int editDepartment(Department newDepartment, int departmentId) {
		return 0;
	}
	
	public int removeDepartment(int departmentId) {
		return 0;
	}
}
