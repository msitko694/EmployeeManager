package com.msitko.employeemanager.views;

/**
 * Interface defining methods needed by concrete EmployeeGuiBuilders
 * 
 * @author Marcin Sitko
 * @version 1.0
 * @see EmployeeStandardBuilder
 */
public interface IEmployeeGuiBuilder {
	void buildFrame();

	void buildMenu();

	void buildToolBar();

	void buildComponent();

	void buildContainer();

	void buildOptionPane();

	EmployeeGuiView getCreatedWindow();
}
