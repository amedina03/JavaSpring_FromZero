package com.example.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.app.dtos.EmployeeRequestDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.errors.EmployeeNotFoundException;
import com.example.app.models.Employee;
import com.example.app.repositories.EmployeeRepositoryJPA;
import com.example.app.utils.MessageUtil;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplementationTest {
	
	private static final String CORRECT_DEPARTMENT_NAME = "Mock Department";
	private static final int CORRECT_DEPARTMENT_ID = 1;
	private static final int INCORRECT_DEPARTMENT_ID = 99;

	private static final String CORRECT_EMPLOYEE_NAME = "Mock Employee";
	private static final String CORRECT_EMPLOYEE_NAME_EDIT = "Mock Employee Edit";
	private static final int CORRECT_EMPLOYEE_ID = 1;
	private static final int INCORRECT_EMPLOYEE_ID = 99;
	
    @Mock
    private EmployeeRepositoryJPA employeeRepository;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private WebClient webClient;
    
    @Mock
    private WebClient.Builder webClientBuilder;

    private EmployeeServiceImplementation employeeService;
    
    @BeforeEach
    public void setUp() {
        webClient = Mockito.mock(WebClient.class, Answers.RETURNS_DEEP_STUBS);
        
        when(webClientBuilder.baseUrl(Mockito.anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        String departmentBaseUrl = "http://dummy-department-service";
        String departmentUri = "/department";
        String departmentNameUri = "/department/name";

        employeeService = Mockito.spy(new EmployeeServiceImplementation(
                employeeRepository,
                messageUtil,
                webClientBuilder,
                departmentBaseUrl,
                departmentUri,
                departmentNameUri
        ));
    }
    
    @Test
    void testGetEmployeeById_Success() {
        Employee mockEmployee = Employee.of(new EmployeeRequestDTO(CORRECT_EMPLOYEE_NAME, CORRECT_DEPARTMENT_ID));
        EmployeeResponseDTO expectedResponse = EmployeeResponseDTO.of(mockEmployee, CORRECT_DEPARTMENT_NAME);

        doReturn(CORRECT_DEPARTMENT_NAME).when(employeeService).getDepartmentName(CORRECT_DEPARTMENT_ID);
        when(employeeRepository.findById(CORRECT_EMPLOYEE_ID)).thenReturn(Optional.of(mockEmployee));

        EmployeeResponseDTO result = employeeService.getEmployeeById(CORRECT_EMPLOYEE_ID);
        assertEquals(expectedResponse, result, "The employee response DTO should match the expected one");

    	Mockito.verify(employeeService, Mockito.times(1)).getDepartmentName(CORRECT_DEPARTMENT_ID);
    }
    
    @Test
    void testGetAllEmployees_Success() {
    	Employee mockEmployee1 = Employee.of(new EmployeeRequestDTO(CORRECT_EMPLOYEE_NAME, CORRECT_DEPARTMENT_ID));
    	Employee mockEmployee2 = Employee.of(new EmployeeRequestDTO(CORRECT_EMPLOYEE_NAME + " 2", CORRECT_DEPARTMENT_ID));
		List<Employee> employeeList = (Arrays.asList(mockEmployee1,mockEmployee2));
    	List<EmployeeResponseDTO> expectedResponse = employeeList.stream().
    			map(employee -> EmployeeResponseDTO.of(employee, CORRECT_DEPARTMENT_NAME)).
    			toList();

        doReturn(CORRECT_DEPARTMENT_NAME).when(employeeService).getDepartmentName(CORRECT_DEPARTMENT_ID);
    	
    	when(employeeRepository.findAll()).thenReturn(employeeList);
    	List<EmployeeResponseDTO> result = employeeService.getAllEmployees();
    	
    	assertEquals(expectedResponse, result, "The employee response DTO should match the expected one");

    	Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    	Mockito.verify(employeeService, Mockito.atLeast(1)).getDepartmentName(CORRECT_DEPARTMENT_ID);
    }
    
    @Test
    void testGetEmployeeById_Fail_EmployeeNotFound() {
    	Mockito.when(employeeRepository.findById(INCORRECT_EMPLOYEE_ID)).thenReturn(Optional.empty());
    	
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(INCORRECT_EMPLOYEE_ID);
        }, "EmployeeNotFoundException should be thrown for non-existing employee");
    }
    
    @Test
    void testAddEmployee_Success() {
    	EmployeeRequestDTO mockRequestEmployee = new EmployeeRequestDTO(CORRECT_EMPLOYEE_NAME, CORRECT_DEPARTMENT_ID);
        Employee mockEmployee = Employee.of(mockRequestEmployee);
        mockEmployee.setId(CORRECT_EMPLOYEE_ID);
        
        doReturn(CORRECT_DEPARTMENT_NAME).when(employeeService).getDepartmentName(CORRECT_DEPARTMENT_ID);
    	Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(mockEmployee);
    	
    	EmployeeResponseDTO response = employeeService.addEmployee(mockRequestEmployee);
    	EmployeeResponseDTO mockResponseEmployee = EmployeeResponseDTO.of(mockEmployee,CORRECT_DEPARTMENT_NAME);
    	
    	assertNotNull(response, "The response should not be null");
    	assertEquals(response, mockResponseEmployee, "Employee data should match");
    	
    	Mockito.verify(employeeRepository, Mockito.times(1)).save(Mockito.any(Employee.class));
    }
    
    @Test
    void testAddEmployee_Fail_DepartmentNotFound(){
    	EmployeeRequestDTO mockRequestEmployee = new EmployeeRequestDTO(CORRECT_EMPLOYEE_NAME, INCORRECT_DEPARTMENT_ID);
    	
    	doThrow(DepartmentNotFoundException.class).when(employeeService).checkIfDepartmentExists(INCORRECT_DEPARTMENT_ID);
    	
    	assertThrows(DepartmentNotFoundException.class, () -> {
    		employeeService.addEmployee(mockRequestEmployee);
    	}, "Department should exist previous to employee addition");
    	
    	Mockito.verify(employeeRepository, Mockito.times(0)).save(Mockito.any());
    	Mockito.verify(employeeService, Mockito.atLeast(1)).checkIfDepartmentExists(INCORRECT_DEPARTMENT_ID);
    }
    
    @Test
    void testEditEmployee_Success() {
    	EmployeeRequestDTO newMockRequestEmployee = new EmployeeRequestDTO(CORRECT_EMPLOYEE_NAME_EDIT, CORRECT_DEPARTMENT_ID);
        Employee newMockEmployee = Employee.of(newMockRequestEmployee);
        newMockEmployee.setId(CORRECT_EMPLOYEE_ID);

        doReturn(CORRECT_DEPARTMENT_NAME).when(employeeService).getDepartmentName(CORRECT_DEPARTMENT_ID);
        
        Mockito.when(employeeRepository.findById(CORRECT_EMPLOYEE_ID)).thenReturn(Optional.of(newMockEmployee));
        Mockito.when(employeeRepository.save(newMockEmployee)).thenReturn(newMockEmployee);
        
        EmployeeResponseDTO response = employeeService.editEmployee(newMockRequestEmployee, CORRECT_EMPLOYEE_ID);
        
        assertNotNull(response,"Response should not be null");
        assertEquals(response, EmployeeResponseDTO.of(newMockEmployee, CORRECT_DEPARTMENT_NAME), "Employee variables should be the same");

        Mockito.verify(employeeRepository, Mockito.times(1)).save(newMockEmployee);
        Mockito.verify(employeeService, Mockito.times(1)).getDepartmentName(CORRECT_DEPARTMENT_ID);
    }
    
//    @Test
//    void testEditEmployee_Fail_EmployeeNotFound() {
//    	int employeeId = 1;
//    	EmployeeRequestDTO newMockRequestEmployee = new EmployeeRequestDTO("Mock Employee Edit", 999);
//    	
//    	assertThrows(EmployeeNotFoundException.class, ()-> {
//    		employeeService.editEmployee(newMockRequestEmployee, employeeId);
//    	}, "Employee should exist previous to employee edition");
//    	
//    	Mockito.verify(employeeRepository, Mockito.times(0)).save(Mockito.any());
//    }
    
//    @Test
//    void testEditEmployee_Fail_DepartmentNotFound() {
//    	int employeeId = 1;
//    	EmployeeRequestDTO newMockRequestEmployee = new EmployeeRequestDTO("Mock Employee Edit", 999);
//    	Department mockDepartment = new Department("IT");
//    	Employee retrievedEmployee = Employee.of(newMockRequestEmployee, mockDepartment);
//    	retrievedEmployee.setId(employeeId);
//    	
//        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(retrievedEmployee));
//    	
//    	assertThrows(DepartmentNotFoundException.class, ()-> {
//    		employeeService.editEmployee(newMockRequestEmployee, employeeId);
//    	}, "Department should exist previous to employee edition");
//    	
//    	Mockito.verify(employeeRepository, Mockito.times(0)).save(Mockito.any());
//    }
    
//    @Test
//    void testRemoveEmployee_Success() {
//    	int employeeId = 1;
//    	
//    	Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(true);
//    	Mockito.doNothing().when(employeeRepository).deleteById(employeeId);
//    	employeeService.removeEmployee(employeeId);
//    	
//    	Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeId);
//    }
    
//    @Test
//    void testRemoveEmployee_Fail_EmployeeNotFound() {
//    	int employeeId = 1;
//    	
//    	Mockito.when(employeeRepository.existsById(employeeId)).thenReturn(false);
//    	assertThrows(EmployeeNotFoundException.class, () -> {
//        	employeeService.removeEmployee(employeeId);
//    	}, "Employee should exist previous to employee removal");
//    	
//    	Mockito.verify(employeeRepository, Mockito.times(0)).deleteById(Mockito.any());
//    }
}
