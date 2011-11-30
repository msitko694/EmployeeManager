/**
 * 
 */
package com.msitko.employeemanager.views.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.msitko.employeemanager.views.EmployeeGuiView;
import com.msitko.employeemanager.views.EmployeeStandardBuilder;

/**
 * @author Marcin Sitko
 * @version 1.0
 */
public class EmployeeStandardBuilderTest {

	@Test
	public void testGetCreatedWindow() {
		// given
		EmployeeStandardBuilder employeeBuilder = new EmployeeStandardBuilder();
		employeeBuilder.buildFrame();
		// when
		EmployeeGuiView result = employeeBuilder.getCreatedWindow();
		// then
		assertNotNull(result);
	}

}
