/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.models.tests;

import java.util.GregorianCalendar;

import com.msitko.employeemanager.exceptions.InvalidEmailException;
import com.msitko.employeemanager.exceptions.InvalidPeselException;
import com.msitko.employeemanager.exceptions.InvalidPhoneNumberException;
import com.msitko.employeemanager.exceptions.ParseGenderException;
import com.msitko.employeemanager.models.Employee;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Maricn Sitko
 * @version 1.0
 */
public class EmployeeTest {

	public EmployeeTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of getAge method, of class Employee if actual date is one day before
	 * Employee birthdays
	 */
	@Test
	public void testGetAgeOneDayBeforeBirthdays() {
		// given
		Employee employee = new Employee();
		GregorianCalendar birthCalendar = new GregorianCalendar(1990, 2, 14);
		employee.setBirthDate(birthCalendar.getTime());
		GregorianCalendar mockActualDate = new GregorianCalendar(2010, 2, 13);
		// when
		int age = employee.getAge(mockActualDate);
		// then
		assertEquals(19, age);
	}

	/**
	 * Test of getAge method, of class Employee if actual date is at Employee
	 * birthdays
	 */
	@Test
	public void testGetAgeAtBirthdays() {
		// given
		Employee employee = new Employee();
		GregorianCalendar birthCalendar = new GregorianCalendar(1990, 2, 14);
		employee.setBirthDate(birthCalendar.getTime());
		GregorianCalendar mockActualDate = new GregorianCalendar(2010, 2, 14);
		// when
		int age = employee.getAge(mockActualDate);
		// then
		assertEquals(20, age);
	}

	/**
	 * Test of setPesel method with valid pesel.
	 */
	@Test
	public void testSettingValidPesel() {
		// given
		Employee employeepl = new Employee();
		long pesel = 90021416955L;
		try {
			// when
			employeepl.setPesel(pesel);
			// then
		} catch (InvalidPeselException ex) {
			fail();
		}
	}

	/**
	 * Test of setPesel method with invalid pesel.
	 */
	@Test
	public void testSettingInvalidPesel() {
		// given
		Employee employee = new Employee();
		long pesel = 90021416956L;
		try {
			// when
			employee.setPesel(pesel);
			// then
			fail();
		} catch (InvalidPeselException ex) {
		}
	}

	/**
	 * Test of setPhone method with valid phone argument.
	 */
	@Test
	public void testSettingValidPhone() {
		// given
		Employee employee = new Employee();
		String phoneNumber = "+(48) 32 421 312";
		try {
			// when
			employee.setPhoneNumber(phoneNumber);
			// then
		} catch (InvalidPhoneNumberException ex) {
			fail();
		}
	}

	/**
	 * Test of setPhone method with invalid phone argument.
	 */
	@Test
	public void testSettingInvalidPhone() {
		// given
		Employee employee = new Employee();
		String phoneNumber = "+(48) 32a 421 312";
		try {
			// when
			employee.setPhoneNumber(phoneNumber);
			// then
			fail();
		} catch (InvalidPhoneNumberException ex) {
		}
	}

	@Test
	public void testGettingEmailAddress() throws InvalidEmailException {
		// given
		Employee employee = new Employee();
		String arg = "tested.email@te4st.testtest.pl";
		employee.setEmailAddress(arg);
		// when
		String gotEmail = employee.getEmailAddress();
		// then
		assertEquals(arg, gotEmail);

	}

	@Test
	public void testEmailHasTooLongEnddomain() {
		// given
		Employee employee = new Employee();
		String arg = "tested.email@te4st.test.pltoolongdomain";
		try {
			// when
			employee.setEmailAddress(arg);
			// then
			fail();
		} catch (InvalidEmailException ex) {
		}
	}

	@Test
	public void testEmailContainIllegalChars() {
		// given
		Employee employee = new Employee();
		String arg = "te\\\'sted.email@te4st.test.pl";
		try {
			// when
			employee.setEmailAddress(arg);
			// then
			fail();
		} catch (InvalidEmailException ex) {
		}
	}

	@Test
	public void testGetBirthDate() {
		// given
		Employee employee = new Employee();
		java.util.Date passedDate = (new GregorianCalendar(1990, 2, 14))
				.getTime();
		employee.setBirthDate(passedDate);
		// when
		java.util.Date returnedDate = employee.getBirthDate();
		// then
		assertEquals(passedDate, returnedDate);
	}

	@Test
	public void testGetEmail() throws InvalidEmailException {
		// given
		Employee employee = new Employee();
		String passedEmail = "testedemail@gmail.com";
		employee.setEmailAddress(passedEmail);
		// when
		String returnedEmail = employee.getEmailAddress();
		// then
		assertEquals(passedEmail, returnedEmail);
	}

	@Test
	public void testGetGender() throws ParseGenderException {
		// given
		Employee employee = new Employee();
		employee.setGender('k');
		// when
		Employee.Gender gend = employee.getGender();
		// then
		assertEquals(Employee.Gender.FEMALE, gend);
	}

	@Test
	public void testGetName() {
		// given
		Employee employee = new Employee();
		String passedName = "Marcin";
		employee.setName(passedName);
		// when;
		String returnedName = employee.getName();
		// then
		assertEquals(passedName, returnedName);
	}

	@Test
	public void testGetSurname() {
		// given
		Employee employee = new Employee();
		employee.setSurname("Kowalski");
		// when
		String resultSurname = employee.getSurname();
		// then
		assertEquals("Kowalski", resultSurname);
	}

	@Test
	public void testGetPesel() throws InvalidPeselException {
		// given
		Employee employee = new Employee();
		long passedPesel = 90021416955L;
		employee.setPesel(passedPesel);
		// when
		long returnedPesel = employee.getPesel();
		// then
		assertEquals(passedPesel, returnedPesel);
	}

	@Test
	public void testGetRatePerHour() {
		// given
		Employee employee = new Employee();
		employee.setRatePerHour(45.123456f);
		// when
		float resultPerHour = employee.getRatePerHour();
		// then
		assertEquals(45.123456f, resultPerHour, 0.001);
	}

	@Test
	public void testGetId() {
		// given
		Employee employee = new Employee();
		employee.setId(4);
		// when
		long resultId = employee.getId();
		// then
		assertEquals(4, resultId);
	}

}
