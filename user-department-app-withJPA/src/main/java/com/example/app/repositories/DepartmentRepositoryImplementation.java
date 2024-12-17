package com.example.app.repositories;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;

import com.example.app.models.Department;

@Repository
public class DepartmentRepositoryImplementation implements DepartmentRepository {
	
	private final DepartmentRepositoryJPA departmentRepositoryJPA;
	
	public DepartmentRepositoryImplementation(DepartmentRepositoryJPA departmentRepositoryJPA) {
		this.departmentRepositoryJPA = departmentRepositoryJPA;
	}
	
	@Override
	public List<Department> getAllDepartments() {
		return this.departmentRepositoryJPA.findAll();
	}

	@Override
	public int addDepartment(Department newDepartment) {
		try {
			this.departmentRepositoryJPA.save(newDepartment);
			return 0;
		} catch(DataIntegrityViolationException e) {
			return 409;
		} catch (JpaSystemException e) {
			return 500;
		}
	}

	@Override
	public int editDepartment(Department newDepartment, int departmentId) {
		try {
			if(this.departmentRepositoryJPA.existsById(departmentId)) {
				newDepartment.setId(departmentId);				
				this.departmentRepositoryJPA.save(newDepartment);
				return 0;
			}
			return 404;
		} catch(DataIntegrityViolationException e) {
			return 409;
		} catch (JpaSystemException e) {
			return 500;
		}
	}

	@Override
	public int removeDepartment(int departmentId) {
		try {
			if(this.departmentRepositoryJPA.existsById(departmentId)) {
				this.departmentRepositoryJPA.deleteById(departmentId);
				return 0;
			}
			return 404;
		} catch(JpaSystemException e) {
			return 500;
		}
	}
}
