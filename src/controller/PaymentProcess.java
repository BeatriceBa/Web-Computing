package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import dao.jdbc.BookDaoJDBC;
import dao.jdbc.BookLoanDaoJDBC;
import dao.jdbc.UserDaoJDBC;
import model.Book;
import model.BookDescription;
import model.BookLoan;

/**
 * Servlet implementation class PaymentProcess
 */
@WebServlet("/PaymentProcess")
public class PaymentProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaymentProcess() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idLoan = Integer.parseInt(request.getParameter("LoanId"));
		BookLoanDaoJDBC bookLoanDao = new BookLoanDaoJDBC();
		bookLoanDao.payArrear(idLoan);
		BookLoan loan = bookLoanDao.getbyKey(idLoan);
		response.sendRedirect("PayArrear?matricola=" + loan.getUser_id());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		Stripe.apiKey = "sk_test_Brv34e14K8CO6t4ZD1fqoRx300QhU7CHI5";

		BookLoanDaoJDBC bl = new BookLoanDaoJDBC();
		BookLoan b = bl.getbyKey(Integer.parseInt(request.getParameter("id")));
		Map<String, String[]> httpParameters = request.getParameterMap();

		Map<String, Object> params = new HashMap<>();
		params.put("amount", (int) b.getArrears() * 100); // or get from request
		params.put("currency", "eur"); // or get from request
		params.put("source", httpParameters.get("stripeToken")[0]);// or get from request
		params.put("receipt_email", httpParameters.get("stripeEmail")[0]);

		Charge charge;

		try {
			charge = Charge.create(params);
			String chargeID = charge.getId();
			out.println("CHARGE OK! " + chargeID);
			bl.onlineArrearPayed((Integer.parseInt(request.getParameter("id"))));
			request.getRequestDispatcher("Profile").forward(request, response);

		} catch (StripeException e) {
			request.getRequestDispatcher("Profile").forward(request, response);
		}

	}
}
