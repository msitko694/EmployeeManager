/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.dataaccess;

import com.msitko.employeemanager.exceptions.NoEmployeeException;
import com.msitko.employeemanager.models.Employee;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class contain list of Employees also provide methods for making modifications
 * i.e saving list to file, getting concrete Employee by id.
 * 
 * @author Marcin Sitko
 * @version 1.0
 * @see Employee
 */
public class EmployeeRepository {

	/**
	 * Contains name of file on which will work model. Default it is
	 * "employees.txt"
	 */
	String fileName;
	/**
	 * Contain all employees' data
	 */
	ArrayList<Employee> emplList;

	/**
	 * 
	 * @return Return reference to array list of employees
	 */
	public ArrayList<Employee> getEmplList() {
		return emplList;
	}

	/**
	 * 
	 * @return Name of file assigned to this model
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 * @param fileName
	 *            Name of file assigned to this model
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 
	 * @param emplList
	 *            reference to list of employees
	 */
	public void setEmplList(ArrayList<Employee> emplList) {
		this.emplList = emplList;
	}

	/**
	 * 
	 * @param id
	 *            Id of searching employee
	 * @return Reference to Employee object which has passed id
	 * @throws NoEmployeeException
	 *             Throwed if employee with passed id didn't exist
	 */
	public Employee getEmployeeByID(int id) throws NoEmployeeException {

		for (Employee it : emplList) {
			if (it.getId() == id) {
				return it;
			}
		}
		throw new NoEmployeeException(String.format(
				"Pracownik z numerem ID: %d nie istnieje", id));
	}

	/**
	 * 
	 * @param id
	 *            Id of employee data which we want to delete
	 * @return If employee with passed id was found return true, else return
	 *         false
	 */
	public boolean deleteEmployee(int id) {
		try {
			Employee toDel = getEmployeeByID(id);
			emplList.remove(toDel);
			this.saveToFile();
			return true;
		} catch (NoEmployeeException ex) {
			System.out.println(ex.getMessage());
			return false;
		}

	}

	/**
	 * Constructor which populate list of employees by data readed from file.
	 * 
	 * @param fileName
	 *            Name of file with saved employees objects
	 */
	public EmployeeRepository(String fileName) {
		if (fileName != null)
			this.fileName = fileName;
		else {
			this.fileName = "employees.txt";
		}
		emplList = new ArrayList<Employee>();
		loadFromFile();
	}

	public void addEmployee(Employee employee) {
		long maxId = 0;
		for (int i = 0; i < emplList.size(); i++) {
			if (maxId < emplList.get(i).getId()) {
				maxId = emplList.get(i).getId();
			}
		}
		maxId++;
		employee.setId(maxId);
		emplList.add(employee);
		saveToFile();

	}

	/**
	 * Saving employee list to txt file
	 * 
	 */
	public void saveToFile() {
		try {
			FileWriter fw = new FileWriter(this.fileName);
			BufferedWriter out = new BufferedWriter(fw);
			for (Employee it : emplList) {
				out.write(String.valueOf(it.getId()) + ";");
				out.write(it.getName() + ";");
				out.write(it.getSurname() + ";");
				DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
				out.write(format.format(it.getBirthDate()) + ";");
				out.write(String.valueOf(it.getRatePerHour()) + ";");
				out.write(String.valueOf(it.getPesel()) + ";");
				out.write(String.valueOf(it.getGender()) + ";");
				out.write(it.getPhoneNumber() + ";");
				out.write(it.getEmailAddress());

				out.write("\r\n");
			}
			out.close();
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	/**
	 * Method which populate list of employees by data readed from file.
	 * 
	 */
	public final void loadFromFile() {

		FileReader fr = null;
		try {
			fr = new FileReader(this.fileName);
		} catch (FileNotFoundException e) {
			return;
		}
		BufferedReader in = new BufferedReader(fr);
		String empl = null;
		try {
			empl = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (empl != null) {
			String[] emplAttributes = empl.split(";");
			Employee newEmpl = new Employee();

			newEmpl.setId(Integer.parseInt(emplAttributes[0]));
			newEmpl.setName(emplAttributes[1]);
			newEmpl.setSurname(emplAttributes[2]);

			DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			Date birthDate = null;
			try {
				birthDate = format.parse(emplAttributes[3]);
			} catch (Exception ex) {
				Logger.getLogger(EmployeeRepository.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			newEmpl.setBirthDate(birthDate);

			newEmpl.setRatePerHour(Float.parseFloat(emplAttributes[4]));

			try {
				newEmpl.setPesel(Long.parseLong(emplAttributes[5]));
			} catch (Exception ex) {
				System.out.println(ex.getMessage()
						+ " Wyjątek wyrzucony przy odczycie z pliku");
			}

			newEmpl.setGender(Employee.Gender.valueOf(emplAttributes[6]));

			try {
				newEmpl.setPhoneNumber(emplAttributes[7]);
			} catch (Exception ex) {
				System.out.println(ex.getMessage()
						+ " Wyjątek wyrzucony przy odczycie z pliku");
			}

			try {
				newEmpl.setEmailAddress(emplAttributes[8]);
			} catch (Exception ex) {
				System.out.println(ex.getMessage()
						+ " Wyjątek wyrzucony przy odczycie z pliku");
			}

			emplList.add(newEmpl);

			try {
				empl = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
