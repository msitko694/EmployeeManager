/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.models.tests;

import java.util.Date;

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
	 * Test of getAge method, of class Employee
	 */
	@Test
	public void testGetAge() {
		System.out.println("getAge");
		Employee empl = new Employee();
		Calendar birthDate = Calendar.getInstance();
		birthDate.set(Calendar.YEAR, 1990);
		Date test = birthDate.getTime();
		empl.setBirthDate(test);
		int age1 = empl.getAge();
		birthDate.set(Calendar.DAY_OF_MONTH,
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1);
		test = birthDate.getTime();
		empl.setBirthDate(test);
		int age2 = empl.getAge();
		assertEquals(age1, age2 + 1);

	}

	/**
	 * Test of validatePesel method, of class Employee.
	 */
	@Test
	public void testValidatePesel() throws Exception {
		System.out.println("validatePesel");
		long pesel = 90021416955L;
		InvalidPeselException testedException = null;
		Employee instance = new Employee();
		try {
			instance.validatePesel(pesel);
		} catch (InvalidPeselException ex) {
			testedException = ex;
		}

		assertNull(testedException);

		pesel = 12345678955L;

		try {
			instance.validatePesel(pesel);
		} catch (InvalidPeselException ex) {
			testedException = ex;
		}

		assertNotNull(testedException);

	}

	@Test
	public void testValidatePhoneNumber() {
		System.out.println("validatePhoneNumber");
		Employee instance = new Employee();
		String phoneNumber1 = "+(32) 44444";

		try {
			instance.validatePhoneNumber(phoneNumber1);
		} catch (InvalidPhoneNumberException e) {

			fail("Shouldn't throw exception");
		}

		String phoneNumber2 = "+(32a) 44444";

		try {
			instance.validatePhoneNumber(phoneNumber2);
			fail("Should throw exception");
		} catch (InvalidPhoneNumberException e) {

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
	public void testEmailHasTooLongEnddomain() throws InvalidEmailException {
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
	public void testEmailContainIllegalChars() throws InvalidEmailException {
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
