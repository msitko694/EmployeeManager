package com.msitko.employeemanager.views;

/**
 * Class is used to direct a creating of employee main window object.
 * 
 * @author Marcin Sitko
 * @version 1.0
 */
public class EmployeeViewCreator {
	/**
	 * Reference to object which implements <code> IEmployeeGuiBuilder</code>
	 */
	IEmployeeGuiBuilder builder;

	/**
	 * 
	 * @param builder
	 *            reference to concrete builder which will be used in creating
	 *            process
	 */
	public EmployeeViewCreator(IEmployeeGuiBuilder builder) {
		this.builder = builder;
	}

	/**
	 * 
	 * @return reference to JFrame object which is an employee main window class
	 */
	public EmployeeGuiView createEmployeeWindow() {
		builder.buildFrame();
		builder.buildMenu();
		builder.buildToolBar();
		builder.buildComponent();
		builder.buildContainer();
		builder.buildOptionPane();
		return builder.getCreatedWindow();
	}
}
