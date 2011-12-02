/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.models;

import com.msitko.employeemanager.dataaccess.EmployeeRepository;
import com.msitko.employeemanager.exceptions.InvalidEmailException;
import com.msitko.employeemanager.exceptions.InvalidPeselException;
import com.msitko.employeemanager.exceptions.InvalidPhoneNumberException;
import com.msitko.employeemanager.exceptions.ParseGenderException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class is used to represent a data of single employee.
 * 
 * @see EmployeeRepository
 * 
 * @author Marcin Sitko
 * @version 1.0
 */
public class Employee {
	/**
	 * Enum class describe gender of Employee.
	 * 
	 * @author Marcin Sitko
	 * @version 1.0
	 */
	public enum Gender {
		MALE(0), FEMALE(1);
		
		private int intValue;
		private Gender(int value) {
			this.intValue = value;
		}
		/**
		 * @return the intValue
		 */
		public int getIntValue() {
			return intValue;
		}
		/**
		 * @param intValue the intValue to set
		 */
		public void setIntValue(int intValue) {
			this.intValue = intValue;
		}
		
		
	}

	/**
	 * Enum class containt unique number assigned to each data field so it can
	 * be easy used in switch instructions.
	 * 
	 * @author Marcin Sitko
	 * @version 1.0
	 */
	public static enum Field {
		ID(0), PESEL(1), NAME(2), SURNAME(3), GENDER(4), PHONE_NUMBER(5), EMAIL(
				6), RATE_PER_HOUR(7), BIRTH_DATE(8);
		private int fieldId;

		Field(int fieldId) {
			this.fieldId = fieldId;
		}

		public int getFieldId() {
			return fieldId;
		}
	}

	public static final int fieldsCount = 9;

	Gender gender;
	/**
	 * contain 11 digits number
	 */
	long pesel;
	long id;
	String name, surname;
	String phoneNumber;
	String emailAddress;
	float ratePerHour;
	Date birthDate;

