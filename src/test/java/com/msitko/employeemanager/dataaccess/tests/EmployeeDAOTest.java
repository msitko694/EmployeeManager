/**
 * 
 */
package com.msitko.employeemanager.dataaccess.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;

import com.msitko.employeemanager.dataaccess.EmployeeDAO;
import com.msitko.employeemanager.dataaccess.IEmployeeDAO;
import com.msitko.employeemanager.exceptions.InvalidEmailException;
import com.msitko.employeemanager.exceptions.InvalidPeselException;
import com.msitko.employeemanager.exceptions.InvalidPhoneNumberException;
import com.msitko.employeemanager.exceptions.NoEmployeeException;
import com.msitko.employeemanager.models.Employee;

/**
 * @author Sitko
 * 
 */
public class EmployeeDAOTest {

	@Test
	public void testCreateDefaultXml() {
		try {
			// given
			IEmployeeDAO dao = new EmployeeDAO();
			dao.createDefaultXml();
			File dbFile = new File("dbConnection.xml");
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document dbXml = docBuilder.parse(dbFile);
			// when
			String nodeName = dbXml.getDocumentElement().getNodeName();
			// then
			assertEquals("database", nodeName);
			dao.getConnection().close();
		} catch (Exception ex) {
			fail();
			ex.getStackTrace();
		}
	}

	@Test
	public void testCreateNewDb() {
		try {
			// given
			IEmployeeDAO dao = new EmployeeDAO();
			dao.createNewDb();
			dao.setConnection(DriverManager.getConnection(dao
					.getConnectionString()));
			Statement statement = dao.getConnection().createStatement();
			ResultSet resultSet = statement
					.executeQuery("Select tbl_name from sqlite_master Where tbl_name = 'Employees'");
			resultSet.next();
			// when
			String result = resultSet.getString(1);
			// then
			assertEquals("Employees", result);
			dao.getConnection().close();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetEmployeeById() {
		try {
			// given
			IEmployeeDAO dao = new EmployeeDAO();
			Employee newEmployee = new Employee();
			newEmployee.setName("testName");
			newEmployee.setBirthDate("14/02/1990");
			newEmployee.setEmailAddress("test@test.pl");
			newEmployee.setSurname("testSurname");
			newEmployee.setGender(Employee.Gender.MALE);
			newEmployee.setPesel(0);
			newEmployee.setRatePerHour(32.3f);
			newEmployee.setPhoneNumber("+48 (32) 32");
			dao.createEmployee(newEmployee);
			// when
			Employee foundedEmployee = dao
					.findEmployeeById(newEmployee.getId());
			// then
			assertEquals("testName", foundedEmployee.getName());
			dao.getConnection().close();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	public void testDeleteEmployee() throws InvalidEmailException,
			InvalidPeselException, InvalidEmailException,
			InvalidPhoneNumberException, ParseException, SQLException {
		// given
		IEmployeeDAO dao = new EmployeeDAO();
		Employee deletingEmployee = new Employee();
		deletingEmployee.setName("testName");
		deletingEmployee.setBirthDate("14/02/1990");
		deletingEmployee.setEmailAddress("test@test.pl");
		deletingEmployee.setSurname("testSurname");
		deletingEmployee.setGender(Employee.Gender.MALE);
		deletingEmployee.setPesel(0);
		deletingEmployee.setRatePerHour(32.3f);
		deletingEmployee.setPhoneNumber("+48 (32) 32");
		dao.createEmployee(deletingEmployee);
		dao.deleteEmployee(deletingEmployee.getId());
		try {
			// when
			dao.findEmployeeById(deletingEmployee.getId());
			// then
			fail();
		} catch (NoEmployeeException ex) {
			dao.getConnection().close();
		}
	}

	@Test
	public void testUpdateEmployee() throws ParseException,
			InvalidEmailException, InvalidPeselException,
			InvalidPhoneNumberException, SQLException, NoEmployeeException {
		// given
		IEmployeeDAO dao = new EmployeeDAO();
		Employee updatingEmployee = new Employee();
		updatingEmployee.setName("testName");
		updatingEmployee.setBirthDate("14/02/1990");
		updatingEmployee.setEmailAddress("test@test.pl");
		updatingEmployee.setSurname("testSurname");
		updatingEmployee.setGender(Employee.Gender.MALE);
		updatingEmployee.setPesel(0);
		updatingEmployee.setRatePerHour(32.3f);
		updatingEmployee.setPhoneNumber("+48 (32) 32");
		dao.createEmployee(updatingEmployee);
		updatingEmployee.setName("updatedName");
		dao.updateEmployee(updatingEmployee);
		// when
		Employee result = dao.findEmployeeById(updatingEmployee.getId());
		// then
		assertEquals("updatedName", result.getName());

		dao.getConnection().close();
	}

	@Test
	public void testFindAll() throws InvalidPhoneNumberException,
			InvalidPeselException, InvalidEmailException, ParseException,
			SQLException {
		// given
		IEmployeeDAO dao = new EmployeeDAO();
		Employee newEmployee = new Employee();
		newEmployee.setName("testName");
		newEmployee.setBirthDate("14/02/1990");
		newEmployee.setEmailAddress("test@test.pl");
		newEmployee.setSurname("testSurname");
		newEmployee.setGender(Employee.Gender.MALE);
		newEmployee.setPesel(0);
		newEmployee.setRatePerHour(32.3f);
		newEmployee.setPhoneNumber("+48 (32) 32");
		dao.createEmployee(newEmployee);
		dao.createEmployee(newEmployee);
		// when
		ArrayList<Employee> resultList = dao.findAll();
		// then
		assertTrue(resultList.size() >= 2);

		dao.getConnection().close();
	}
}
