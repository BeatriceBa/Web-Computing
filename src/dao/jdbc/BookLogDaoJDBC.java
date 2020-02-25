package dao.jdbc;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import dao.BookLogDao;
import dao.BookOrderDao;
import database.DatabaseManager;
import model.BookLoan;
import model.BookLog;
import model.BookOrder;

public class BookLogDaoJDBC implements BookLogDao{

	@Override
	public void delete(BookLog element) {
		final Connection connection = DatabaseManager.getConnection();
		final String query = " DELETE FROM BookLog WHERE book_id = ? ";
		try {
			final PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, element.getBook_id());
			pstmt.executeUpdate();
			pstmt.close();
			//connection.close();
		} catch (final SQLException e) {
			System.out.println("[BookLogDaoJDBC] Errore nella delete: " + e.getMessage());
		}
	}

	@Override
	public BookLog getbyKey(int id) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT * FROM BookLog WHERE book_id = " + id;
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			BookOrderDaoJDBC bl = new BookOrderDaoJDBC();
			while (rs.next()) {
				Array array = rs.getArray("book_orders"); 
				Object[] object = (Object[]) array.getArray();
				List<BookOrder> bookorders = new LinkedList<BookOrder>(); 
				for (int i = 0; i < object.length; i++) {
					bookorders.add(bl.getbyKey((int)object[i]));
				}
				
				return new BookLog(rs.getInt("book_id"), bookorders );
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("[BookLogDaoJDBC] Errore nella getbykey: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<BookLog> findAll() {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT * FROM BookLog ";
		List<BookLog> result = new LinkedList<BookLog>(); 
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			BookOrderDaoJDBC bl = new BookOrderDaoJDBC();
			while (rs.next()) {
				Array array = rs.getArray("book_orders"); 
				Object[] object = (Object[]) array.getArray();
				List<BookOrder> bookorders = new LinkedList<BookOrder>(); 
				for (int i = 0; i < object.length; i++) {
					bookorders.add(bl.getbyKey((int)object[i]));
				}
				result.add(new BookLog(rs.getInt("book_id"), bookorders ));
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("[BookLogDaoJDBC] Errore nella findAll: " + e.getMessage());
		}
		return result;
	}

	@Override
	public void save(BookLog element) {
		final Connection conn = DatabaseManager.getConnection();
		List<BookOrder> book_orders = element.getOrders();
		String array = "{ ";
		if (book_orders.size()-1>0) {
			for (int i = 0; i<book_orders.size()-1; i++) {
				array+=book_orders.get(i).getBookOrder_id()+",";
			}
			array+=book_orders.get(book_orders.size()-1).getBookOrder_id(); 
		}
		array+=" }";
		final String query = "INSERT INTO BookLog (" + "book_id, book_orders) VALUES('"
				+ element.getBook_id() + "','"
				+array + "');";

		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (final SQLException e) {
			System.out.println("[BookLogDaoJDBC] Errore nella save: " + e.getMessage());
		}
	}

	/*
	 * Quando aggiungo un bookorder, per ogni libro presente nel bookorder devo aggiornare il relativo booklog
	 * bookorder -> getbookloans
	 * bookloans -> get book
	 */
	@Override
	public void update(BookOrder elementToAdd) {
		BookOrderDao bookorder_dao = new BookOrderDaoJDBC ();
		BookOrder bookorder = bookorder_dao.getbyKey(elementToAdd.getBookOrder_id());
		if (bookorder == null)
			System.out.println("bookorder null");
		else {
			//System.out.println("bookorder not null");
			List <BookLoan> bookloans = bookorder.getLoans();
			for (BookLoan bookloan : bookloans) 
				updateSingle(bookloan.getBook().getId(), elementToAdd.getBookOrder_id());
		}
		
	}
	
	private void updateSingle(int book, int bookorder) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "UPDATE BookLog\n"
				+ "SET book_orders = array_append(book_orders, ?)"
				+ "WHERE book_id = ?;";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, bookorder);
			stmt.setInt(2, book);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("[BookLogDaoJDBC] Errore nella update: " + e.getMessage());
		}
	}

}
