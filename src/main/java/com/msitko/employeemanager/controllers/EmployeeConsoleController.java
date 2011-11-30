/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.controllers;

import com.msitko.employeemanager.views.*;
import com.msitko.employeemanager.dataaccess.EmployeeRepository;
import java.awt.event.*;

/**
 * EmployeeController class handle and proceed events which gets from console
 * employee views.
 * 
 * @author Marcin Sitko
 * @version 1.0
 * @see EmployeeConsoleView
 * 
 */
public class EmployeeConsoleController {

	/**
	 * Enum type contains values of different menu options of EmployeeController
	 * class
	 */
	public static enum MenuOptions {
		DISPLAY_MENU(0), DISPLAY_EMPLOYEE(1), ADD_EMPLOYEE(2), EDIT_EMPLOYEE(3), DELETE_EMPLOYEE(
				4), EXIT_APP(5);

		int valueOf;

		MenuOptions(int valueOf) {
			this.valueOf = valueOf;
		}

		public int getValueOf() {
			return valueOf;
		}

		public void setValueOf(int valueOf) {
			this.valueOf = valueOf;
		}

	}

	/**
	 * reference to view managing by controller
	 */
	private EmployeeConsoleView employeeView;
	/**
	 * reference to model managing by controller
	 */
	private EmployeeRepository employeeModel;

	/**
	 * @param employeeView
	 *            - reference to assigned view
	 * @param employeeModel
	 *            - reference to assigned model
	 */
	public EmployeeConsoleController(EmployeeConsoleView employeeView,
			EmployeeRepository employeeModel) {
		this.employeeView = employeeView;
		this.employeeModel = employeeModel;

		this.employeeView.addListener(new NewEmployeeListener());
		this.employeeView.addListener(new ShowEmployeeListener());
		this.employeeView.addListener(new DisplayMenuListener());
		this.employeeView.addListener(new ExitAppListener());
		this.employeeView.addListener(new DeleteEmployeeListener());
		this.employeeView.addListener(new EditEmployeeListener());

		employeeView.printMenu();
	}

	/**
	 * Class is used to receive events which are deleting employee
	 */
	class EditEmployeeListener implements ActionListener {

		/**
		 * 
		 * @param e
		 *            - ActionEvent object
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == MenuOptions.EDIT_EMPLOYEE.valueOf) {
				employeeView.editEmployeeView(employeeModel);
			}
		}
	}

	/**
	 * Class is used to receive events which are deleting employee
	 */
	class DeleteEmployeeListener implements ActionListener {

		/**
		 * 
		 * @param e
		 *            - ActionEvent object
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == MenuOptions.DELETE_EMPLOYEE.valueOf) {
				employeeView.deleteEmployeeView(employeeModel);
			}
		}
	}

	/**
	 * Class is used to receive events which are displaying main menu
	 */
	class ExitAppListener implements ActionListener {

		/**
		 * 
		 * @param e
		 *            - ActionEvent object
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == MenuOptions.EXIT_APP.valueOf) {
				System.exit(0);
			}
		}
	}

	/**
	 * Class is used to handle events which are displaying main menu
	 */
	class DisplayMenuListener implements ActionListener {

		/**
		 * 
		 * @param e
		 *            - ActionEvent object
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == MenuOptions.DISPLAY_MENU.valueOf) {
				employeeView.printMenu();
			}
		}
	}

	/**
	 * Class is used to handle events which are showing info about employees
	 */
	class ShowEmployeeListener implements ActionListener {

		/**
		 * 
		 * @param e
		 *            - ActionEvent object
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == MenuOptions.DISPLAY_EMPLOYEE.valueOf) {
				employeeView.showEmployeesView(employeeModel);
			}
		}
	}

	/**
	 * Class is used to handle events which creates a new employee
	 */
	class NewEmployeeListener implements ActionListener {

		/**
		 * 
		 * @param e
		 *            - ActionEvent object
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == MenuOptions.ADD_EMPLOYEE.valueOf) {
				employeeView.addEmployeeView(employeeModel);
			}
		}
	}
}
