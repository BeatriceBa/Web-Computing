package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BookDao;
import dao.BookDescriptionDao;
import dao.BookLoanDao;
import dao.BookLogDao;
import dao.BookOrderDao;
import dao.UserDao;
import dao.jdbc.BookDaoJDBC;
import dao.jdbc.BookDescriptionDaoJDBC;
import dao.jdbc.BookLoanDaoJDBC;
import dao.jdbc.BookLogDaoJDBC;
import dao.jdbc.BookOrderDaoJDBC;
import dao.jdbc.UserDaoJDBC;
import database.DatabaseManager;
import model.Book;
import model.BookDescription;
import model.Category;
import utilities.MailHandler;

/**
 * Servlet implementation class TestingServlet
 */
@WebServlet("/TestingServlet")
public class TestingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestingServlet() {
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
		

		final PrintWriter out = response.getWriter();
		final DatabaseManager dm = DatabaseManager.getInstance();
		dm.DatabaseInit();
//		UserDaoJDBC daoUser = new UserDaoJDBC(); 
//		BookOrderDaoJDBC dao_Order = new BookOrderDaoJDBC();
//		BookDescriptionDaoJDBC dao_Description = new BookDescriptionDaoJDBC();
//
//
//		UserDao dao = new UserDaoJDBC();
//		BookDao daoBook = new BookDaoJDBC(); 
//		BookLoanDao daoLoan = new BookLoanDaoJDBC(); 
//		BookOrderDao daoOrder = new BookOrderDaoJDBC(); 
//		BookLogDao daoLog = new BookLogDaoJDBC(); 
//		BookDescriptionDao daoDesc = new BookDescriptionDaoJDBC(); 
//
//		Date date = null; 
//		Date date2 = null; 
//		try {
//			date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-22");
//			date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-10");
//		} catch (ParseException e1) {
//
//			e1.printStackTrace();
//		}
//
//		try {
//
//		} catch (final Exception e) {
//			response.getWriter().append("Errore nella query: " + e.toString());
//			System.out.println(e.getLocalizedMessage());
//		}
		BookDescriptionDao dao = new BookDescriptionDaoJDBC();
		BookDao dao_book = new BookDaoJDBC();

		HashMap<String,Boolean> result = dao.isThereABook();
		List<Book> books = dao_book.findAll();
		
		for (Map.Entry<String, Boolean> book : result.entrySet()) {
			System.out.println(book.getKey() + "->" + book.getValue());
		}
		
		BookDescription bd1 = new BookDescription("ISBN", 
				"author", 
				"title", 
				"publishingHouse", 
				new Date(),
				new Category("Admin"), 
				"description", 
				"imageUrl");
		
		BookDescription bd2 = new BookDescription("ISBN", 
				"author", 
				"title", 
				"publishingHouse", 
				new Date(),
				new Category("Admin"), 
				"description", 
				"imageUrl");
	}


}