	/**
	 * Function checked if passed email is valid. Email should be in format
	 * <tt>login@domain.endofdoamin</tt> where <tt>login</tt> can contain
	 * alphanumeric data + underlines + dots, <tt>domain</tt> can also contain
	 * alphanumeric data + underlines + dots, <tt>endofdomain</tt> has max
	 * length 4 characters and can contain only alphabet data.
	 * 
	 * @param email
	 *            sets email propertied
	 * @throws InvalidEmailException
	 *             If email invalid throw exception
	 */
	public void validateEmail(String email) throws InvalidEmailException {
		Pattern emailPattern = Pattern
				.compile("^[\\w-\\.]+@(?:[\\w]+\\.)+[a-zA-Z]{2,4}$");
		Matcher emailMatcher = emailPattern.matcher(email);
		if (!emailMatcher.find()) {
			throw new InvalidEmailException("Podany email jest nieprawidłowy");
		}
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 * @throws InvalidEmailException
	 *             Throws <tt>InvalidEmailException</tt> if email is not correct
	 * 
	 * 
	 */
	public void setEmailAddress(String emailAddress)
			throws InvalidEmailException {
		try {
			validateEmail(emailAddress);
			this.emailAddress = emailAddress;
		} catch (InvalidEmailException ex) {
			throw new InvalidEmailException(ex.getMessage());
		}
	}

	/**
	 * Default constructor, initializing birthDate field by default Date()
	 * constructor, rest fields receiving their default values.
	 */
	public Employee() {
		birthDate = new Date();
		name = "";
		surname = "";
		phoneNumber = "";
		emailAddress = "";
		gender = Gender.MALE;
		ratePerHour = 0.0f;
	}

	/**
	 * 
	 * @return Gender of employee
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * 
	 * @param gender
	 *            Gender of employee
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 
	 * @param gender
	 *            Gender of employee in char format (M- male, K- female)
	 */
	public void setGender(char gender) throws ParseGenderException {
		gender = Character.toLowerCase(gender);
		if (gender == 'm') {
			setGender(Gender.MALE);
		} else if (gender == 'k')
			setGender(Gender.FEMALE);
		else
			throw new ParseGenderException(
					"Błąd przy parsowaniu płci pracownika, podano złą opcję");
	}

	/**
	 * 
	 * @return get Pesel number of employee
	 */
	public long getPesel() {
		return pesel;
	}

	/**
	 * 
	 * @param pesel
	 *            Pesel number of employee
	 * @throws InvalidPeselException
	 *             If pesel is invalid throw InvalidPeselException
	 */
	public void setPesel(long pesel) throws InvalidPeselException {
		try {
			validatePesel(pesel);
			this.pesel = pesel;
		} catch (InvalidPeselException ex) {
			throw new InvalidPeselException(ex.getMessage());
		}

	}

	/**
	 * Method checked if pesel number is correct. Algorithm of checking pesel
	 * validation is described <a href="http://pl.wikipedia.org/wiki/PESEL"
	 * >here. </a>
	 * 
	 * @param pesel
	 *            Pesel number of employee
	 * @throws InvalidPeselException
	 *             If pesel invalid throw InvalidPeselException
	 */
	public void validatePesel(long pesel) throws InvalidPeselException {

		byte[] peselDigits = new byte[11];

		for (int i = 0; i < 11; i++) {

			peselDigits[i] = (byte) (pesel % 10);
			pesel = pesel / 10;
		}
		byte[] multiplierWages = { 3, 1, 9, 7, 3, 1, 9, 7, 3, 1 };

		int controlSum = 0;
		for (int i = 1; i < 11; i++) {
			controlSum += peselDigits[i] * multiplierWages[i - 1];
		}
		controlSum = controlSum % 10;
		controlSum = 10 - controlSum;
		controlSum = controlSum % 10;

		if (controlSum != peselDigits[0]) {
			throw new InvalidPeselException("Podany pesel jest nieprawidłowy ");
		}
	}

	/**
	 * 
	 * @return id of employee
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            id of employee
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return Returns birth date of employee
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * 
	 * @param birthDate
	 *            Sets birth date of employee
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * 
	 * @param birthDate
	 *            Sets birth date of employee from string in format (dd/mm/yyyy)
	 * @throws ParseException
	 *             if passed date isn't in yyyy/ format
	 */
	public void setBirthDate(String birthDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy/mm/dd");
		this.birthDate = format.parse(birthDate);
	}

	/**
	 * 
	 * @return Returns rate per hour in PLN
	 */
	public float getRatePerHour() {
		return ratePerHour;
	}

	/**
	 * 
	 * @param ratePerHour
	 *            Sets rate per hour for employee
	 */
	public void setRatePerHour(float ratePerHour) {
		this.ratePerHour = ratePerHour;
	}

	/**
	 * 
	 * @return Return employee name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            setting name for employee
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Return employee surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * 
	 * @return Returns age of employee
	 */
	public int getAge(Calendar actualDate) {

		Calendar calBirthDate = new GregorianCalendar();
		calBirthDate.setTime(getBirthDate());
		int age = actualDate.get(Calendar.YEAR)
				- calBirthDate.get(Calendar.YEAR);
		if (calBirthDate.get(Calendar.MONTH) > actualDate.get(Calendar.MONTH)) {
			age--;
		} else if (calBirthDate.get(Calendar.MONTH) == actualDate
				.get(Calendar.MONTH)) {
			if (calBirthDate.get(Calendar.DAY_OF_MONTH) > actualDate
					.get(Calendar.DAY_OF_MONTH)) {
				age--;
			}
		}
		return age;

	}

	/**
	 * 
	 * @param surname
	 *            Setting surname for employee
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		String genderName = null;
		if (this.gender == Gender.MALE) {
			genderName = "MĘŻCZYZNA";
		} else {
			genderName = "KOBIETA";
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		String str = "ID: " + id + "\n" + "Pesel: " + genderName + "\n"
				+ "Pesel: " + String.valueOf(pesel) + "\n" + "Imię: " + name
				+ " \n" + "Nazwisko: " + surname + "\n" + "Wiek: "
				+ getAge(Calendar.getInstance()) + "\n" + "Data urodzenia: "
				+ format.format(birthDate) + "\n" + "Numer telefonu: "
				+ getPhoneNumber() + "\n" + "Adres email: " + getEmailAddress()
				+ "\n" + "Stawka godzinowa: " + Float.toString(ratePerHour)
				+ " \n";

		return str;
	}

	/**
	 * 
	 * @return Phone number of employee
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 
	 * @param phoneNumber
	 *            Phone number of employee
	 * @throws InvalidPhoneNumberException
	 *             If phone number contain other chars than 0..9 , (,),+ throw
	 *             exception.
	 */
	public void setPhoneNumber(String phoneNumber)
			throws InvalidPhoneNumberException {
		this.validatePhoneNumber(phoneNumber);
		this.phoneNumber = phoneNumber;
	}

	public void validatePhoneNumber(String phoneNumber)
			throws InvalidPhoneNumberException {
		Pattern phonePatter = Pattern.compile("^[+]??[\\-()\\s0-9]+$");
		Matcher phoneMatcher = phonePatter.matcher(phoneNumber);
		if (!phoneMatcher.find()) {
			throw new InvalidPhoneNumberException(
					"Podany email jest nieprawidłowy");
		}
	}

}
