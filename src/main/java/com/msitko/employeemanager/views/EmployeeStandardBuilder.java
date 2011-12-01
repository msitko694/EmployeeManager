package com.msitko.employeemanager.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.msitko.employeemanager.controllers.EmployeeSwingController;
import com.msitko.employeemanager.models.Employee;
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
	 * Constructor which create new <code> EmployeeGuiView </code>, assign a new
	 * EmployeeGuiView object and connect this object with passed controller
	 * 
	 */
	public EmployeeStandardBuilder(EmployeeSwingController controller) {
		buildedView = new EmployeeGuiView();
		buildedView.setController(controller);
	}

	@Override
	public void buildFrame() {
		buildedView.setMainFrame(new JFrame("Pracownicy"));
		buildedView.getMainFrame().setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);
		buildedView.getMainFrame().setSize(400, 300);
	}

	@Override
	public void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Plik");
		JMenuItem exit = new JMenuItem("Zakończ");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Zakończ")) {
					buildedView.getController().closeApplication();
				}
			}
		});
		fileMenu.add(exit);
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

		buildedView.getMainFrame().getContentPane()
				.add(toolPanel, BorderLayout.NORTH);
	}

	@Override
	public void buildComponent() {				
		buildedView.setAllEmployeesTable(new JTable());
		buildedView.getAllEmployeesTable().setName("Wszyscy pracownicy");
		EmployeeTableModel dataModel = new EmployeeTableModel();
		buildedView.getController().setTableModel(dataModel);
		buildedView.getAllEmployeesTable().setModel(dataModel);
		buildedView.getController().loadAllEmployees();
	}

	@Override
	public void buildContainer() {
		buildedView.setTabbedPane(new JTabbedPane());
		JScrollPane scrollPane = new JScrollPane(buildedView.getTabbedPane());
		buildedView.getTabbedPane().add(buildedView.getAllEmployeesTable());
		buildedView.getTabbedPane().setAutoscrolls(true);
		buildedView.getMainFrame().getContentPane()
				.add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void buildButtons() {
		JPanel buttonsPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		buttonsPanel.setLayout(flowLayout);
		
		JButton butNew = new JButton();
		butNew.setText("Dodaj");
		buttonsPanel.add(butNew);
		buildedView.setButNew(butNew);
		
		JButton butDelete = new JButton();
		butDelete.setText("Usuń");
		buttonsPanel.add(butDelete);
		buildedView.setButDelete(butDelete);
		
		buildedView.getMainFrame().getContentPane().add(buttonsPanel,  BorderLayout.SOUTH);
	}

	@Override
	public EmployeeGuiView getCreatedWindow() {
		return buildedView;
	}

}
