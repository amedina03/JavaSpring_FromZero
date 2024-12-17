package com.example.app.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.app.models.Department;

public class DepartmentRowMapper implements RowMapper<Department>{
	@Override
	public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("id"));
		department.setName(rs.getString("name"));
		return department;
	}
}
