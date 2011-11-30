package com.msitko.employeemanager.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.msitko.employeemanager.dataaccess.EmployeeRepository;
import com.msitko.employeemanager.models.EmployeeTableModel;

/**
 * Standard employee window builder. Builds window with frame's default close
 * operation set to EXIT_ON_CLOSE.
 * 
 * @author Marcin Sitko
 * @version 1.0
 * @see EmployeeViewCreator
 * @see EmployeeGuiView
 */
public class EmployeeStandardBuilder implements IEmployeeGuiBuilder {

	private EmployeeGuiView buildedView;

	/**
	 * Default constructor which create new <code> EmployeeGuiView </code> and
	 * assign it to <code> buildedView </code>
	 */
	public EmployeeStandardBuilder() {
		buildedView = new EmployeeGuiView();
	}

	@Override
	public void buildFrame() {
		buildedView.setMainFrame( new JFrame("Pracownicy"));
		buildedView.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildedView.getMainFrame().setSize(400, 300);
	}

	@Override
	public void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Plik");
		fileMenu.add("Zakończ");
		menuBar.add(fileMenu);
		JMenu helpMenu = new JMenu("Pomoc");
		helpMenu.add("O programie");
		menuBar.add(helpMenu);
		buildedView.getMainFrame().setJMenuBar(menuBar);
	}

	@Override
	public void buildToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);

		JTextField txtSearchField = new JTextField();
		txtSearchField.setPreferredSize(new Dimension(150, 0));
		JButton searchButton = new JButton();
		searchButton.setText("Szukaj");

		toolBar.add(txtSearchField);
		toolBar.add(searchButton);

		JPanel toolPanel = new JPanel(new BorderLayout());
		toolPanel.setPreferredSize(new Dimension(400, 35));
		toolPanel.add(toolBar, BorderLayout.NORTH);

		buildedView.getMainFrame().getContentPane().add(toolPanel, BorderLayout.NORTH);
	}

	@Override
	public void buildComponent() {
		buildedView.setAllEmployeesTable(new JTable());
		buildedView.getAllEmployeesTable().setName("Wszyscy pracownicy");
		EmployeeRepository repo = new EmployeeRepository("employee.txt");
		EmployeeTableModel dataModel = new EmployeeTableModel();
		dataModel.setListOfEmployees(repo.getEmplList());
		buildedView.getAllEmployeesTable().setModel(dataModel);
	}

	@Override
	public void buildContainer() {
		buildedView.setTabbedPane(new JTabbedPane());
		buildedView.getTabbedPane().add(buildedView.getAllEmployeesTable());
		buildedView.getMainFrame().getContentPane().add(buildedView.getTabbedPane(), BorderLayout.CENTER);
	}

	@Override
	public void buildOptionPane() {
		// TODO Auto-generated method stub

	}

	@Override
	public EmployeeGuiView getCreatedWindow() {
		return buildedView;
	}

}
