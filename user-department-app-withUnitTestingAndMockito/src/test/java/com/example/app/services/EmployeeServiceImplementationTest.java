package com.example.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.errors.EmployeeNotFoundException;
import com.example.app.models.Department;
import com.example.app.models.Employee;
import com.example.app.repositories.DepartmentRepositoryJPA;
import com.example.app.repositories.EmployeeRepositoryJPA;
import com.example.app.utils.MessageUtil;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplementationTest {
	
	@Mock
    private EmployeeRepositoryJPA employeeRepository;

    @Mock
    private DepartmentRepositoryJPA departmentRepository;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private EmployeeServiceImplementation employeeService;
	
    @Test
    void testGetEmployeeById_Success() {
    	int employeeId = 1;
    	Department mockDepartment = new Department("IT");
    	Employee mockEmployee = Employee.of(new EmployeeRequestDTO("Mock Employee"), mockDepartment);
    	EmployeeResponseDTO expectedResponse = EmployeeResponseDTO.of(mockEmployee);
    	
    	Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockEmployee));
    	EmployeeResponseDTO result = employeeService.getEmployeeById(employeeId);
    	
    	assertEquals(expectedResponse, result, "The employee response DTO should match the expected one");
    }
    
    @Test
    void testGetAllEmployees_Success() {
    	Department mockDepartment = new Department("IT");
    	Employee mockEmployee1 = Employee.of(new EmployeeRequestDTO("Mock Employee 1"), mockDepartment);
    	Employee mockEmployee2 = Employee.of(new EmployeeRequestDTO("Mock Employee 2"), mockDepartment);
		List<Employee> employeeList = (Arrays.asList(mockEmployee1,mockEmployee2));
    	List<EmployeeResponseDTO> expectedResponse = employeeList.stream().
    			map(employee -> EmployeeResponseDTO.of(employee)).
    			toList();
    	
    	Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
    	List<EmployeeResponseDTO> result = employeeService.getAllEmployees();
    	
    	assertEquals(expectedResponse, result, "The employee response DTO should match the expected one");
    	
    	Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }
    
    @Test
    void testGetEmployeeById_Fail_EmployeeNotFound() {
    	int employeeId = 99;
    	Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
    	
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(employeeId);
        }, "EmployeeNotFoundException should be thrown for non-existing employee");
    }
    
    @Test
    void testAddEmployee_Success() {
    	int departmentId = 1;
    	int employeeId = 1;
    	Department mockDepartment = new Department("IT");
    	EmployeeRequestDTO mockRequestEmployee = new EmployeeRequestDTO("Mock Employee", 1);
        Employee mockEmployee = Employee.of(mockRequestEmployee, mockDepartment);
        mockEmployee.setId(employeeId);
        
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockDepartment));
    	Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(mockEmployee);
    	
    	EmployeeResponseDTO response = employeeService.addEmployee(mockRequestEmployee);
    	EmployeeResponseDTO mockResponseEmployee = EmployeeResponseDTO.of(mockEmployee);
    	
    	assertNotNull(response, "The response should not be null");
    	assertEquals(response, mockResponseEmployee, "Employee data should match");
    	
    	Mockito.verify(employeeRepository, Mockito.times(1)).save(Mockito.any(Employee.class));
    }
    
    @Test
    void testAddEmployee_Fail_DepartmentNotFound(){
    	EmployeeRequestDTO mockRequestEmployee = new EmployeeRequestDTO("Mock Employee", 999);
    	
    	assertThrows(DepartmentNotFoundException.class, () -> {
    		employeeService.addEmployee(mockRequestEmployee);
    	}, "Department should exist previous to employee addition");
    	
    	Mockito.verify(employeeRepository, Mockito.times(0)).save(Mockito.any());
    }
    
    @Test
    void testEditEmployee_Success() {
    	int employeeId = 1;
    	int newDepartmentId = 2;
    	Department newMockDepartment = new Department("DevOps");
    	newMockDepartment.setId(newDepartmentId);
    	EmployeeRequestDTO newMockRequestEmployee = new EmployeeRequestDTO("Mock Employee Edit", 2);
        Employee newMockEmployee = Employee.of(newMockRequestEmployee, newMockDepartment);
        newMockEmployee.setId(employeeId);

        Mockito.when(departmentRepository.findById(newDepartmentId)).thenReturn(Optional.of(newMockDepartment));
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(newMockEmployee));
        Mockito.when(employeeRepository.save(newMockEmployee)).thenReturn(newMockEmployee);
        
        EmployeeResponseDTO response = employeeService.editEmployee(newMockRequestEmployee, employeeId);
        
        assertNotNull(response,"Response should not be null");
        assertEquals(response, EmployeeResponseDTO.of(newMockEmployee), "Employee variables should be the same");

        Mockito.verify(departmentRepository, Mockito.times(1)).findById(newDepartmentId);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(newMockEmployee);
    }
    
    @Test
    void testEditEmployee_Fail_EmployeeNotFound() {
    	int employeeId = 1;
    	EmployeeRequestDTO newMockRequestEmployee = new EmployeeRequestDTO("Mock Employee Edit", 999);
    	
    	assertThrows(EmployeeNotFoundException.class, ()-> {
    		employeeService.editEmployee(newMockRequestEmployee, employeeId);
    	}, "Employee should exist previous to employee edition");
    	
    	Mockito.verify(employeeRepository, Mockito.times(0)).save(Mockito.any());
    }
    
    @Test
    void testEditEmployee_Fail_DepartmentNotFound() {
    	int employeeId = 1;
    	EmployeeRequestDTO newMockRequestEmployee = new EmployeeRequestDTO("Mock Employee Edit", 999);
    	Department mockDepartment = new Department("IT");
    	Employee retrievedEmployee = Employee.of(newMockRequestEmployee, mockDepartment);
    	retrievedEmployee.setId(employeeId);
    	
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(retrievedEmployee));
    	
    	assertThrows(DepartmentNotFoundException.class, ()-> {
    		employeeService.editEmployee(newMockRequestEmployee, employeeId);
    	}, "Department should exist previous to employee edition");
    	
    	Mockito.verify(employeeRepository, Mockito.times(0)).save(Mockito.any());
    }
    
    @Test
    void testRemoveEmployee_Success() {
    	int employeeId = 1;
    	
    	Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);
    	Mockito.doNothing().when(employeeRepository).deleteById(employeeId);
    	employeeService.removeEmployee(employeeId);
    	
    	Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeId);
    }
    
    @Test
    void testRemoveEmployee_Fail_EmployeeNotFound() {
    	int employeeId = 1;
    	
    	Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(false);
    	assertThrows(EmployeeNotFoundException.class, () -> {
        	employeeService.removeEmployee(employeeId);
    	}, "Employee should exist previous to employee removal");
    	
    	Mockito.verify(employeeRepository, Mockito.times(0)).deleteById(Mockito.any());
    }
}
