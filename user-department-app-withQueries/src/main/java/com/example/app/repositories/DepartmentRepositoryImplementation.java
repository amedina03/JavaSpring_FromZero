package com.example.app.repositories;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.app.mappers.DepartmentRowMapper;
import com.example.app.models.Department;

@Repository
@Primary
public class DepartmentRepositoryImplementation implements DepartmentRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public DepartmentRepositoryImplementation(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Department> getAllDepartments() {
		String sql = "SELECT * FROM Departments";
		try {
			return jdbcTemplate.query(sql, new DepartmentRowMapper());
		} catch (DataAccessException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public int addDepartment(Department newDepartment) {
		String sql = "INSERT INTO Departments (name) VALUES (?)";
		try {
			int rowsAffected = jdbcTemplate.update(sql, newDepartment.getName());
			if(rowsAffected > 0) {
				return 0;
			}
			return 500;
		} catch (DataAccessException e) {
			return 500;
		}
	}

	@Override
	public int editDepartment(Department newDepartment, int departmentId) {
		String sql = "UPDATE Departments SET name = ? WHERE id = ?";
		try {
			int rowsAffected = jdbcTemplate.update(sql, newDepartment.getName(), departmentId);
			if(rowsAffected > 0) {
				return 0;
			}
			return 500;
		} catch (DataAccessException e) {
			return 500;
		}
	}

	@Override
	public int removeDepartment(int departmentId) {
		String sql = "DELETE FROM Departments WHERE id = ?";
		try {
			int rowsAffected = jdbcTemplate.update(sql, departmentId);
			if(rowsAffected > 0) {
				return 0;
			}
			return 500;
		} catch (DataAccessException e) {
			return 500;
		}
	}
}
