/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.models.tests;

import java.util.GregorianCalendar;

import com.msitko.employeemanager.exceptions.InvalidEmailException;
import com.msitko.employeemanager.exceptions.InvalidPeselException;
import com.msitko.employeemanager.exceptions.InvalidPhoneNumberException;
import com.msitko.employeemanager.models.Employee;

import java.util.Calendar;
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
		Employee instance = new Employee();
		long pesel = 90021416955L;
		try {
			// when
			instance.setPesel(pesel);
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
		Employee instance = new Employee();
		long pesel = 90021416956L;
		try {
			// when
			instance.setPesel(pesel);
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
		Employee instance = new Employee();
		String phoneNumber = "+(48) 32 421 312";
		try {
			// when
			instance.setPhoneNumber(phoneNumber);
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
		Employee instance = new Employee();
		String phoneNumber = "+(48) 32a 421 312";
		try {
			// when
			instance.setPhoneNumber(phoneNumber);
			// then
			fail();
		} catch (InvalidPhoneNumberException ex) {
		}
	}
	

	@Test
	public void testGettingEmailAddress() throws InvalidEmailException {
		// given
		Employee instance = new Employee();
		String arg = "tested.email@te4st.testtest.pl";
		instance.setEmailAddress(arg);
		// when
		String gotEmail = instance.getEmailAddress();
		// then
		assertEquals(arg, gotEmail);

	}

	@Test
	public void testEmailHasTooLongEnddomain() {
		// given
		Employee instance = new Employee();
		String arg = "tested.email@te4st.test.pltoolongdomain";
		try {
			// when
			instance.setEmailAddress(arg);
			// then
			fail();
		} catch (InvalidEmailException ex) {
		}
	}

	@Test
	public void testEmailContainIllegalChars() {
		// given
		Employee instance = new Employee();
		String arg = "te\\\'sted.email@te4st.test.pl";
		try {
			// when
			instance.setEmailAddress(arg);
			// then
			fail();
		} catch (InvalidEmailException ex) {
		}
	}
}
