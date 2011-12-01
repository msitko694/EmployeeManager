package com.msitko.employeemanager.dataaccess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.msitko.employeemanager.exceptions.InvalidEmailException;
import com.msitko.employeemanager.exceptions.InvalidPeselException;
import com.msitko.employeemanager.exceptions.InvalidPhoneNumberException;
import com.msitko.employeemanager.exceptions.NoEmployeeException;
import com.msitko.employeemanager.models.Employee;

/**
 * Class represent Employee DAO object, gets configuration from xml file
 * 
 * @author Marcin Sitko
 * @version 1.0
 */
public class EmployeeDAO implements IEmployeeDAO  {

	Connection connection;

	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}

	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Default constructor which sets connection to database. It reads data from
	 * xml configuration file with name dbConnection.xml
	 */
	public EmployeeDAO() {

		File cfgConnectionFile = new File("dbConnection.xml");
		if (!cfgConnectionFile.exists()) {
			createDefaultXml();
			cfgConnectionFile = new File("dbConnection.xml");
		}

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document dbXml = docBuilder.parse(cfgConnectionFile);
			NodeList nodeList = dbXml.getDocumentElement()
					.getElementsByTagName("jdbcClassName");
			String jdbcClassName = nodeList.item(0).getTextContent();
			nodeList = dbXml.getDocumentElement().getElementsByTagName(
					"connectionString");
			String connectionString = nodeList.item(0).getTextContent();

			Class.forName(jdbcClassName);
			connection = DriverManager.getConnection(connectionString);
			createNewDb();
		} catch (ParserConfigurationException ex) {
			ex.getStackTrace();
			System.out
					.println("Błąd przy odczycie xml z konfiguracja polaczenia bazy danych");
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Nie znaleziono sterownika klasy jdbc");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#createNewDb()
	 */
	@Override
	public void createNewDb() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select tbl_name from sqlite_master where tbl_name = 'Employees'");
			if (!resultSet.next()) {
				statement
						.executeUpdate("create table Employees (name string, surname string, pesel int8, "
								+ "phoneNumber string, emailAddress string, ratePerHour float, birthDate date) ");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#createDefaultXml()
	 */
	@Override
	public void createDefaultXml() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Node rootNode = doc.createElement("database");
			doc.appendChild(rootNode);

			Node appendedNode = doc.createElement("jdbcClassName");
			appendedNode.setTextContent("org.sqlite.JDBC");
			rootNode.appendChild(appendedNode);
			appendedNode = doc.createElement("connectionString");
			appendedNode.setTextContent("jdbc:sqlite:employees.db");
			rootNode.appendChild(appendedNode);

			FileWriter dbConfiguration = new FileWriter("dbConnection.xml");
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult stream = new StreamResult(dbConfiguration);
			transformer.transform(domSource, stream);
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
			System.out.println("Nie udało się stworzyć DocumentBuilder");
		} catch (DOMException ex) {
			ex.printStackTrace();
			System.out.println("Blad przy operacji DOM");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Blad operacji I/O");
		} catch (TransformerException ex) {
			ex.printStackTrace();
			System.out.println("Blad transformacji");
		}
	}


	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#createEmployee(com.msitko.employeemanager.models.Employee)
	 */
	@Override
	public void createEmployee(Employee newEmployee) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO Employees (name, surname, pesel, phoneNumber, emailAddress, "
							+ "ratePerHour, birthDate) VALUES ( ?, ?, ?, ?, ?, ?, ?)");

			preparedStatement.setQueryTimeout(5);
			preparedStatement.setString(1, newEmployee.getName());
			preparedStatement.setString(2, newEmployee.getSurname());
			preparedStatement.setLong(3, newEmployee.getPesel());
			preparedStatement.setString(4, newEmployee.getPhoneNumber());
			preparedStatement.setString(5, newEmployee.getEmailAddress());
			preparedStatement.setFloat(6, newEmployee.getRatePerHour());
			preparedStatement.setDate(7, new java.sql.Date(newEmployee
					.getBirthDate().getTime()));
			preparedStatement.executeUpdate();

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select last_insert_rowid()");
			resultSet.next();
			newEmployee.setId(resultSet.getInt(1));
			resultSet.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#findEmployeeById(int)
	 */
	@Override
	public Employee findEmployeeById(int id) throws NoEmployeeException,
			SQLException {
		PreparedStatement statement = connection
				.prepareStatement("Select rowid, name, surname, pesel, phoneNumber, emailAddress, "
						+ "ratePerHour, birthDate From Employees Where rowid = ?");
		statement.setInt(1, id);
		statement.setQueryTimeout(5);
		ResultSet resultSet = statement.executeQuery();
		if (!resultSet.next()) {
			throw new NoEmployeeException(
					"Nie odnaleziono pracownika z podanym id");
		}

		Employee readedEmployee = new Employee();
		readedEmployee.setId(resultSet.getInt("rowid"));
		readedEmployee.setName(resultSet.getString("name"));
		readedEmployee.setSurname(resultSet.getString("surname"));
		try {
			readedEmployee.setPesel(resultSet.getLong("pesel"));
			readedEmployee.setPhoneNumber(resultSet.getString("phoneNumber"));
			readedEmployee.setEmailAddress(resultSet.getString("emailAddress"));
		} catch (InvalidPeselException ex) {
			System.out.println("Nie prawidlowy pesel");
		} catch (InvalidEmailException ex) {
			System.out.println("Nie prawidlowy email");
		} catch (InvalidPhoneNumberException ex) {
			System.out.println("Nie prawidlowy numer telefonu");
		}
		readedEmployee.setRatePerHour(resultSet.getFloat("ratePerHour"));
		readedEmployee.setBirthDate(resultSet.getDate("birthDate"));
		resultSet.close();
		return readedEmployee;
	}

	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#deleteEmployee(int)
	 */
	@Override
	public void deleteEmployee(int id) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("Delete From Employees Where rowid = ? ");
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#updateEmployee(com.msitko.employeemanager.models.Employee)
	 */
	@Override
	public void updateEmployee(Employee updatingEmployee) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE Employees SET name= ?, surname= ?, pesel= ?, "
						+ "phoneNumber= ?, emailAddress=?, ratePerHour= ?, birthDate= ? "
						+ "WHERE rowid = ?");

		preparedStatement.setQueryTimeout(5);
		preparedStatement.setString(1, updatingEmployee.getName());
		preparedStatement.setString(2, updatingEmployee.getSurname());
		preparedStatement.setLong(3, updatingEmployee.getPesel());
		preparedStatement.setString(4, updatingEmployee.getPhoneNumber());
		preparedStatement.setString(5, updatingEmployee.getEmailAddress());
		preparedStatement.setFloat(6, updatingEmployee.getRatePerHour());
		preparedStatement.setDate(7, new java.sql.Date(updatingEmployee
				.getBirthDate().getTime()));
		preparedStatement.setInt(8, updatingEmployee.getId());
		preparedStatement.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.msitko.employeemanager.dataaccess.IEmployeeDAO#findAll()
	 */
	@Override
	public ArrayList<Employee> findAll() throws SQLException {
		PreparedStatement statement = connection
				.prepareStatement("Select rowid, name, surname, pesel, phoneNumber, emailAddress, "
						+ "ratePerHour, birthDate From Employees");
		statement.setQueryTimeout(5);
		ResultSet resultSet = statement.executeQuery();
		ArrayList<Employee> resultList = new ArrayList<>();

		while (resultSet.next()) {
			Employee readedEmployee = new Employee();
			readedEmployee.setId(resultSet.getInt("rowid"));
			readedEmployee.setName(resultSet.getString("name"));
			readedEmployee.setSurname(resultSet.getString("surname"));
			try {
				readedEmployee.setPesel(resultSet.getLong("pesel"));
				readedEmployee.setPhoneNumber(resultSet
						.getString("phoneNumber"));
				readedEmployee.setEmailAddress(resultSet
						.getString("emailAddress"));
			} catch (InvalidPeselException ex) {
				System.out.println("Nie prawidlowy pesel");
			} catch (InvalidEmailException ex) {
				System.out.println("Nie prawidlowy email");
			} catch (InvalidPhoneNumberException ex) {
				System.out.println("Nie prawidlowy numer telefonu");
			}
			readedEmployee.setRatePerHour(resultSet.getFloat("ratePerHour"));
			readedEmployee.setBirthDate(resultSet.getDate("birthDate"));
			resultList.add(readedEmployee);
		}
		resultSet.close();
		return resultList;
	}
}
