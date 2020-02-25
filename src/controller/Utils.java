package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.BookDescriptionDao;
import dao.jdbc.BookDescriptionDaoJDBC;
import model.BookDescription;
import utilities.SessionHandler;

/**
 * Servlet implementation class Utils
 */
@WebServlet("/Utils")
public class Utils extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Utils() {
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

		if (request.getParameter("action").equals("imageCacheRefresh")) {

			if (!SessionHandler.isAdmin(request))
				return;

			BookDescriptionDao bookDescription_dao = new BookDescriptionDaoJDBC();
			List<BookDescription> books = bookDescription_dao.findAll();
			URL url = null;

			for (BookDescription b : books) {
				if (b.getImageUrl().equals("/noimage.jpg")) {
					String thumb_url = null;
					BufferedImage image = null;
					url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + b.getISBN()
					+ "&key=AIzaSyDGBd3sgZ2CZGzgzeI_gDhBJPQRdMGY1vU");
					String resp = new String();
					URLConnection yc = url.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
					String inputLine;
					JSONObject firstItem = null;
					while ((inputLine = in.readLine()) != null) {
						resp += inputLine;
					}
					try {

						JSONObject obj = new JSONObject(resp);
						if (obj.has("items")) {
							thumb_url = obj.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo")
									.getJSONObject("imageLinks").getString("thumbnail");
							//thumb_url = thumb_url.replaceAll("(zoom=\\d)", "zoom=1");
							//System.out.println(thumb_url);
							b.setImageUrl(thumb_url);
							bookDescription_dao.update(b);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		}

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
