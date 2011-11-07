/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.exceptions;

/**
 * Exception class which is used to communicate that pesel is invalid
 * 
 * @author Maricn Sitko
 * @version 1.0
 */
public class InvalidPeselException extends Exception {

	/**
	 * Used in serialization
	 */
	private static final long serialVersionUID = 4405924386669042660L;

	public InvalidPeselException(String msg) {
		super(msg);
	}
}
