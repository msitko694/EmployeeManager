package com.msitko.employeemanager.exceptions;

public class InvalidEmailException extends Exception {
	/**
	 * Used in serialization
	 */
	private static final long serialVersionUID = 3274132809493929805L;

	public InvalidEmailException(String msg) {
		super(msg);
	}
}
