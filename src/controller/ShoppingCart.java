package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import model.Book;
import model.BookDescription;
import model.BookLoan;
import model.BookOrder;
import model.Cart;
import model.User;
import utilities.MailHandler;
import utilities.SessionHandler;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class ShoppingCart extends HttpServlet {

	private User user = null;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShoppingCart() {
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

		HttpSession session = request.getSession(false);
		List<BookLoan> loans = new LinkedList<BookLoan>();
		UserDao user_dao = new UserDaoJDBC();
		BookDao book_dao = new BookDaoJDBC();
		BookLoanDao bookLoan_dao = new BookLoanDaoJDBC();
		BookOrderDao bookOrder_dao = new BookOrderDaoJDBC();
		BookDescriptionDao bookDescription_dao = new BookDescriptionDaoJDBC();
		Cart shoppingCart = new Cart();

		PrintWriter out = response.getWriter();

		if (SessionHandler.getUserID(request) == -1) {
			out.append("NO_USER");
			out.close();
			return;
		}

		if (SessionHandler.isAdmin(request) && request.getParameter("Action") == null) {
			response.sendRedirect("Profile");
			return;
		} else if (SessionHandler.isAdmin(request)) {
			out.append("IS_ADMIN");
			out.close();
			return;
		}

		if (session.getAttribute("primary_key") != null) {
			int id = (Integer) session.getAttribute("primary_key");
			if (user == null) {
				user = user_dao.getbyKey(id);
			}
		}

		passParameters(user, request);

		if (request.getParameter("Action") != null && request.getParameter("Action").equals("AddToCart")) {
			String isbn = request.getParameter("BookDescription");
			BookDescription bd = bookDescription_dao.getbyKey(isbn);
			List<Book> books = book_dao.findAll();
			for (Book b : books) {
				if (b.getBookDescription().getISBN().equals(bd.getISBN()) && b.isAvailable()) {

					if (isInCart(b, user)) {
						out.append("IN_CART");
						out.close();
						return;
					}

					if (user_dao.isMoroso(user)) {
						out.append("ARREAR");
						out.close();
						return;
					}

					if (isAlreadyTaken(b, user)) {
						out.append("CURRENT");
						out.close();
						return;
					}

					if (limitReached(user)) {
						out.append("LIMIT");
						out.close();
						return;
					}

					out.append("ADDED");
					out.close();
					user.getCart().add(b);
					passParameters(user, request);
					return;
				}
			}
			out.append("NO_ID");
			out.close();
			request.setAttribute("cart", user.getCart());
		}
		if (request.getParameter("Action") != null && request.getParameter("Action").equals("RemoveFromCart")) {
			String isbn = request.getParameter("BookDescription");
			BookDescription bd = bookDescription_dao.getbyKey(isbn);
			List<Book> books = user.getCart();
			Book lui = null;
			for (Book b : books) {
				if (b.getBookDescription().getISBN().equals(bd.getISBN())) {
					lui = b;
					break;
				}
			}
			if (lui != null) {
				books.remove(lui);
				request.setAttribute("cart", user.getCart());
			}
		}

		if (request.getParameter("Action") != null && request.getParameter("Action").equals("Checkout")
				&& user.getCart().isEmpty()) {
			out.append("EMPTY");
			out.close();
			return;
		}

		if (request.getParameter("Action") != null && request.getParameter("Action").equals("Checkout")) {
			String email_message = "Gentile " + user.getName()
					+ ", e' stato confermato il prestito per i seguenti libri: \n";
			BookLogDao booklog_dao = new BookLogDaoJDBC();
			BookOrderDao bookorder_dao = new BookOrderDaoJDBC();
			for (Book b : user.getCart()) {
				LocalDate actual = LocalDate.now();
				LocalDate afterWeek = actual.plusWeeks(1);
				BookLoan bl = new BookLoan(java.sql.Date.valueOf(actual), java.sql.Date.valueOf(afterWeek), true, 0, b,
						false, user.getId());
				bookLoan_dao.save(bl);
				bl.setId(bookLoan_dao.getLastBookLoan());
				loans.add(bl);
				email_message += b.getBookDescription().getTitle() + " di " + b.getBookDescription().getAuthor()
						+ " da restituire il: " + bl.getReturn_date().toString() + "\n";
			}
			user.getCart().clear();
			BookOrder bo = new BookOrder(bookorder_dao.getLastBookLoan() + 1, loans, user.getId());
			bookOrder_dao.save(bo);
			booklog_dao.update(bo);
			MailHandler.send(user, "Conferma Prestito Libri", email_message + "\nSaluti, Biblioteca INGSW");
		}
		
		request.getRequestDispatcher("cart.jsp").include(request, response);

	}

	private void passParameters(User user, HttpServletRequest request) {
		request.setAttribute("cart", user.getCart());
	}

	private boolean isInCart(Book book, User user) {
		for (Book userBook : user.getCart()) {
			if (userBook.getBookDescription().getISBN().equals(book.getBookDescription().getISBN()))
				return true;
		}
		return false;
	}

	private boolean limitReached(User user) {
		UserDao user_dao = new UserDaoJDBC();
		if (user.getCart().size() + user_dao.getBookCount(user) + 1 >= 6)
			return true;
		return false;
	}

	private boolean isAlreadyTaken(Book book, User user) {
		BookLoanDao bookloan_dao = new BookLoanDaoJDBC();
		List<BookLoan> taken = bookloan_dao.findCurrentByUser(user.getId());
		for (BookLoan userBook : taken) {
			if (userBook.getBook().getBookDescription().getISBN().equals(book.getBookDescription().getISBN()))
				return true;
		}
		return false;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}