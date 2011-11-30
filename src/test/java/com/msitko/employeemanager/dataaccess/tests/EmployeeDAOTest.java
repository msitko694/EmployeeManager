/**
 * 
 */
package com.msitko.employeemanager.dataaccess.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;

import com.msitko.employeemanager.dataaccess.EmployeeDAO;
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
			EmployeeDAO dao = new EmployeeDAO();
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
			EmployeeDAO dao = new EmployeeDAO();
			dao.createNewDb();
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
			EmployeeDAO dao = new EmployeeDAO();
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
}
