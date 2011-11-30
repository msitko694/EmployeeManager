package com.msitko.employeemanager.models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel extends AbstractTableModel {

	private ArrayList<Employee> listOfEmployees;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8207397652842994017L;

	/**
	 * @return the listOfEmployees
	 */
	public ArrayList<Employee> getListOfEmployees() {
		return listOfEmployees;
	}

	/**
	 * @param listOfEmployees
	 *            the listOfEmployees to set
	 */
	public void setListOfEmployees(ArrayList<Employee> listOfEmployees) {
		this.listOfEmployees = listOfEmployees;
	}

	@Override
	public int getRowCount() {
		return listOfEmployees.size();
	}

	@Override
	public int getColumnCount() {
		return Employee.fieldsCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == Employee.Field.GENDER.getFieldId()) {
			return listOfEmployees.get(rowIndex).getGender();
		} else if (columnIndex == Employee.Field.ID.getFieldId()) {
			return listOfEmployees.get(rowIndex).getId();
		} else if (columnIndex == Employee.Field.BIRTH_DATE.getFieldId()) {
			return listOfEmployees.get(rowIndex).getBirthDate();
		} else if (columnIndex == Employee.Field.EMAIL.getFieldId()) {
			return listOfEmployees.get(rowIndex).getEmailAddress();
		} else if (columnIndex == Employee.Field.NAME.getFieldId()) {
			return listOfEmployees.get(rowIndex).getName();
		} else if (columnIndex == Employee.Field.SURNAME.getFieldId()) {
			return listOfEmployees.get(rowIndex).getSurname();
		} else if (columnIndex == Employee.Field.PESEL.getFieldId()) {
			return listOfEmployees.get(rowIndex).getPesel();
		} else if (columnIndex == Employee.Field.PHONE_NUMBER.getFieldId()) {
			return listOfEmployees.get(rowIndex).getPhoneNumber();
		} else if (columnIndex == Employee.Field.RATE_PER_HOUR.getFieldId()) {
			return listOfEmployees.get(rowIndex).getRatePerHour();
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}

}
