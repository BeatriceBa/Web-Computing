package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.jdbc.BookDescriptionDaoJDBC;

/**
 * Servlet implementation class AutoComplete
 */
@WebServlet("/AutoComplete")
public class AutoComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AutoComplete() {
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
		PrintWriter out = response.getWriter();
		JSONArray arrayObj = new JSONArray();
		BookDescriptionDaoJDBC dao = new BookDescriptionDaoJDBC();
		String query = request.getParameter("libro").toString();
		query = query.toLowerCase();
		List<String> titles = dao.getAllTitles();
		for (int i = 0; i < titles.size(); i++) {
			String title = titles.get(i).toLowerCase();
			if (title.contains(query)) {
				arrayObj.put(titles.get(i));
			}
		}

		out.println(arrayObj.toString());
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
