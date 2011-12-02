package com.msitko.employeemanager.controllers;

import java.sql.SQLException;

import javax.swing.RowFilter;

import com.msitko.employeemanager.dataaccess.IEmployeeDAO;
import com.msitko.employeemanager.models.Employee;
import com.msitko.employeemanager.models.EmployeeTableModel;
import com.msitko.employeemanager.views.EmployeeConsoleView;
import com.msitko.employeemanager.views.EmployeeGuiView;
import com.msitko.employeemanager.views.NewEmployeeView;

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

	/**
	 * Creates and add new tab with newEmployeeView
	 * 
	 * @see NewEmployeeView
	 */
	public void newEmployeeTab() {
		NewEmployeeView newEmployeeView = new NewEmployeeView(this);
		view.getTabbedPane().addTab("Nowy pracownik",
				newEmployeeView.getMainPanel());
	}

	/**
	 * Methods changes visibility of create and delete employee buttons
	 */
	public void visibleCreateDeleteButtons(boolean isVisible) {
		if (view != null) {
			view.getButDelete().setVisible(isVisible);
			view.getButNew().setVisible(isVisible);
		}
	}

	/**
	 * Saves employee object into database
	 * 
	 * @param savingEmployee
	 */
	public void saveEmployee(Employee savingEmployee) {
		dao.createEmployee(savingEmployee);
		tableModel.getListOfEmployees().add(savingEmployee);
	}

	/**
	 * Delete from database employee with passed id
	 * 
	 * @param object
	 * @param selectedRows
	 *            array of numbers of selected rows
	 */
	public void deleteEmployee(int[] selectedRows) {
		for (int it : selectedRows) {
			long id = (long) view.getAllEmployeesTable().getModel()
					.getValueAt(it, Employee.Field.ID.getFieldId());
			dao.deleteEmployee(id);
		}
		try{
		tableModel
				.getListOfEmployees()
				.subList(selectedRows[0],
						selectedRows[selectedRows.length - 1] + 1).clear();
		tableModel.fireTableRowsDeleted(selectedRows[0],
				selectedRows[selectedRows.length - 1]);
		}catch (Exception ex) {
			System.out.println(selectedRows.length);
		}
	}

	/**
	 * Method updates employee in database
	 * 
	 * @param employeeToUpdate
	 *            employee to update
	 */
	public void updateEmployee(Employee employeeToUpdate) {
		try {
			dao.updateEmployee(employeeToUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method applies filter to table
	 * 
	 * @param phrase
	 *            which we are looking for
	 */
	public void applyFilter(String phrase) {
		if (phrase.length() > 0) {
			tableModel.getSorter().setRowFilter(
					RowFilter.regexFilter(".*" + phrase + ".*"));
		} else {
			tableModel.getSorter().setRowFilter(null);
		}
	}
}
