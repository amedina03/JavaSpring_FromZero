package com.example.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.models.Employee;

public interface EmployeeRepositoryJPA extends JpaRepository<Employee, Integer>{

}
