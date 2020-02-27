package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDescriptionDao;
import dao.jdbc.BookDescriptionDaoJDBC;
import database.DatabaseManager;
import model.BookDescription;
import utilities.SessionHandler;

/**
 * Servlet implementation class ShowBooks
 */
@WebServlet("/ShowBooks")
public class ShowBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowBooks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final DatabaseManager dm = DatabaseManager.getInstance();
		System.out.println("FUCK");
		BookDescriptionDao dao = new BookDescriptionDaoJDBC();
		List<BookDescription> bookDescriptions = dao.findAll();
		HashMap<String,Boolean> copies = dao.isThereABook();
		
		request.setAttribute("bookDescriptions", bookDescriptions);
		request.setAttribute("copies", copies);
		request.setAttribute("isAdmin", SessionHandler.isAdmin(request));
		
		HttpSession sessione = request.getSession(false);
		
		RequestDispatcher rd = request.getRequestDispatcher("/showbooks.jsp");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
