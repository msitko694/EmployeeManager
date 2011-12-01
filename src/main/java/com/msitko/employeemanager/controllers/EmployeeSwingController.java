package com.msitko.employeemanager.controllers;

import java.sql.SQLException;

import javax.swing.JPanel;

import com.msitko.employeemanager.dataaccess.IEmployeeDAO;
import com.msitko.employeemanager.models.EmployeeTableModel;
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
	private IEmployeeDAO dao;
	private EmployeeTableModel tableModel;

	public EmployeeSwingController(EmployeeGuiView view, IEmployeeDAO dao) {
		this.view = view;
		this.dao = dao;
	}

	public void loadAllEmployees() {
		try {
			tableModel.setListOfEmployees(dao.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the tableModel
	 */
	public EmployeeTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            the tableModel to set
	 */
	public void setTableModel(EmployeeTableModel tableModel) {
		this.tableModel = tableModel;
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
	 * @return the dao object
	 */
	public IEmployeeDAO getDao() {
		return this.dao;
	}

	/**
	 * @param repository
	 *            the dao object to set
	 */
	public void setDao(IEmployeeDAO dao) {
		this.dao = dao;
	}

	/**
	 * Method closes application
	 */
	public void closeApplication() {
		view.getMainFrame().dispose();
	}
	
	public void newEmployeeTab(){
		JPanel newEmployeePanel = new JPanel();
		view.getTabbedPane().addTab("Nowy pracownik", newEmployeePanel);
	}
}
