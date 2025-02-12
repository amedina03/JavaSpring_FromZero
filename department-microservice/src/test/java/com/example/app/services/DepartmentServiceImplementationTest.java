package com.example.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.app.dtos.DepartmentResponseDTO;
import com.example.app.dtos.EmployeeResponseDTO;
import com.example.app.errors.DepartmentNotFoundException;
import com.example.app.errors.DuplicateDepartmentEntryException;
import com.example.app.models.Department;
import com.example.app.repositories.DepartmentRepositoryJPA;
import com.example.app.utils.MessageUtil;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplementationTest {
	
	@Mock
	DepartmentRepositoryJPA departmentRepository;
	
	@Mock
	MessageUtil messageUtil;
	
	@InjectMocks
	DepartmentServiceImplementation departmentService;
	
	@Test
	void testGetAllDepartments_Success() {
		Department mockDepartment1 = new Department("IT");
		Department mockDepartment2 = new Department("DevOps");
		List<Department> departmentList = Arrays.asList(mockDepartment1, mockDepartment2);
		List<DepartmentResponseDTO> expectedResponse = departmentList.stream().
				map(department -> DepartmentResponseDTO.of(department)).
				toList();
	
		Mockito.when(departmentRepository.findAll()).thenReturn(departmentList);
		List<DepartmentResponseDTO> response = departmentService.getAllDepartments();
		
		assertEquals(expectedResponse, response, "Expected result does not match actual result");
	}
	
//	@Test
//	void testGetAllDepartmentEmployees_Success() {
//		int departmentId = 1;
//		int expectedSize = 2;
//		Department mockDepartment = new Department("IT");
//		mockDepartment.setId(departmentId);
//		EmployeeRequestDTO mockEmployee1 = new EmployeeRequestDTO("Mock Employee 1");
//		EmployeeRequestDTO mockEmployee2 = new EmployeeRequestDTO("Mock Employee 2");
//		List<Employee> employeeList = Arrays.asList(Employee.of(mockEmployee1, mockDepartment), Employee.of(mockEmployee2, mockDepartment));
//		mockDepartment.setEmployeeList(employeeList);
//		List<EmployeeResponseDTO> expectedResponse = employeeList.stream().
//				map(employee -> EmployeeResponseDTO.of(employee)).
//				toList();
//		
//		Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockDepartment));
//		
//		List<EmployeeResponseDTO> response = departmentService.getAllDepartmentEmployees(departmentId);
//
//		assertEquals(response.size(), expectedSize, "Expected size does not match actual size");
//		assertEquals(expectedResponse, response, "Expected employee list does not match actual employee list");
//	}
	
//	@Test 
//	void testGetAllDepartmentEmployees_Fail_DepartmentNotFound(){
//		int departmentId = 999;
//		
//		Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
//		
//		assertThrows(DepartmentNotFoundException.class, () -> {
//			departmentService.getAllDepartmentEmployees(departmentId);
//		}, "Department not found exception should be thrown");
//	}
	
	@Test
	void testAddDepartment_Success() {
		String departmentName = "IT";
		Department mockDepartment = new Department(departmentName);
		DepartmentResponseDTO expectedResponse = DepartmentResponseDTO.of(mockDepartment);
		
		Mockito.when(departmentRepository.existsByName(departmentName)).thenReturn(false);
		Mockito.when(departmentRepository.save(mockDepartment)).thenReturn(mockDepartment);
		
		DepartmentResponseDTO response = departmentService.addDepartment(mockDepartment);
		
		assertEquals(expectedResponse, response, "Expected data in new department does not match actual data");
		
		Mockito.verify(departmentRepository).existsByName(departmentName);
	}
	
	@Test
	void testAddDepartment_Fail_DuplicateDepartmentEntry() {
		String departmentName = "IT";
		Department mockDepartment = new Department(departmentName);
		
		Mockito.when(departmentRepository.existsByName(departmentName)).thenReturn(true);
		
		assertThrows(DuplicateDepartmentEntryException.class, () -> {
			departmentService.addDepartment(mockDepartment);
		}, "Duplicate department entry exception should be thrown due to matching names");

		Mockito.verify(departmentRepository, Mockito.times(0)).save(Mockito.any());
	}
	
	@Test
	void testEditDepartment_Success() {
		int departmentId = 1;
		Department oldMockDepartment = new Department("IT");
		Department newMockDepartment = new Department("IT Edit");
		oldMockDepartment.setId(departmentId);
		newMockDepartment.setId(departmentId);
		DepartmentResponseDTO expectedResponse = DepartmentResponseDTO.of(newMockDepartment);

		Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(oldMockDepartment));
		Mockito.when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(newMockDepartment);
		
		DepartmentResponseDTO response = departmentService.editDepartment(newMockDepartment, departmentId);
		
		assertEquals(expectedResponse, response, "Expected data in new department does not match actual data");

		Mockito.verify(departmentRepository).findById(departmentId);
		Mockito.verify(departmentRepository, Mockito.times(1)).save(Mockito.any(Department.class));
	}
	
	@Test
	void testEditDepartment_Fail_DepartmentNotFound() {
		int departmentId = 1;
		Department mockDepartment = new Department("IT Edit");
		
		Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
		
		assertThrows(DepartmentNotFoundException.class, () -> {
			departmentService.editDepartment(mockDepartment, departmentId);
		}, "Department should exists previous to department edition");
		
		Mockito.verify(departmentRepository, Mockito.times(0)).save(Mockito.any());
	}
	
	@Test
	void testEditDepartment_Fail_DuplicateDepartmentEntry() {
		int departmentId = 1;
		String departmentName = "IT";
		Department mockDepartment = new Department(departmentName);
		
		Mockito.when(departmentRepository.existsByName(departmentName)).thenReturn(true);
		
		assertThrows(DuplicateDepartmentEntryException.class, () -> {
			departmentService.editDepartment(mockDepartment, departmentId);
		}, "Duplicate department entry exception should be thrown due to matching names");
		
		Mockito.verify(departmentRepository, Mockito.times(0)).save(Mockito.any());
	}
	
	@Test
	void testRemoveDepartment_Success() {
		int departmentId = 1;
		
		Mockito.when(departmentRepository.existsById(departmentId)).thenReturn(true);
		Mockito.doNothing().when(departmentRepository).deleteById(departmentId);
		
		departmentService.removeDepartment(departmentId);
		
		Mockito.verify(departmentRepository, Mockito.times(1)).deleteById(departmentId);
	}
	
	@Test
	void testRemoveDepartment_Fail_DepartmentNotFound() {
		int departmentId = 1;
		
		Mockito.when(departmentRepository.existsById(departmentId)).thenReturn(false);
		
		assertThrows(DepartmentNotFoundException.class, () -> {
			departmentService.removeDepartment(departmentId);
		}, "Department should exists previous to department removal");
	}
}
