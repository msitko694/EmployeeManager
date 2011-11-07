/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msitko.employeemanager.events;


import java.awt.event.*;
import java.util.ArrayList;

/**
 * Class gives methods which are needed to be availble in event sources. If you
 * want to make your own class acts like a event source extend EventSource
 * class.
 * 
 * @author Marcin Sitko
 * @version 1.0
 */
public abstract class EventSource {
	/**
	 * List of all action listeners
	 */
	ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	/**
	 * Add passed listener to listeners
	 * 
	 * @param l
	 *            Passed listener
	 */
	public void addListener(ActionListener l) {
		listeners.add(l);
	}

	/**
	 * Remove passed listener from listeners
	 * 
	 * @param l
	 *            Passed listener
	 */
	public void removeListener(ActionListener l) {
		listeners.remove(l);
	}

	/**
	 * Fire event by invoking actionPerformed method of every listener in the
	 * list
	 * 
	 * @param e
	 *            Event object passed to action listeners
	 */
	public void fireEvent(ActionEvent e) {
		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}

}
