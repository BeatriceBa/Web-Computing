package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.jdbc.BookDescriptionDaoJDBC;
import model.BookDescription;
import utilities.SessionHandler;

/**
 * Servlet implementation class BookInfo
 */
@WebServlet("/BookInfo")
public class BookInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookInfo() {
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
		String isbn = request.getParameter("isbn");
		HttpSession sessione = request.getSession(false);
		BookDescriptionDaoJDBC bd = new BookDescriptionDaoJDBC();
		BookDescription bookd = bd.getbyKey(isbn);
		request.setAttribute("title", bookd.getTitle());
		request.setAttribute("author", bookd.getAuthor());
		request.setAttribute("description", bookd.getDescription());
		request.setAttribute("isbn", bookd.getISBN());
		request.setAttribute("image", bookd.getImageUrl());
		request.setAttribute("date", bookd.getYear());
		request.setAttribute("category", bookd.getCategory().getName());
		request.setAttribute("isAdmin", SessionHandler.isAdmin(request));
		request.getRequestDispatcher("bookinfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
