package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.jdbc.BookLoanDaoJDBC;
import database.DatabaseManager;
import model.BookLoan;

/**
 * Servlet implementation class PayArrear
 */
@WebServlet("/PayArrear")
public class PayArrear extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PayArrear() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		DatabaseManager db = DatabaseManager.getInstance();

		int matricola = Integer.parseInt(request.getParameter("matricola"));

		BookLoanDaoJDBC bookloandao = new BookLoanDaoJDBC();
		List<BookLoan> bookloanlist = bookloandao.findCurrentByUser(matricola);
		request.setAttribute("bookLoanList", bookloanlist);
		request.getRequestDispatcher("showuserloans.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// doGet(request, response);
	}

}
