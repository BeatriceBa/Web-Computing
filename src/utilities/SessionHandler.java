package utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import dao.UserDao;
import dao.jdbc.UserDaoJDBC;
import model.User;
import model.UserType;

public class SessionHandler {

	// getUserID - Ritorna l'id dell'utente, in caso non è autenticato ritorna -1
	public static int getUserID(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		User user = null;
		UserDao dao = new UserDaoJDBC();

		if (session == null) {
			return (-1);
		}

		if (session.getAttribute("primary_key") == null) {
			return (-1);
		}

		Integer id = (Integer) session.getAttribute("primary_key");
		try {
			user = dao.getbyKey(id);
		} catch (Exception e) {
			return (-1);
		}

		return (id);
	}

	public static boolean isAdmin(HttpServletRequest request) {
		int user_id = getUserID(request);
		if (user_id == -1) {
			return false;
		}
		UserDao dao = new UserDaoJDBC();
		if (dao.getbyKey(user_id).getUser_type().equals(UserType.fromString("Admin"))) {
			return true;
		}
		return false;
	}

	public static void doLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		try {
			session.invalidate();
		} catch(Exception e) {
			return;
		}
	}

	public static void doLogin(HttpServletRequest request, int user_id) {
		HttpSession session = request.getSession(true);
		session.setAttribute("primary_key", user_id);
	}
}
