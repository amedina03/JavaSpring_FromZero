package com.example.app.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.app.mappers.EmployeeRowMapper;
import com.example.app.models.Employee;

@Repository
@Primary
public class EmployeeRepositoryImplementation implements EmployeeRepository {

	private final JdbcTemplate jdbcTemplate;
	
	public EmployeeRepositoryImplementation(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int AddEmployee(Employee newEmployee) {
		String sql = "INSERT INTO Employees (name, departmentId) VALUES (?, ?)";
		try {
			int rowsAffected = jdbcTemplate.update(sql, newEmployee.getName(), newEmployee.getDepartmentId());
			if(rowsAffected > 0) {
				return 0;
			}
			return 500;
		} catch(DataIntegrityViolationException e) {
			return 404;
		} catch(DataAccessException e) {
			return 500;
		}
	}

	@Override
	public int RemoveEmployee(int employeeId) {
		String sql = "DELETE FROM Employees WHERE id = ?";
		try {
			int rowsAffected = jdbcTemplate.update(sql, employeeId);
			if(rowsAffected > 0) {
				return 0;
			}
			return 500;
		} catch (DataAccessException e) {
			return 500;
		}
	}

	@Override
	public int EditEmployee(Employee newEmployee, int employeeId) {
		String sql = "UPDATE Employees SET name = ?, departmentId = ? WHERE id = ?";
		try {
			int rowsAffected = jdbcTemplate.update(sql, newEmployee.getName(), newEmployee.getDepartmentId(), employeeId);
			if(rowsAffected > 0) {
				return 0;
			}
			return 500;
		} catch (DataAccessException e) {
			return 500;
		}
	}

	@Override
	public List<Employee> GetAllEmployees() {
		String sql = "SELECT * FROM Employees";
		try {
			return jdbcTemplate.query(sql, new EmployeeRowMapper());
		} catch(DataAccessException e) {
			return Collections.emptyList();
		} 
	}

	@Override
	public Optional<Employee> GetEmployeeById(int employeeId) {
		String sql = "SELECT * FROM Employees WHERE id = ?";
		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), employeeId));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}
}
