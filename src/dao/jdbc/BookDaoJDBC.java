package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dao.BookDao;
import database.DatabaseManager;
import model.Book;
import model.BookDescription;

public class BookDaoJDBC implements BookDao {

	@Override
	public void delete(Book book) {
		final Connection connection = DatabaseManager.getConnection();
		final String query = " DELETE FROM Book WHERE id = ? ";
		try {
			final PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, book.getId());
			pstmt.executeUpdate();
			pstmt.close();
			//connection.close();
		} catch (final SQLException e) {
			System.out.println("[BookDaoJDBC] Errore nella delete: " + e.getMessage());
		}
	}

	@Override
	public List<Book> findAll() {
		List<Book> results = new LinkedList<Book>();
		final Connection connection = DatabaseManager.getConnection();
		BookDescriptionDaoJDBC bd = new BookDescriptionDaoJDBC();
		final String query = "SELECT * FROM Book";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				results.add(new Book(rs.getInt("id"),
						bd.getbyKey(rs.getString("book_description")),
						rs.getBoolean("available")));
			}
			stmt.close();
			//connection.close();
			return results;
		} catch (SQLException e) {
			System.out.println("[BookDaoJDBC] Errore nella findAll: " + e.getMessage());
		}
		return null;
	}

	@Override
	public void save(Book book) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "INSERT INTO Book ( book_description,available) VALUES('" 
				+ book.getBookDescription().getISBN() + "','" + book.isAvailable() + "');";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (final SQLException e) {
			System.out.println("[BookDaoJDBC] Errore nella save: " + e.getMessage());
		}
	}

	@Override
	public void update(Book book) {
		final Connection conn = DatabaseManager.getConnection();
		BookDescriptionDaoJDBC bd = new BookDescriptionDaoJDBC();
		final String query = "UPDATE Book SET available= ? WHERE id = ?";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setBoolean(1, book.isAvailable());
			stmt.setInt(2, book.getId());
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Book getbyKey(int id) {
		final Connection conn = DatabaseManager.getConnection();
		BookDescriptionDaoJDBC bd = new BookDescriptionDaoJDBC();
		final String query = "SELECT * FROM Book WHERE id = " + id;
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				return new Book(rs.getInt("id"),
						bd.getbyKey(rs.getString("book_description")),
						rs.getBoolean("available"));
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
