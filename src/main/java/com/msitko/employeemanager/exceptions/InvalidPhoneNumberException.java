package com.msitko.employeemanager.exceptions;

/**
 * Exception class which is used to communicate that phone number is invalid
 * 
 * @author Maricn Sitko
 * @version 1.0
 */
public class InvalidPhoneNumberException extends Exception {

	/**
	 * Used in serialization
	 */
	private static final long serialVersionUID = -7668369420910093705L;

	public InvalidPhoneNumberException(String msg) {
		super(msg);
	}

}
