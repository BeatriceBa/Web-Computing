package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.UserDao;
import dao.jdbc.UserDaoJDBC;
import database.DatabaseManager;
import model.User;
import model.UserType;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final DatabaseManager dm = DatabaseManager.getInstance();

		UserDao dao = new UserDaoJDBC();

		PrintWriter out = response.getWriter();  
		int id = Integer.parseInt((request.getParameter("id")));
		
		User user = dao.getbyKey(id);
		
		if(user != null) {
			out.append("NO");
			out.close();
		} else {
			User registering_user = new User(
					Integer.parseInt(request.getParameter("id")),
					request.getParameter("name"),
					request.getParameter("surname"),
					request.getParameter("address"),
					request.getParameter("email"),
					request.getParameter("password"),
					UserType.fromString(request.getParameter("userType")),
					0,
					0); 
			dao.save(registering_user);
			out.append("OK");
			HttpSession session=request.getSession();
			session.setAttribute("primary_key",registering_user.getId());
			out.close();
		}
	}

}
