package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.BookDao;
import dao.BookDescriptionDao;
import dao.UserDao;
import dao.jdbc.BookDaoJDBC;
import dao.jdbc.BookDescriptionDaoJDBC;
import dao.jdbc.UserDaoJDBC;
import database.DatabaseManager;
import model.Book;
import model.BookDescription;
import model.User;
import utilities.SessionHandler;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Admin() {
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

		UserDao dao = new UserDaoJDBC();
		HttpSession session = request.getSession(false);
		User user = null;

		if (SessionHandler.isAdmin(request)) {
			user = dao.getbyKey(SessionHandler.getUserID(request));
			request.setAttribute("name", user.getName());
			request.setAttribute("surname", user.getSurname());
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DatabaseManager db = DatabaseManager.getInstance();
		BookDao book_dao = new BookDaoJDBC();
		BookDescriptionDao bookDescription_dao = new BookDescriptionDaoJDBC();
		UserDaoJDBC user_dao = new UserDaoJDBC();

		PrintWriter out = response.getWriter();

		if (request.getParameter("action").equals("payUserArrears")) {
			User user = user_dao.getbyKey(Integer.parseInt(request.getParameter("userid")));
		}

		if (request.getParameter("action").equals("findUser")) {
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");

			List<User> reg_users = user_dao.findAll();
			for (User u : reg_users) {
				if (u.getName().equals(name) && u.getSurname().equals(surname)) {
					out.print(u.getId());
					out.close();
				}
			}
		}

		if (request.getParameter("action").contentEquals("removeBook")) {
			String ISBN = request.getParameter("bookInfo");
			int copies = Integer.parseInt(request.getParameter("copies"));
			List<Book> book_list = book_dao.findAll();
			for (Book b : book_list) {
				if (b.getBookDescription().getISBN().equals(ISBN) && b.isAvailable() && copies > 0) {
					b.setAvailable(false);
					book_dao.update(b);
					copies--;
				}
				if (copies == 0) {
					out.print("OK");
					out.close();
					return;
				}

			}

		}

	}

}
