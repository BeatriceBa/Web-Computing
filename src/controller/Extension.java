package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.*;
import dao.jdbc.BookLoanDaoJDBC;
import dao.jdbc.UserDaoJDBC;
import database.DatabaseManager;
import model.BookLoan;
import model.User;
import utilities.SessionHandler;

/**
 * Servlet implementation class Extension
 */
@WebServlet("/Extension")
public class Extension extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	private User user = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Extension() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		
		int loanid = Integer.parseInt(request.getParameter("LoanId"));
		final DatabaseManager dm = DatabaseManager.getInstance();
		BookLoanDao bookLoanDao = new BookLoanDaoJDBC();
		BookLoan bookLoan = bookLoanDao.getbyKey(loanid);
		
		if (bookLoan.isExtendable()) {
			bookLoanDao.extend(loanid);
			out.append("OK");
			out.close();
		} 
		else {
			out.append("NO_PROROGA");
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
