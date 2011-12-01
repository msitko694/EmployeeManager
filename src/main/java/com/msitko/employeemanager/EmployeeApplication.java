package com.msitko.employeemanager;

import javax.swing.SwingUtilities;

import com.msitko.employeemanager.controllers.EmployeeSwingController;
import com.msitko.employeemanager.dataaccess.EmployeeDAO;
import com.msitko.employeemanager.dataaccess.IEmployeeDAO;
import com.msitko.employeemanager.views.EmployeeGuiView;
import com.msitko.employeemanager.views.EmployeeStandardBuilder;
import com.msitko.employeemanager.views.EmployeeViewCreator;

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
	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				EmployeeGuiView mainView = null;
				IEmployeeDAO dao = new EmployeeDAO();
				EmployeeSwingController mainContr = new EmployeeSwingController(
						mainView, dao);
				mainView = (new EmployeeViewCreator(
						new EmployeeStandardBuilder(mainContr)))
						.createEmployeeWindow();
				mainContr.setView(mainView);
				mainView.getMainFrame().setVisible(true);
			}
		});
	}
}
