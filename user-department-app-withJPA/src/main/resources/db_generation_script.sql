CREATE DATABASE IF NOT EXISTS springdb;
USE springdb;

DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

CREATE TABLE departments(
	id INT PRIMARY KEY auto_increment,
    name VARCHAR(124) NOT NULL
);

CREATE TABLE employees(
	id INT PRIMARY KEY auto_increment,
    name VARCHAR(124) NOT NULL,
    departmentId INT,
    FOREIGN KEY (departmentId) REFERENCES departments(id)
);

INSERT INTO departments (name) VALUES("IT"), ("Human resources"), ("Accounting");
INSERT INTO employees (name, departmentId) VALUES("Alejandro", 1), ("Joaquina", 1), ("Marcos", 2), ("Rafael", 3), ("María", 3), ("Lola", 1);