/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.exceptions;

/**
 * Exception class which is used to communicate that there is no employee with
 * passed ID number
 * 
 * @author Marcin Sitko
 * @version 1.0
 */
public class NoEmployeeException extends Exception {

	/**
	 * Used in serialization
	 */
	private static final long serialVersionUID = 4502455153021252543L;

	public NoEmployeeException(String msg) {
		super(msg);
	}
}
