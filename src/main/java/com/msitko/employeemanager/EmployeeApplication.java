package com.msitko.employeemanager;

import javax.swing.SwingUtilities;
import javax.swing.plaf.SliderUI;

import com.msitko.employeemanager.controllers.EmployeeSwingController;
import com.msitko.employeemanager.dataaccess.EmployeeDAO;
import com.msitko.employeemanager.dataaccess.EmployeeRepository;
import com.msitko.employeemanager.views.EmployeeGuiView;
import com.msitko.employeemanager.views.EmployeeStandardBuilder;
import com.msitko.employeemanager.views.EmployeeViewCreator;

/**
 * Main class of Employee Manager application, contain only a main loop.
 * 
 * @author Marcin Sitko
 * @version 1.0
 * 
 */
public class EmployeeApplication {

	public static class MojaKlasa {
		int x;

		public MojaKlasa(int x) {
			this.x = x;
		}

		public synchronized int getX() throws InterruptedException {
			wait(1000);
			return x;
		}
		public synchronized void doSomething(){
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("doSomething()");
		}
		public synchronized void doSomethingElse(){
			System.out.println("doSomethingElse()");
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 * 
	 * 
	 */
	public static void main(String[] args) {
		String fileName = null;
		try {
			fileName = args[0];
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Wyjątek , indeks poza tablicą : "
					+ ex.getLocalizedMessage() + "\n" + ex.getMessage());
		}
		final MojaKlasa klasa = new MojaKlasa(2);
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				int x = 0;
				Thread th2 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						klasa.doSomething();
						
					}
				});
				th2.start();
				klasa.doSomethingElse();
				System.out.println("Koniec");
			}
		});
		th.run();
		
		EmployeeGuiView mainView = (new EmployeeViewCreator(
				new EmployeeStandardBuilder())).createEmployeeWindow();
		EmployeeRepository mainRepository = new EmployeeRepository(fileName);
		@SuppressWarnings("unused")
		EmployeeSwingController mainContr = new EmployeeSwingController(
				mainView, mainRepository);
		EmployeeDAO dao = new EmployeeDAO();
		mainView.getMainFrame().setVisible(true);
	}
}
