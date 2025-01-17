package com.example.app.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.example.app.repositories.DepartmentRepositoryJPA;
import com.example.app.repositories.EmployeeRepositoryJPA;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplementationTest {
	
	@Mock
    private EmployeeRepositoryJPA employeeRepository;

    @Mock
    private DepartmentRepositoryJPA departmentRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private EmployeeServiceImplementation employeeService;
	
}
