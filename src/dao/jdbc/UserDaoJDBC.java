package dao.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dao.UserDao;
import database.DatabaseManager;
import model.User;
import model.UserType;
import model.Book;
import model.BookLoan; 

public class UserDaoJDBC implements UserDao {

	@Override
	public void delete(User user) {
		final Connection connection = DatabaseManager.getConnection();
		final String query = " DELETE FROM user_data WHERE id = ? ";
		try {
			final PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, user.getId());
			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (final SQLException e) {
			System.out.println("[UserDaoJDBC ] Errore nella delete: " + e.getMessage());
		}

	}

	@Override
	public List<User> findAll() {
		final Connection conn = DatabaseManager.getConnection();
		List<User> results = new LinkedList<User>();
		final String query = "SELECT * FROM user_data";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				results.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("address"),
						rs.getString("email"), rs.getString("password"), UserType.fromString(rs.getString("user_type")),
						rs.getFloat("total_arrears"), rs.getInt("book_count")));
			}
			stmt.close();
			//conn.close();
			return results;
		} catch (SQLException e) {

		}	
		return null;
	}

	// Alla creazione dell'utente (SOLO SE UTENTE STD) bisogna creare il registro
	@Override
	public void save(User user) {
		final Connection conn = DatabaseManager.getConnection();

		final String query = "INSERT INTO user_data (id, name, surname, address, email, password, user_type, total_arrears, book_count) VALUES( ' "
				+ user.getId() +"','" 
				+ user.getName() + "','" 
				+ user.getSurname() + "','"
				+ user.getAddress() + "','" 
				+ user.getEmail() + "','" 
				+ user.getPassword() + "','" 
				+ user.getUser_type().toString() + "','"
				+ user.getTotal_arrears() + "','"
				+ user.getBook_count() +"');";
		try {
			PreparedStatement stmtu = conn.prepareStatement(query);
			stmtu.executeUpdate();
			stmtu.close();
			//conn.close();
		} catch (final SQLException e) {
			System.out.println("[UserDaoJDBC] Errore nella save: " + e.getMessage());
		}

	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean isMoroso(User user) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT * FROM BookLoan WHERE user_id = ? AND arrears != 0 ";

		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1,user.getId());

			ResultSet rs = stmt.executeQuery();
			//System.out.println("RESULT SET : "+ rs.getString(columnIndex)());

			if (rs.next() == false) {
				stmt.close();
				//conn.close();
				return false;
			}
			stmt.close();
			//conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User getbyKey(int id) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT * FROM user_data WHERE id = ? ";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1,id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return new User(rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("surname"), 
						rs.getString("address"),
						rs.getString("email"), 
						rs.getString("password"), 
						UserType.fromString(rs.getString("user_type")),
						rs.getFloat("total_arrears"),
						rs.getInt("book_count"));
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("getByKey: " + e.getLocalizedMessage());
		}
		return null;
	}
	@Override
	public int getBookCount(User user) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT book_count FROM user_data WHERE id = ? ";

		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1,user.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int result = rs.getInt("book_count");
				return result;
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("getBookCount: " + e.getLocalizedMessage());
		}
		return 0;
	}
	
	@Override
	public List<User> findAllNoAdmin() {
		final Connection conn = DatabaseManager.getConnection();
		List<User> results = new LinkedList<User>();
		final String query = "SELECT * FROM user_data WHERE user_type != 'Admin';";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				results.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("address"),
						rs.getString("email"), rs.getString("password"), UserType.fromString(rs.getString("user_type")),
						rs.getFloat("total_arrears"), rs.getInt("book_count")));
			}
			stmt.close();
			//conn.close();
			return results;
		} catch (SQLException e) {
			System.out.println("Error in findAllNoAdmin: " + e.getLocalizedMessage());
		}	
		return null;
	}

}
