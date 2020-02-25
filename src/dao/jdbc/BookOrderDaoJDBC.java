package dao.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import dao.BookOrderDao;
import database.DatabaseManager;
import model.BookDescription;
import model.BookLoan;
import model.BookOrder;

public class BookOrderDaoJDBC implements BookOrderDao{

	@Override
	public void delete(BookOrder element) {
		// TODO Auto-generated method stub

	}

	@Override
	public BookOrder getbyKey(int id) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT * FROM BookOrder WHERE book_id = " + id;
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			BookLoanDaoJDBC bl = new BookLoanDaoJDBC();
			while (rs.next()) {
				Array array = rs.getArray("book_loans"); 
				Object[] object = (Object[]) array.getArray();
				List<BookLoan> booksloan = new LinkedList<BookLoan>(); 
				for (int i = 0; i < object.length; i++) {
					booksloan.add(bl.getbyKey((int)object[i]));
				}
				return new BookOrder(rs.getInt("book_id"),booksloan,rs.getInt("user_id"));
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("[BookOrderDaoJDBC ] Errore nella getbyKey: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<BookOrder> findAll() {
		final Connection conn = DatabaseManager.getConnection();
		final List<BookOrder> results = new LinkedList<BookOrder>();
		final String query = "SELECT * FROM BookOrder ";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			BookLoanDaoJDBC bl = new BookLoanDaoJDBC();
			while (rs.next()) {
				Array array = rs.getArray("book_loans"); 
				Object[] object = (Object[]) array.getArray();
				List<BookLoan> booksloan = new LinkedList<BookLoan>(); 
				for (int i = 0; i < object.length; i++) {
					booksloan.add(bl.getbyKey((int)object[i]));
				}
				results.add(new BookOrder(rs.getInt("book_id"),booksloan,rs.getInt("user_id")));
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("[BookOrderDaoJDBC ] Errore nella findAll: " + e.getMessage());
		}
		return results;
	}

	@Override
	public void save(BookOrder element) {
		final Connection conn = DatabaseManager.getConnection();
		List<BookLoan> book_loan = element.getLoans();
		String array = "ARRAY[";

		for (int i = 0; i<book_loan.size(); i++) {
			if(i != book_loan.size()-1) {

				array+="'"+book_loan.get(i).getId()+"'::INTEGER,";
			}
			else {
				System.out.println("Stampa importante" + book_loan.get(book_loan.size()-1).getId());
				array+="'"+book_loan.get(book_loan.size()-1).getId()+"'::INTEGER";
			}
		}

		array+="]";

		final String query = "INSERT INTO BookOrder (" + "book_loans,user_id) VALUES("
				+array + ",'" 
				+ element.getUser_id()+ "');";

		System.out.println("stampa finale : " + query);

		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (final SQLException e) {
			System.out.println("[BookOrderDaoJDBC] Errore nella save: " + e.getMessage());
		}

	}
	
	@Override
	public int getLastBookLoan() {
		final Connection conn = DatabaseManager.getConnection();
		try {
			int max = 0;
			final Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(book_id) FROM BookOrder");
			ResultSet rs = s2.getResultSet();

			while (rs.next()) {
				max = rs.getInt(1);

			}
			s2.close();
			//conn.close();
			return max;
		} catch (SQLException e) {
			System.out.println("[BookOrderDaoJDBC] Errore nella getbyKey: " + e.getMessage());
		}
		return 0;
	}
	

	@Override
	public void update(BookOrder element) {
		// TODO Auto-generated method stub

	}

}
