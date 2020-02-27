package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDao;
import dao.BookDescriptionDao;
import dao.jdbc.BookDaoJDBC;
import dao.jdbc.BookDescriptionDaoJDBC;
import model.Book;
import model.BookDescription;
import model.Category;

/**
 * Servlet implementation class ShowResults
 */
@WebServlet("/ShowResults")
public class ShowResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowResults() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String term = (String) request.getParameter("adding");	
		BookDescription b = new BookDescription();
		ArrayList<BookDescription> result = b.wantToAdd(term);
		
		HttpSession sessione = request.getSession(false);
		
		request.setAttribute("term", term);
		request.setAttribute("result", result);
		
		request.getRequestDispatcher("showresults.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		if (request.getParameter("action").equals("addBook") && 
				!request.getParameter("copies").equals("") &&
				Integer.parseInt(request.getParameter("copies")) > 0) {
			System.out.println("WEWE");
			PrintWriter out = response.getWriter();
			BookDao book_dao = new BookDaoJDBC();
			BookDescriptionDao bookDescription_dao = new BookDescriptionDaoJDBC();
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			BookDescription bd = new BookDescription(); 
			
			if (bookDescription_dao.getbyKey(request.getParameter("isbn")) == null){
				bd.setISBN(request.getParameter("isbn"));
				bd.setDescription(request.getParameter("description"));
				try {
					
				  	String date=request.getParameter("date"); 
				    SimpleDateFormat sdf=new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy",Locale.UK);
				    Date currentdate;
				    currentdate=sdf.parse(date);
				    
				    Date newdate = format.parse(Integer.toString(1900+currentdate.getYear()));   
				    bd.setYear(newdate);
				    
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				bd.setAuthor(request.getParameter("author"));
				bd.setTitle(request.getParameter("title"));
				
				bd.setImageUrl(request.getParameter("imageUrl"));
				bd.setCategory(new Category(request.getParameter("category")));
				bd.setPublishingHouse(request.getParameter("publishingHouse"));
				bookDescription_dao.save(bd);
			}
			else {
				bd=bookDescription_dao.getbyKey(request.getParameter("isbn"));
			}
			for (int i = 0; i < Integer.parseInt(request.getParameter("copies")); i++) {
				Book copy = new Book(bd, true);
				book_dao.save(copy);
			}
			System.out.println(request.getParameter("title"));
			out.print("OK");
			out.close();
		}
	}

}
