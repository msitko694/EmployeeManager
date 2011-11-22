package com.msitko.employeemanager;

import com.msitko.employeemanager.controllers.EmployeeController;
import com.msitko.employeemanager.models.EmployeeManager;
import com.msitko.employeemanager.views.EmployeeView;

/**
 * Main class of Employee Manager application, contain only a main loop.
 * 
 * @author Marcin Sitko
 * @version 1.0
 * 
 */
public class EmployeeApplication {

	/**
	 * @param args
	 *            the command line arguments
	 * 
	 * 
	 */
	public static void main(String[] args) {
		String fileName = null;
		try {
			fileName = args[0];
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Wyjątek , indeks poza tablicą : "
					+ ex.getLocalizedMessage() + "\n" + ex.getMessage());
		}
		EmployeeView mainView = new EmployeeView();
		EmployeeManager mainModel = new EmployeeManager(fileName);
		@SuppressWarnings("unused")
		EmployeeController mainContr = new EmployeeController(mainView,
				mainModel);
	}
}
