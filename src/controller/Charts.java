package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import dao.*;
import dao.jdbc.*;
import database.DatabaseManager;
import javafx.util.Pair;
import model.*;

/**
 * Servlet implementation class Charts
 */
@WebServlet("/Charts")
public class Charts extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
    public Charts() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DatabaseManager db = DatabaseManager.getInstance();
		UserDao user_dao = new UserDaoJDBC();
		BookLoanDao bookloan_dao = new BookLoanDaoJDBC();
		List<User> total_users = user_dao.findAllNoAdmin();
		List<Pair<String,Integer>> loans_by_user = bookloan_dao.getNumberLoan();
		Gson gsonObj = new Gson();
        
		String arrearDataPoints = gsonObj.toJson(setArrearChart(total_users));
		String loanDataPoints = gsonObj.toJson(setLoanChart(loans_by_user));
		request.setAttribute("arrear", arrearDataPoints);
		request.setAttribute("loan", loanDataPoints);
		
		request.getRequestDispatcher("charts.jsp").forward(request, response);
	}

	private List<Map<String,String>> setArrearChart(List <User> total_users) {
		Map<String,String> map = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for (User current : total_users) {
			map = new HashMap<String,String>(); 
			map.put("label", current.getName() + current.getSurname()); 
			map.put("value", String.valueOf(current.getTotal_arrears())); 
			list.add(map);
		}
		return list;
	}
	
	private List<Map<String,String>> setLoanChart(List<Pair<String,Integer>> loans_by_user) {
		Map<String,String> map = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for (Pair<String,Integer> current : loans_by_user) {
			map = new HashMap<String,String>(); 
			map.put("label", current.getKey()); 
			map.put("value", String.valueOf(current.getValue())); 
			list.add(map);
		}
		return list;
	}

}
