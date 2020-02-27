package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.jdbc.BookDaoJDBC;
import dao.jdbc.BookDescriptionDaoJDBC;
import database.DatabaseManager;
import model.Book;
import model.BookDescription;
import utilities.SessionHandler;

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
		HttpSession sessione = request.getSession(false);

		BookDescriptionDaoJDBC bod = new BookDescriptionDaoJDBC(); 
		BookDescription book = bod.getbyKey(request.getParameter("libro")); 
		LinkedList<BookDescription> bookD = bod.getbyTitle(request.getParameter("libro"));
		HashMap<String,Boolean> copies = bod.isThereABook();
		
		request.setAttribute("bookSearched",book); 
		request.setAttribute("searchedFor", request.getParameter("libro"));
		request.setAttribute("result", bookD);
		request.setAttribute("copies", copies);
		request.setAttribute("isAdmin", SessionHandler.isAdmin(request));
		request.getRequestDispatcher("searchbook.jsp").forward(request, response);
		
		doGet(request,response);
	}

}
