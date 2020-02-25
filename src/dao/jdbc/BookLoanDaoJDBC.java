package dao.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import dao.BookLoanDao;
import database.DatabaseManager;
import javafx.util.Pair;
import model.BookLoan;
import model.User;
import model.Book;
import model.BookDescription;

public class BookLoanDaoJDBC implements BookLoanDao {

	@Override
	public void delete(BookLoan book_loan) {
		final Connection connection = DatabaseManager.getConnection();
		final String query = " DELETE FROM BookLoan WHERE id = ? ";
		try {
			final PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, book_loan.getId());
			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (final SQLException e) {
			System.out.println("[BookLoanDaoJDBC] Errore nella delete: " + e.getMessage());
		}
	}

	@Override
	public List<BookLoan> findAll() {

		final Connection conn = DatabaseManager.getConnection();
		List<BookLoan> bookLoans = new LinkedList<BookLoan>();
		final String query = "SELECT * FROM BookLoan";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			BookDaoJDBC bl = new BookDaoJDBC();
			while (rs.next()) {

				BookLoan bol = new BookLoan(rs.getInt("id"), rs.getDate("loan_date"), rs.getDate("return_date"),
						rs.getBoolean("extendable"), rs.getFloat("arrears"), bl.getbyKey(rs.getInt("book")),
						rs.getBoolean("returned"), rs.getInt("user_id"));
				bol.setArrear_payed(rs.getBoolean("arrear_payed"));
				bookLoans.add(bol);
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("[BookLoanDaoJDBC ] Errore nella findAll: " + e.getMessage());
		}
		return bookLoans;
	}

	@Override
	public void save(BookLoan book_loan) {
		final Connection conn = DatabaseManager.getConnection();
		UserDaoJDBC user = new UserDaoJDBC();
		if (user.getbyKey(book_loan.getUser_id()).getBook_count() == 5) {

		} else if (user.isMoroso(user.getbyKey(book_loan.getUser_id()))) {

		} else {
			final String query = "INSERT INTO BookLoan ("
					+ "loan_date, return_date, extendable, arrears, book, returned, user_id, arrear_payed ) VALUES('"
					+ book_loan.getLoan_date() + "','" + book_loan.getReturn_date() + "','" + book_loan.isExtendable()
					+ "','" + book_loan.getArrears() + "','" + book_loan.getBook().getId() + "','"
					+ book_loan.isReturned() + "','" + book_loan.getUser_id() + "','" + "false');";
			try {
				final PreparedStatement stmt = conn.prepareStatement(query);
				stmt.executeUpdate();
				stmt.close();
				//conn.close();
			} catch (final SQLException e) {
				System.out.println("[BookLoanDaoJDBC] Errore nella save: " + e.getMessage());
			}
		}
	}

	@Override
	public void update(BookLoan book_loan) {
		// TODO Auto-generated method stub

	}

	@Override
	public BookLoan getbyKey(int id) {
		final Connection conn = DatabaseManager.getConnection();
		final String query = "SELECT * FROM BookLoan WHERE id = " + id;
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			BookDaoJDBC bl = new BookDaoJDBC();
			while (rs.next()) {
				BookLoan bol = new BookLoan(rs.getInt("id"), rs.getDate("loan_date"), rs.getDate("return_date"),
						rs.getBoolean("extendable"), rs.getFloat("arrears"), bl.getbyKey(rs.getInt("book")),
						rs.getBoolean("returned"), rs.getInt("user_id"));
				bol.setArrear_payed(rs.getBoolean("arrear_payed"));
				return bol;
			}
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			System.out.println("[BookLoanDaoJDBC] Errore nella getbyKey: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<BookLoan> findCurrentByUser(int id) {
		final Connection conn = DatabaseManager.getConnection();
		List<BookLoan> bookLoans = new LinkedList<BookLoan>();
		final String query = "SELECT * FROM BookLoan WHERE user_id = ? AND returned = false ";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			BookDaoJDBC bl = new BookDaoJDBC();
			while (rs.next()) {
				BookLoan bol = new BookLoan(rs.getInt("id"), rs.getDate("loan_date"), rs.getDate("return_date"),
						rs.getBoolean("extendable"), rs.getFloat("arrears"), bl.getbyKey(rs.getInt("book")),
						rs.getBoolean("returned"), rs.getInt("user_id"));
				// System.out.println(bol.toString());
				bol.setArrear_payed(rs.getBoolean("arrear_payed"));
				bookLoans.add(bol);
			}
			stmt.close();
			//conn.close();
			return bookLoans;
		} catch (SQLException e) {
			System.out.println("[BookLoanDaoJDBC] Errore nella getbyKey: " + e.getMessage());
		}
		return bookLoans;
	}

