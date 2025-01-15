package com.example.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.models.Department;

public interface DepartmentRepositoryJPA extends JpaRepository<Department, Integer>{

}
