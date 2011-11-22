/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.views;

import com.msitko.employeemanager.controllers.EmployeeController;
import com.msitko.employeemanager.dataaccess.EmployeeRepository;
import com.msitko.employeemanager.events.*;
import com.msitko.employeemanager.exceptions.InvalidEmailException;
import com.msitko.employeemanager.exceptions.InvalidPeselException;
import com.msitko.employeemanager.exceptions.InvalidPhoneNumberException;
import com.msitko.employeemanager.exceptions.NoEmployeeException;
import com.msitko.employeemanager.exceptions.ParseGenderException;
import com.msitko.employeemanager.models.Employee;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * Class is used to presenting information about Employee
 * 
 * @author Marcin Sitko
 * @version 1.0
 * 
 */
public class EmployeeView extends EventSource {

	/**
	 * Method prints menu and proceeding chosen action
	 */
	public void printMenu() {
		System.out.println("1. Wyświetl pracowników");
		System.out.println("2. Dodaj dane pracownika");
		System.out.println("3. Zmień dane pracownika");
		System.out.println("4. Usuń dane pracownika");
		System.out.println("5. Wyjdź z aplikacji");

		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			int option = Integer.parseInt(br.readLine());
			
			switch (option) {
			case 1:
				displayEmployeesEvent();
				break;
			case 2:
				addEmployeeEvent();
				break;
			case 3:
				editEmployeeEvent();
				break;
			case 4:
				deleteEmployeeEvent();
				break;
			case 5:
				exitAppEvent();
				break;
			default:
				this.printMenu();

			}
		} catch (IOException e) {
			System.out.print(e);

		} catch (NumberFormatException e) {
			System.out
					.println("Podana opcja jest nieprawidłowa, wybierz jeszcze raz:");
			this.printMenu();
		}

	}

	/**
	 * 
	 * @param editedEmployee
	 *            Reference to Employee object which will be filled with
	 *            data(except Employee id).
	 */
	public void fillEmployeeWithData(Employee editedEmployee) {

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		try {
			System.out.println("Podaj imię  pracownika:");
			editedEmployee.setName(br.readLine());
			System.out.println("Podaj nazwisko  pracownika:");
			editedEmployee.setSurname(br.readLine());
		} catch (IOException exc) {
			System.out.println(exc);
		}

		boolean isPeselCorrect = false;
		while (!isPeselCorrect) {
			try {
				System.out.println("Podaj pesel pracownika: ");
				long pesel = Long.parseLong(br.readLine());
				editedEmployee.setPesel(pesel);
				isPeselCorrect = true;

			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			} catch (NumberFormatException ex) {
				System.out.println("Podana wartość nie jest liczbą całkowitą");
			} catch (InvalidPeselException ex) {
				System.out.println(ex.getMessage());
			}
		}

		boolean isGenderCorrect = false;
		while (!isGenderCorrect) {
			try {
				System.out
						.println("Podaj pesel praconika (M - mężczyzna, K -kobieta) ");
				char gender = br.readLine().charAt(0);
				editedEmployee.setGender(gender);
				isGenderCorrect = true;

			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			} catch (ParseGenderException ex) {
				System.out.println(ex.getMessage());
			}

		}

		boolean dateValid = false;
		while (false == dateValid) {
			try {
				System.out
						.println("Podaj datę (w formacie dd/mm/yyyy) urodzenia  pracownika:");
				String birthDate = br.readLine();
				editedEmployee.setBirthDate(birthDate);
				dateValid = true;
			} catch (IOException ex) {
				System.out.println(ex);
			} catch (ParseException ex) {
				System.out
						.println("Data została podana w złym formacie, podaj ją jeszcze raz");
			}
		}

		boolean isPhoneCorrect = false;
		while (!isPhoneCorrect) {
			try {
				System.out.println("Podaj numer telefonu pracownika: ");
				String phoneNumber = br.readLine();
				editedEmployee.setPhoneNumber(phoneNumber);
				isPhoneCorrect = true;
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			} catch (InvalidPhoneNumberException ex) {
				System.out.println("Podany numer telefonu jest nieprawidłowy");
			}
		}
		
		boolean isEmailCorrect = false;
		while (!isEmailCorrect) {
			try {
				System.out.println("Podaj adres email pracownika: ");
				String email = br.readLine();
				editedEmployee.setEmailAddress(email);
				isEmailCorrect = true;
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			} catch (InvalidEmailException ex) {
				System.out.println("Podany adres email jest nieprawidłowy");
			}
		}

		boolean rateValid = false;
		while (false == rateValid) {
			try {
				System.out.println("Podaj stawkę godzinową w PLN:");
				editedEmployee.setRatePerHour(Float.parseFloat(br.readLine()));
				rateValid = true;
			} catch (IOException ex) {
				System.out.println(ex);
			} catch (NumberFormatException ex) {
				System.out
						.println("Podano stawka jest nieprawidłowa, wprowadź ją jeszcze raz.");
			}

		}

	}

	/**
	 * Method drawing and handling editing of an employee
	 * 
	 * @param employeeModel
	 *            Reference to a model on which view will work
	 */
	public void editEmployeeView(EmployeeRepository employeeModel) {

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		Employee editedEmployee = null;

		try {
			System.out
					.println("Podaj ID pracownika, którego dane chcesz edytować:");
			int x = 0;
			x = Integer.parseInt(br.readLine());
			editedEmployee = employeeModel.getEmployeeByID(x);
		} catch (IOException ex) {
			System.out.println(ex);
		} catch (NumberFormatException ex) {
			System.out.println("Podana wartość nie jest liczbą");
		} catch (NoEmployeeException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		fillEmployeeWithData(editedEmployee);
		employeeModel.saveToFile();

	}

	/**
	 * 
	 * Method drawing and handling showing of an employee dialog.
	 * 
	 * @param employeeModel
	 *            Reference to a model on which view will work
	 */
	public void deleteEmployeeView(EmployeeRepository employeeModel) {

		System.out.println("Podaj ID pracownika, którego mam usunąć: ");
		int id = 0;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		try {
			id = Integer.parseInt(br.readLine());
			if (employeeModel.deleteEmployee(id)) {
				System.out.println("Dane pracownika o ID: "
						+ String.valueOf(id) + " został usunięty");
			}
		} catch (IOException ex) {
			System.out.println(ex);
		} catch (NumberFormatException ex) {
			System.out.println("Podano ID w złym formacie");
			return;
		}

	}

	/**
	 * Method drawing and handling showing of an employee dialog.
	 * 
	 * @param employeeModel
	 *            Reference to a model on which view will work
	 */
	public void showEmployeesView(EmployeeRepository employeeModel) {
		for (Employee it : employeeModel.getEmplList()) {
			System.out.println(it.toString());
		}
	}

	/**
	 * Method drawing and handling adding of an employee dialog.
	 * 
	 * @param employeeModel
	 *            Reference to a model on which view will work
	 */
	public void addEmployeeView(EmployeeRepository employeeModel) {
		Employee newEmpl = new Employee();
		fillEmployeeWithData(newEmpl);
		employeeModel.addEmployee(newEmpl);
	}

	/**
	 * Method firing delete employee event
	 */
	public void editEmployeeEvent() {
		fireEvent(new ActionEvent(this, EmployeeController.MenuOptions.EDIT_EMPLOYEE.getValueOf(), null));
		displayMenuEvent();
	}

	/**
	 * Method firing add employee event
	 */
	public void addEmployeeEvent() {
		
		fireEvent(new ActionEvent(this, EmployeeController.MenuOptions.ADD_EMPLOYEE.getValueOf(), null));
		displayMenuEvent();
	}

	/**
	 * Method firing delete employee event
	 */
	public void deleteEmployeeEvent() {
		fireEvent(new ActionEvent(this, EmployeeController.MenuOptions.DELETE_EMPLOYEE.getValueOf(), null));
		displayMenuEvent();
	}

	/**
	 * Method firing display employee event
	 */
	public void displayEmployeesEvent() {
		fireEvent(new ActionEvent(this, EmployeeController.MenuOptions.DISPLAY_EMPLOYEE.getValueOf(), null));
		displayMenuEvent();
	}

	/**
	 * Method firing display menu event
	 */
	public void displayMenuEvent() {
		fireEvent(new ActionEvent(this, EmployeeController.MenuOptions.DISPLAY_MENU.getValueOf(), null));
	}

	/**
	 * Method firing exit application event
	 */
	public void exitAppEvent() {
		fireEvent(new ActionEvent(this, EmployeeController.MenuOptions.EXIT_APP.getValueOf(), null));
	}
}
