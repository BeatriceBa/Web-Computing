package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.jdbc.BookDaoJDBC;
import dao.jdbc.BookDescriptionDaoJDBC;
import database.DatabaseManager;
import model.Book;
import model.BookDescription;

/**
 * Servlet implementation class SearchBook
 */
@WebServlet("/SearchBook")
public class SearchBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchBook() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final DatabaseManager dm = DatabaseManager.getInstance();
		BookDescriptionDaoJDBC bod = new BookDescriptionDaoJDBC(); 
		BookDescription book = bod.getbyKey(request.getParameter("libro")); 
		request.setAttribute("bookSearched",book); 
		request.setAttribute("searchedFor", request.getParameter("libro"));
		request.getRequestDispatcher("searchbook.jsp").forward(request, response);
		doGet(request,response);
	}

}
