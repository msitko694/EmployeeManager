package com.msitko.employeemanager.controllers;

import com.msitko.employeemanager.dataaccess.EmployeeRepository;
import com.msitko.employeemanager.views.EmployeeConsoleView;
import com.msitko.employeemanager.views.EmployeeGuiView;

/**
 * EmployeeController class handle and proceed events which gets from swing
 * employee views.
 * 
 * @author Marcin Sitko
 * @version 1.0
 * @see EmployeeConsoleView
 * 
 */
public class EmployeeSwingController {
	private EmployeeGuiView view;
	private EmployeeRepository repository;

	public EmployeeSwingController(EmployeeGuiView view, EmployeeRepository repository) {
		this.view = view;
		this.repository = repository;
	}

	/**
	 * @return the view
	 */
	public EmployeeGuiView getView() {
		return view;
	}

	/**
	 * @param view
	 *            the view to set
	 */
	public void setView(EmployeeGuiView view) {
		this.view = view;
	}

	/**
	 * @return the repository
	 */
	public EmployeeRepository getRepository() {
		return repository;
	}

	/**
	 * @param repository
	 *            the repository to set
	 */
	public void setRepository(EmployeeRepository repository) {
		this.repository = repository;
	}

}
