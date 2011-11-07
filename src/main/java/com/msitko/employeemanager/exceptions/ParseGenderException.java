/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.exceptions;

/**
 * Exception class which is used to communicate that parsing gender was failed
 * (Parsed can by only MALE and FEMALE string)
 * 
 * @author Marcin Sitko
 * @version 1.0
 * 
 */
public class ParseGenderException extends Exception {

	/**
	 * Used for serialization
	 */
	private static final long serialVersionUID = -6213477465915682667L;

	public ParseGenderException(String msg) {
		super(msg);
	}

}
