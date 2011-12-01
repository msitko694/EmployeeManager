package com.msitko.employeemanager.views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.msitko.employeemanager.controllers.EmployeeSwingController;

/***
 * Class is used about presenting information in Swing GUI
 * 
 * @author Marcin Sitko
 * @version 1.0
 * @see EmployeeConsoleView
 */
public class EmployeeGuiView {

	private JFrame mainFrame;
	private JTable allEmployeesTable;
	private JToolBar toolBar;
	private JTabbedPane tabbedPane;
	private EmployeeSwingController controller;
	private JButton butNew;
	private JButton butDelete;

	public EmployeeGuiView() {
	}

	
	/**
	 * @return the butNew
	 */
	public JButton getButNew() {
		return butNew;
	}


	/**
	 * @param butNew the butNew to set
	 */
	public void setButNew(JButton butNew) {
		this.butNew = butNew;
	}


	/**
	 * @return the butDelete
	 */
	public JButton getButDelete() {
		return butDelete;
	}


	/**
	 * @param butDelete the butDelete to set
	 */
	public void setButDelete(JButton butDelete) {
		this.butDelete = butDelete;
	}


	/**
	 * @return the controller
	 */
	public EmployeeSwingController getController() {
		return controller;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(EmployeeSwingController controller) {
		this.controller = controller;
	}

	/**
	 * @return the mainFrame
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param mainFrame
	 *            the mainFrame to set
	 */
	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * @return the allEmployeesTable
	 */
	public JTable getAllEmployeesTable() {
		return allEmployeesTable;
	}

	/**
	 * @param allEmployeesTable
	 *            the allEmployeesTable to set
	 */
	public void setAllEmployeesTable(JTable allEmployeesTable) {
		this.allEmployeesTable = allEmployeesTable;
	}

	/**
	 * @return the toolBar
	 */
	public JToolBar getToolBar() {
		return toolBar;
	}

	/**
	 * @param toolBar
	 *            the toolBar to set
	 */
	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * @param tabbedPane
	 *            the tabbedPane to set
	 */
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
}
