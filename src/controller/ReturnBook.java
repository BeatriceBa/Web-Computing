package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.jdbc.BookLoanDaoJDBC;
import model.BookLoan;

/**
 * Servlet implementation class ReturnBook
 */
@WebServlet("/ReturnBook")
public class ReturnBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReturnBook() {
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
		BookLoanDaoJDBC bookLoanDao = new BookLoanDaoJDBC();	
		int id = Integer.parseInt(request.getParameter("bookloanid"));
		bookLoanDao.returnBook(id);
		int matricola = bookLoanDao.getbyKey(id).getUser_id(); 
		List<BookLoan> bookloanlist = bookLoanDao.findCurrentByUser(matricola);
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
		doGet(request, response);
	}

}
