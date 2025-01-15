package com.example.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.models.Department;

@Repository
public class DepartmentRepositoryImplementation implements DepartmentRepository {
	
	private final DepartmentRepositoryJPA departmentRepositoryJPA;
	
	public DepartmentRepositoryImplementation(DepartmentRepositoryJPA departmentRepositoryJPA) {
		this.departmentRepositoryJPA = departmentRepositoryJPA;
	}
	
	@Override
	public Optional<Department> getDepartmentById(int departmentId) {
		return this.departmentRepositoryJPA.findById(departmentId);
	}
	
	@Override
	public List<Department> getAllDepartments() {
		return this.departmentRepositoryJPA.findAll();
	}

	@Override
	public Department addDepartment(Department newDepartment) {
		return this.departmentRepositoryJPA.save(newDepartment);
	}

	@Override
	public Department editDepartment(Department newDepartment, int departmentId) {
		newDepartment.setId(departmentId);
		return this.departmentRepositoryJPA.save(newDepartment);
	}

	@Override
	public void removeDepartment(int departmentId) {
		this.departmentRepositoryJPA.deleteById(departmentId);
	}
}
