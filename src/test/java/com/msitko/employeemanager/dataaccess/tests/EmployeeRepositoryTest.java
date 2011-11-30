/**
 * 
 */
package com.msitko.employeemanager.dataaccess.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Test;

import com.msitko.employeemanager.dataaccess.EmployeeRepository;
import com.msitko.employeemanager.exceptions.NoEmployeeException;
import com.msitko.employeemanager.models.Employee;

/**
 * @author Sitko
 * 
 */
public class EmployeeRepositoryTest {

	public static final String fileName = "employeeTest.txt";

	@After
	public void tearUp() {
		File file = new File(fileName);
		file.delete();
	}

	@Test
	public void testAddNewEmployee() throws NoEmployeeException {
		// given
		Employee newEmployee = new Employee();
		EmployeeRepository employeeRepo = new EmployeeRepository(fileName);
		employeeRepo.addEmployee(newEmployee);
		// when
		Employee result = employeeRepo.getEmployeeByID(1);
		// then
		assertEquals(1, employeeRepo.getEmplList().size());
		assertEquals(newEmployee, result);
	}

	@Test
	public void testDeleteEmployee() throws NoEmployeeException {
		// given
		Employee newEmployee = new Employee();
		EmployeeRepository employeeRepo = new EmployeeRepository(fileName);
		employeeRepo.addEmployee(newEmployee);
		employeeRepo.deleteEmployee(1);
		// when
		int result = employeeRepo.getEmplList().size();
		// then
		assertEquals(0, result);
	}

	@Test
	public void testGetFileName() {
		// given
		EmployeeRepository employeeRepo = new EmployeeRepository(fileName);
		// when
		String result = employeeRepo.getFileName();
		// then
		assertEquals(fileName, result);
	}

	@Test
	public void testGetEmployeeList() {
		// given
		EmployeeRepository employeeRepo = new EmployeeRepository(fileName);
		// when
		ArrayList<Employee> result = employeeRepo.getEmplList();
		// then
		assertNotNull(result);
	}	
}
