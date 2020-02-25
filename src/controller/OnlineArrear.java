package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.jdbc.BookLoanDaoJDBC;
import database.DatabaseManager;
import model.BookLoan;

/**
 * Servlet implementation class OnlineArrear
 */
@WebServlet("/OnlineArrear")
public class OnlineArrear extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OnlineArrear() {
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
		BookLoanDaoJDBC bookLoanDao = new BookLoanDaoJDBC();

		bookLoanDao.onlineArrearExceeded(4);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int loanid = Integer.parseInt(request.getParameter("ArrearLoanId"));

		final DatabaseManager dm = DatabaseManager.getInstance();
		BookLoanDaoJDBC bookLoanDao = new BookLoanDaoJDBC();

		bookLoanDao.onlineArrearExceeded(4);

	}

}
