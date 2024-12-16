package com.example.app.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.app.models.Department;

@Repository
public class DepartmentRepositoryFromList implements DepartmentRepository{

	private final List<Department> departmentList = new LinkedList<Department>();
	
	public List<Department> getAllDepartments(){
		return new LinkedList<Department>(departmentList);
	}
	
	
	public int addDepartment(Department newDepartment) {
		departmentList.add(newDepartment);
		return 0;
	}
	
	public int editDepartment(Department newDepartment, int departmentId) {
		for (int i = 0; i < departmentList.size(); i++) {
			if(departmentList.get(i).getId() == departmentId) {
				departmentList.remove(i);
				return 0;
			}
		}
		return 404;
	}
	
	public int removeDepartment(int departmentId) {
		for (int i = 0; i < departmentList.size(); i++) {
			if(departmentList.get(i).getId() == departmentId) {
				departmentList.remove(i);
				return 0;
			}
		}
		return 404;
	}
}
