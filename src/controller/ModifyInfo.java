package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import dao.jdbc.BookDescriptionDaoJDBC;
import dao.jdbc.UserDaoJDBC;
import model.BookDescription;
import model.Category;
import model.User;
import model.UserType;

/**
 * Servlet implementation class ModifyInfo
 */
@WebServlet("/ModifyInfo")
public class ModifyInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("modifyinfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDescriptionDaoJDBC dao = new BookDescriptionDaoJDBC();
		PrintWriter out=response.getWriter();  
		if ( dao.getbyKey(request.getParameter("isbn")) == null) {
			out.append("NO");
			out.close();
		}
		else {
			BookDescription updating_bd = dao.getbyKey(request.getParameter("isbn")); 
			updating_bd.setTitle(request.getParameter("title"));
			updating_bd.setDescription(request.getParameter("description"));
			updating_bd.setAuthor(request.getParameter("author"));
			updating_bd.setCategory(new Category(request.getParameter("category")));
			if (dao.update(updating_bd)) {
				out.append("YES");
				out.close();
			}else {
				out.append("NO");
				out.close();
			}
		}
	}

}
