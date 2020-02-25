package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	private static Connection connection;
	private static DatabaseManager instance = null;

	public static Connection getConnection() {
		return connection;
	}

	public static DatabaseManager getInstance() {
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}

	private final String password = "biblioteca";

	// Credenziali elephantSQL
	private final String url = "jdbc:postgresql://magikarp.fun:5432/biblioteca";

	private final String user = "ingsw";

	private DatabaseManager() {
		connect();
	}

	public void connect() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (final ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("[SQLConnector] Connesso a " + url);
		} catch (final SQLException e) {
			System.out.println("[SQLConnector] Errore: " + e.getMessage());
		}
	}

	public void createTableBook() {
		final String table = "CREATE TABLE IF NOT EXISTS Book( \n " 
				+ "id SERIAL PRIMARY KEY , \n"
				+ "book_description TEXT REFERENCES BookDescription(isbn), \n" 
				+ "available BOOLEAN NOT NULL \n" 
				+ ");";
		try {
			final Statement stmt = connection.createStatement();
			stmt.executeUpdate(table);
		} catch (final SQLException e) {
			System.out.println("[DatabaseManager] Errore nella query createTable Book: " + e.getMessage());
		}
	}

	public void createTableBookDescription() {
		final String table = "CREATE TABLE IF NOT EXISTS BookDescription( \n "
				+ "isbn TEXT PRIMARY KEY, \n"
				+ "author TEXT NOT NULL, \n" 
				+ "title TEXT NOT NULL, \n" 
				+ "publishing_house TEXT NOT NULL, \n"
				+ "year DATE NOT NULL, \n" 
				+ "category TEXT NOT NULL, \n" 
				+ "description TEXT NOT NULL, \n" 
				+ "imageUrl TEXT NOT NULL \n"
				+ ");";
		try {
			final Statement stmt = connection.createStatement();
			stmt.executeUpdate(table);
		} catch (final SQLException e) {
			System.out.println("[DatabaseManager] Errore nella query createTable BookDescription: " + e.getMessage());
		}
	}

	public void createTableBookLoan() {
		final String table = "CREATE TABLE IF NOT EXISTS BookLoan( \n " + "id SERIAL PRIMARY KEY , \n"
				+ "loan_date DATE NOT NULL, \n" 
				+ "return_date DATE NOT NULL, \n" 
				+ "extendable BOOLEAN NOT NULL, \n"
				+ "arrears REAL, \n" 
				+ "book INTEGER NOT NULL ,\n"
				+ "returned BOOLEAN NOT NULL,\n"
				+ "user_id INTEGER NOT NULL, \n"
				+ "arrear_payed BOOLEAN NOT NULL \n"
				+ ");";
		try {
			final Statement stmt = connection.createStatement();
			stmt.executeUpdate(table);
		} catch (final SQLException e) {
			System.out.println("[DatabaseManager] Errore nella query createTable BookLoan: " + e.getMessage());
		}
	}

	public void createTableUser() {
		final String table = "CREATE TABLE IF NOT EXISTS user_data ( \n " 
				+ "id INTEGER PRIMARY KEY , \n"
				+ "name TEXT NOT NULL, \n" 
				+ "surname TEXT NOT NULL, \n"
				+ "address TEXT NOT NULL, \n"
				+ "email TEXT NOT NULL, \n" 
				+ "password TEXT NOT NULL, \n" 
				+ "user_type TEXT NOT NULL, \n"
				+ "total_arrears REAL, \n" 
				+ "book_count INTEGER \n " 
				+ ");";
		try {
			final Statement stmt = connection.createStatement();
			stmt.executeUpdate(table);
		} catch (final SQLException e) {
			System.out.println("[DatabaseManager] Errore nella query createTable User: " + e.getMessage());
		}
	}

	public void createTableBookLog() {
		final String table = "CREATE TABLE IF NOT EXISTS BookLog( \n " 
				+ "book_id INTEGER PRIMARY KEY , \n"
				+ "book_orders INTEGER [] \n" 
				+ ");";
		try {
			final Statement stmt = connection.createStatement();
			stmt.executeUpdate(table);
		} catch (final SQLException e) {
			System.out.println("[DatabaseManager] Errore nella query createTable BookLog: " + e.getMessage());
		}
	}

	public void createTableBookOrder() {
		final String table = "CREATE TABLE IF NOT EXISTS BookOrder( \n " 
				+ "book_id SERIAL PRIMARY KEY , \n"
				+ "book_loans INTEGER [], \n"
				+ "user_id INTEGER NOT NULL \n"
				+ ");";
		try {
			final Statement stmt = connection.createStatement();
			stmt.executeUpdate(table);
		} catch (final SQLException e) {
			System.out.println("[DatabaseManager] Errore nella query createTable BookOrder: " + e.getMessage());
		}
	}

	public void DatabaseInit() {
		createTableBookDescription();
		createTableBook();
		createTableBookLoan();
		createTableUser();
		createTableBookOrder();
		createTableBookLog();
	}

}
