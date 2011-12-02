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
		Employee empl = new Employee();
		GregorianCalendar birthCalendar = new GregorianCalendar(1990, 2, 14);
		empl.setBirthDate(birthCalendar.getTime());
		GregorianCalendar mockActualDate = new GregorianCalendar(2010, 2, 13);
		// when
		int age = empl.getAge(mockActualDate);
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
		Employee empl = new Employee();
		GregorianCalendar birthCalendar = new GregorianCalendar(1990, 2, 14);
		empl.setBirthDate(birthCalendar.getTime());
		GregorianCalendar mockActualDate = new GregorianCalendar(2010, 2, 14);
		// when
		int age = empl.getAge(mockActualDate);
		// then
		assertEquals(20, age);
	}

	/**
	 * Test of setPesel method with valid pesel.
	 */
	@Test
	public void testSettingValidPesel() {
		// given
		Employee empl = new Employee();
		long pesel = 90021416955L;
		try {
			// when
			empl.setPesel(pesel);
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
		Employee empl = new Employee();
		long pesel = 90021416956L;
		try {
			// when
			empl.setPesel(pesel);
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
		Employee empl = new Employee();
		String phoneNumber = "+(48) 32 421 312";
		try {
			// when
			empl.setPhoneNumber(phoneNumber);
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
		Employee empl = new Employee();
		String phoneNumber = "+(48) 32a 421 312";
		try {
			// when
			empl.setPhoneNumber(phoneNumber);
			// then
			fail();
		} catch (InvalidPhoneNumberException ex) {
		}
	}

	@Test
	public void testGettingEmailAddress() throws InvalidEmailException {
		// given
		Employee empl = new Employee();
		String arg = "tested.email@te4st.testtest.pl";
		empl.setEmailAddress(arg);
		// when
		String gotEmail = empl.getEmailAddress();
		// then
		assertEquals(arg, gotEmail);

	}

	@Test
	public void testEmailHasTooLongEnddomain() {
		// given
		Employee empl = new Employee();
		String arg = "tested.email@te4st.test.pltoolongdomain";
		try {
			// when
			empl.setEmailAddress(arg);
			// then
			fail();
		} catch (InvalidEmailException ex) {
		}
	}

	@Test
	public void testEmailContainIllegalChars() {
		// given
		Employee empl = new Employee();
		String arg = "te\\\'sted.email@te4st.test.pl";
		try {
			// when
			empl.setEmailAddress(arg);
			// then
			fail();
		} catch (InvalidEmailException ex) {
		}
	}

	@Test
	public void testGetBirthDate() {
		// given
		Employee empl = new Employee();
		java.util.Date passedDate = (new GregorianCalendar(1990, 2, 14))
				.getTime();
		empl.setBirthDate(passedDate);
		// when
		java.util.Date returnedDate = empl.getBirthDate();
		// then
		assertEquals(passedDate, returnedDate);
	}

	@Test
	public void testGetEmail() throws InvalidEmailException {
		// given
		Employee empl = new Employee();
		String passedEmail = "testedemail@gmail.com";
		empl.setEmailAddress(passedEmail);
		// when
		String returnedEmail = empl.getEmailAddress();
		// then
		assertEquals(passedEmail, returnedEmail);
	}

	@Test
	public void testGetGender() throws ParseGenderException {
		// given
		Employee empl = new Employee();
		empl.setGender('k');
		// when
		Employee.Gender gend = empl.getGender();
		// then
		assertEquals(Employee.Gender.FEMALE, gend);
	}

	@Test
	public void testGetName() {
		// given
		Employee empl = new Employee();
		String passedName = "Marcin";
		empl.setName(passedName);
		// when;
		String returnedName = empl.getName();
		// then
		assertEquals(passedName, returnedName);
	}

	@Test
	public void testGetSurname() {
		// given
		Employee instance = new Employee();
		instance.setSurname("Kowalski");
		// when
		String resultSurname = instance.getSurname();
		// then
		assertEquals("Kowalski", resultSurname);
	}

	@Test
	public void testGetPesel() throws InvalidPeselException {
		// given
		Employee empl = new Employee();
		long passedPesel = 90021416955L;
		empl.setPesel(passedPesel);
		// when
		long returnedPesel = empl.getPesel();
		// then
		assertEquals(passedPesel, returnedPesel);
	}

	@Test
	public void testGetRatePerHour() {
		// given
		Employee instance = new Employee();
		instance.setRatePerHour(45.123456f);
		// when
		float resultPerHour = instance.getRatePerHour();
		// then
		assertEquals(45.123456f, resultPerHour, 0.001);
	}

	@Test
	public void testGetId() {
		// given
		Employee instance = new Employee();
		instance.setId(4);
		// when
		long resultId = instance.getId();
		// then
		assertEquals(4, resultId);
	}

}
