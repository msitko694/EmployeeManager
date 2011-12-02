package com.msitko.employeemanager.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.msitko.employeemanager.controllers.EmployeeSwingController;
import com.msitko.employeemanager.exceptions.InvalidEmailException;
import com.msitko.employeemanager.exceptions.InvalidPeselException;
import com.msitko.employeemanager.exceptions.InvalidPhoneNumberException;

public class EmployeeTableModel extends AbstractTableModel {

	private ArrayList<Employee> listOfEmployees;
	private EmployeeSwingController controller;
	private TableRowSorter<TableModel> sorter;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8207397652842994017L;

	
	/**
	 * @return the sorter
	 */
	public TableRowSorter<TableModel> getSorter() {
		return sorter;
	}

	/**
	 * @param sorter the sorter to set
	 */
	public void setSorter(TableRowSorter<TableModel> sorter) {
		this.sorter = sorter;
	}

	/**
	 * @return the controller
	 */
	public EmployeeSwingController getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(EmployeeSwingController controller) {
		this.controller = controller;
	}

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
			if (listOfEmployees.get(rowIndex).getGender() == Employee.Gender.MALE) {
				return "Mężczyzna";
			} else {
				return "Kobieta";
			}
		} else if (columnIndex == Employee.Field.ID.getFieldId()) {
			return listOfEmployees.get(rowIndex).getId();
		} else if (columnIndex == Employee.Field.BIRTH_DATE.getFieldId()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
			return dateFormat.format(listOfEmployees.get(rowIndex)
					.getBirthDate());
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

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == Employee.Field.GENDER.getFieldId()) {
			String gender = (String) aValue;
			if (gender.toLowerCase().equals("mężczyzna")) {
				listOfEmployees.get(rowIndex).setGender(Employee.Gender.MALE);
			} else if (gender.toLowerCase().equals("kobieta")) {
				listOfEmployees.get(rowIndex).setGender(Employee.Gender.FEMALE);
			}
		} else if (columnIndex == Employee.Field.ID.getFieldId()) {
			listOfEmployees.get(rowIndex).setId((Integer) aValue);
		} else if (columnIndex == Employee.Field.BIRTH_DATE.getFieldId()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
			try {
				listOfEmployees.get(rowIndex).setBirthDate(
						dateFormat.parse((String) aValue));
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		} else if (columnIndex == Employee.Field.EMAIL.getFieldId()) {
			try {
				listOfEmployees.get(rowIndex).setEmailAddress((String) aValue);
			} catch (InvalidEmailException ex) {
				ex.printStackTrace();
			}
		} else if (columnIndex == Employee.Field.NAME.getFieldId()) {
			listOfEmployees.get(rowIndex).setName((String) aValue);
		} else if (columnIndex == Employee.Field.SURNAME.getFieldId()) {
			listOfEmployees.get(rowIndex).setSurname((String) aValue);
		} else if (columnIndex == Employee.Field.PESEL.getFieldId()) {
			try {
				listOfEmployees.get(rowIndex).setPesel((long) aValue);
			} catch (InvalidPeselException e) {
			}
		} else if (columnIndex == Employee.Field.PHONE_NUMBER.getFieldId()) {
			try {
				listOfEmployees.get(rowIndex).setPhoneNumber((String) aValue);
			} catch (InvalidPhoneNumberException e) {
			}
		} else if (columnIndex == Employee.Field.RATE_PER_HOUR.getFieldId()) {
			listOfEmployees.get(rowIndex).setRatePerHour((Float) aValue);
		}
		
		Employee employeeToUpdate = listOfEmployees.get(rowIndex);
		controller.updateEmployee(employeeToUpdate);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == Employee.Field.ID.getFieldId()) {
			return false;
		}
		return true;
	}
}
