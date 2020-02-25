package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.BookDescriptionDao;
import database.DatabaseManager;
import model.BookDescription;
import model.Category;

public class BookDescriptionDaoJDBC implements BookDescriptionDao {

	@Override
	public void delete(BookDescription bookDescription) {
		final Connection connection = DatabaseManager.getConnection();
		final String query = " DELETE FROM BookDescription WHERE isbn = ? ";
		try {
			final PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, bookDescription.getISBN());
			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (final SQLException e) {
			System.out.println("[BookDescriptionDaoJDBC] Errore nella delete: " + e.getMessage());
		}
	}

	@Override
	public List<BookDescription> findAll() {
		final List<BookDescription> results = new LinkedList<BookDescription>();
		final Connection conn = DatabaseManager.getConnection();
		if (conn == null) {
			System.out.println("conn is null");
		}
		final String query = "SELECT * FROM BookDescription";
		try {
			final PreparedStatement pstmt = conn.prepareStatement(query);
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				results.add(new BookDescription(rs.getString("isbn"), rs.getString("author"), rs.getString("title"),
						rs.getString("publishing_house"), rs.getDate("year"),
						new Category(rs.getString("category")), rs.getString("description"), rs.getString("imageUrl")));
			}
			pstmt.close();
			//conn.close();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("[BookDescriptionDAOJDBC] Errore nella query: " + e.getLocalizedMessage());
		} 

		return results;
	}

	@Override
	public void save(BookDescription bookDescription) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "INSERT INTO BookDescription (isbn, author, title, publishing_house, year, category, description, imageUrl)"
				+ "VALUES(?,?,?,?,?,?,?,?)";

		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, bookDescription.getISBN());
			stmt.setString(2, bookDescription.getAuthor());
			stmt.setString(3, bookDescription.getTitle());
			stmt.setString(4, bookDescription.getPublishingHouse());
			stmt.setDate(5, new java.sql.Date(bookDescription.getYear().getTime()));
			stmt.setString(6, bookDescription.getCategory().getName());
			stmt.setString(7, bookDescription.getDescription());
			stmt.setString(8, bookDescription.getImageUrl());
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (final SQLException e) {
			System.out.println("[BookDescriptionDaoJDBC] Errore nella save: " + e.getMessage());
		}
	}

	@Override
	public boolean update(BookDescription bookDescription) {
		final Connection conn = DatabaseManager.getConnection(); 
		String query = "UPDATE BookDescription SET author = ? , title = ? , publishing_house = ? , "
				+ "year = ? , category = ? , description = ? , imageUrl = ?"
				+ "WHERE isbn = ? ";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, bookDescription.getAuthor());
			stmt.setString(2, bookDescription.getTitle());
			stmt.setString(3, bookDescription.getPublishingHouse());
			stmt.setDate(4, (java.sql.Date) bookDescription.getYear());
			stmt.setString(5, bookDescription.getCategory().getName());
			stmt.setString(6, bookDescription.getDescription());
			stmt.setString(7, bookDescription.getImageUrl());
			stmt.setString(8, bookDescription.getISBN());
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
			return true; 

		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		} 
	}

	@Override

	public BookDescription getbyKey(String id) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT * FROM BookDescription WHERE isbn = ? "; 

		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				return new BookDescription(rs.getString("isbn"),
						rs.getString("author"),
						rs.getString("title"),
						rs.getString("publishing_house"),
						rs.getDate("year"),
						new Category(rs.getString("category")),
						rs.getString("description"),
						rs.getString("imageUrl"));
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public LinkedList<BookDescription> getbyTitle(String title) {
		
		title = title.toLowerCase(); 
		LinkedList<BookDescription> allBooks=(LinkedList<BookDescription>) this.findAll(); 
		LinkedList<BookDescription> bookD= new LinkedList<BookDescription>(); 
		
		for (BookDescription book: allBooks) {
			String bookTitle = book.getTitle().toLowerCase(); 
			if (bookTitle.matches("(.*)("+title+")(.*)")) {
				bookD.add(book); 
			}
		}
		return bookD; 
	}

	@Override
	public List<String> getAllTitles() {
		List<String> titles = new ArrayList<String>(); 
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT title FROM BookDescription"; 
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				titles.add(rs.getString("title")); 
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return titles;
	}

	@Override
	public List<String> getTitles(String query){
		String title = null;
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		List<String> allTitles = getAllTitles();
		for(int i=0; i<allTitles.size(); i++) {
			title = allTitles.get(i).toLowerCase();
			if(title.startsWith(query)) {
				matched.add(allTitles.get(i));
			}
		}
		return matched;
	}
	
	@Override
	public HashMap<String,Boolean> isThereABook() {
		HashMap<String,Boolean> result = new HashMap <String,Boolean>();
		final Connection conn = DatabaseManager.getConnection();
		String query = "SELECT *  "
					+ "FROM BookDescription "
					+ "LEFT JOIN Book ON (BookDescription.isbn = Book.book_description)";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
//				BookDescription tmp = new BookDescription(rs.getString("isbn"), 
//							rs.getString("author"), 
//							rs.getString("title"),
//							rs.getString("publishing_house"), 
//							rs.getDate("year"),
//							new Category(rs.getString("category")), 
//							rs.getString("description"), 
//							rs.getString("imageUrl"));
				
				Boolean bool = false;
				if ( rs.getObject("id") != null ) {
					bool = true;
				}
				result.put(rs.getString("isbn"), bool);
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("[BookLoanDaoJDBC] Errore nella getNumberLoan: " + e.getMessage());
		}
		return result;
	}
}
