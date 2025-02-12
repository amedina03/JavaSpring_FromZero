package com.example.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.models.Employee;

public interface EmployeeRepositoryJPA extends JpaRepository<Employee, Integer>{
	public List<Employee> findAllByDepartmentId(int departmentId);
}