	@Override
	public float getTotalArrearsbyUser(User u) {
		float sum = 0;
		final String query = " SELECT arrears FROM BookLoan WHERE user_id = ? ";
		final Connection connection = DatabaseManager.getConnection();
		try {
			final PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, u.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getFloat("arrears") > 0) {
					sum += rs.getFloat("arrears");
				}
			}
			pstmt.close();
			connection.close();
		} catch (final SQLException e) {
			System.out.println("[BookLoanDaoJDBC] Errore nella getTotalArrearbyUser: " + e.getMessage());
		}
		return sum;
	}

	@Override
	public int getLastBookLoan() {
		final Connection conn = DatabaseManager.getConnection();
		try {
			int max = 0;
			final Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(id) FROM BookLoan");
			ResultSet rs = s2.getResultSet();

			while (rs.next()) {
				max = rs.getInt(1);

			}
			s2.close();
			//conn.close();
			return max;
		} catch (SQLException e) {
			System.out.println("[BookLoanDaoJDBC] Errore nella getbyKey: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public void returnBook(int id) {
		final Connection conn = DatabaseManager.getConnection();
		String query = "UPDATE BookLoan SET returned = true WHERE id = ? ";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void payArrear(int id) {
		final Connection conn = DatabaseManager.getConnection();
		String query = "UPDATE BookLoan SET arrears = 0, returned = true WHERE id = ? ";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void extend(int id) {
		final Connection conn = DatabaseManager.getConnection();
		String query = "UPDATE BookLoan SET extendable = false, return_date = ? WHERE id = ? AND extendable = true;";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);

			java.util.Date newDate = this.getbyKey(id).getReturn_date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(newDate);
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			java.util.Date afterWeek = calendar.getTime();

			java.sql.Date param = new java.sql.Date(afterWeek.getTime());

			stmt.setDate(1, param);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setArrears(int id) {
		final Connection conn = DatabaseManager.getConnection();
		String query = "UPDATE BookLoan SET arrears = ? WHERE id = ?;";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);

			java.util.Date newDate = this.getbyKey(id).getReturn_date();

			stmt.setDouble(1, adjustArrear(newDate));
			stmt.setInt(2, id);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onlineArrearExceeded(int id) {
		final Connection conn = DatabaseManager.getConnection();
		String query = "UPDATE BookLoan SET return_date = ?, arrears = ? WHERE id = ? ";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			java.util.Date newDate = this.getbyKey(id).getReturn_date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(newDate);
			calendar.add(Calendar.DAY_OF_YEAR, -7);
			java.util.Date afterWeek = calendar.getTime();

			java.sql.Date param = new java.sql.Date(afterWeek.getTime());
			// Data riportata ad una settimana prima
			stmt.setDate(1, param);
			// Calcola la mora-> (data attuale - data restituzione(param))*3.0 aka 3 euro al
			// giorno, da aggiustare poi
			Double newArrear = adjustArrear(param);
			System.out.println("new arrear is " + newArrear);
			stmt.setDouble(2, newArrear);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public float calculateDays(java.util.Date begin, java.util.Date end) {
		long difference = end.getTime() - begin.getTime();
		float daysBetween = (difference / (1000 * 60 * 60 * 24));
		return daysBetween;
	}

	@Override
	public Double adjustArrear(java.util.Date restitutionDate) {
		// creating the instance of LocalDate using the day, month, year info
		LocalDate localDate = LocalDate.now();
		java.util.Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		float difference = calculateDays(restitutionDate, currentDate);
		return difference * 1.0;
	}

	@Override
	public void onlineArrearPayed(int id) {

		// Se l'utente ha pagato la mora online
		// la mora viene impostata a 0
		// la data di restituzione prorogata di 7 giorni
		// e non puo essere esteso il prestito.

		final Connection conn = DatabaseManager.getConnection();
		String query = "UPDATE BookLoan SET extendable = false, return_date = ?, arrears = 0 WHERE id = ?;";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);

			java.util.Date newDate = this.getbyKey(id).getReturn_date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(newDate);
			calendar.add(Calendar.DAY_OF_YEAR, 7);
			java.util.Date afterWeek = calendar.getTime();

			java.sql.Date param = new java.sql.Date(afterWeek.getTime());

			stmt.setDate(1, param);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			stmt.close();
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public List<Pair<String,Integer>> getNumberLoan() {
		List<Pair<String,Integer>> result = new LinkedList <Pair<String,Integer>>();
		final Connection conn = DatabaseManager.getConnection();
		String query = "SELECT bookloan.user_id, COUNT (*), user_data.name, user_data.surname "
					+ "FROM bookloan "
					+ "INNER JOIN user_data ON (bookloan.user_id = user_data.id) "
					+ "GROUP BY bookloan.user_id, user_data.name, user_data.surname;";
		try {
			final PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new Pair<String,Integer>(rs.getString("name") + rs.getString("surname"),
													 rs.getInt("count")));
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("[BookLoanDaoJDBC] Errore nella getNumberLoan: " + e.getMessage());
		}
		return result;
	}
}
