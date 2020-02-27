package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BookLoanDao;
import dao.UserDao;
import dao.jdbc.BookLoanDaoJDBC;
import dao.jdbc.UserDaoJDBC;
import database.DatabaseManager;
import model.BookLoan;
import model.User;
import utilities.SessionHandler;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final DatabaseManager dm = DatabaseManager.getInstance();
		UserDao dao = new UserDaoJDBC();
		BookLoanDao bookLoanDao = new BookLoanDaoJDBC();

		List<BookLoan> everyLoan = bookLoanDao.findAll();
		List<BookLoan> returnedLoans = new LinkedList<BookLoan>();
		List<BookLoan> currentLoans = new LinkedList<BookLoan>();

		if (SessionHandler.isAdmin(request)) {
			request.getRequestDispatcher("Admin").forward(request, response);
			return;
		}

		// ----Have access to current user:----
		if (SessionHandler.getUserID(request) != -1) {
			User user = dao.getbyKey(SessionHandler.getUserID(request));
			boolean isMoroso = dao.isMoroso(user);
			for (BookLoan b : everyLoan) {
				if (b.isReturned() && b.getUser_id() == user.getId())
					returnedLoans.add(b);
				else if (!b.isReturned() && b.getUser_id() == user.getId()) 
					currentLoans.add(b);
			}
			
			request.setAttribute("user", user);
			request.setAttribute("everyLoan", everyLoan);
			request.setAttribute("returnedLoans", returnedLoans);
			request.setAttribute("currentLoans", currentLoans);
			request.setAttribute("isMoroso", isMoroso);
			request.setAttribute("totalArrearsPerUser", user.getTotal_arrears());
			request.getRequestDispatcher("profile.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/").forward(request, response);
		}
	}

	private int getUsersBook(User user) {
		BookLoanDao book_loan_dao = new BookLoanDaoJDBC();
		List<BookLoan> lista = book_loan_dao.findCurrentByUser(user.getId());
		return lista.size();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
