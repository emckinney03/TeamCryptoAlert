package session;

import util.UtilDB;
import datamodel.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

//To maintain login session from Kasun Dharmasdasa (https://medium.com/@kasunpdh/session-management-in-java-using-servlet-filters-and-cookies-7c536b40448f)
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
//------------------------------------------------

/**
 * Servlet implementation class LoginUser
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	private boolean validateLogin(String userName, String userPass) {
		boolean validCredentials = false;
		List<User> matchingUsers = UtilDB.lookupUser(userName);
		if (matchingUsers.size() > 0) {
			User targetUser = matchingUsers.get(0);
			if (userName.contentEquals(targetUser.getUserName()) && userPass.contentEquals(targetUser.getUserPass())) {
				validCredentials = true;
			}
		}
		return validCredentials;
	}
	
	public void loginFailure(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");  
		out.println("<script type=\"text/javascript\">");  
		out.println("alert('Username or Password were incorrect.');");  
		out.println("</script>");
		rd.include(request, response);
	}
	
	// internal session/cookie code was modified from Kasun Dharmasdasa (https://medium.com/@kasunpdh/session-management-in-java-using-servlet-filters-and-cookies-7c536b40448f)
	public void createSession(HttpServletRequest request, HttpServletResponse response, String userName) throws ServletException, IOException {
		//get the old session and invalidate
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}
		//generate a new session
		HttpSession newSession = request.getSession(true);

		//setting session to expiry in 10 mins
		newSession.setMaxInactiveInterval(10*60);

		Cookie loginSuccess = new Cookie("validCredentials", userName);
		response.addCookie(loginSuccess);
		response.sendRedirect("/TechExercise/session.jsp");
	}
	//-------------------------------------------------------------------
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userPass = request.getParameter("userPass");

		if (userName == null || userPass == null) {
			loginFailure(request, response);
		}
		else {
			if (validateLogin(userName, userPass)) {
				// response.sendRedirect("/TechExercise/session.html");
				createSession(request, response, userName);
			}
			else {
				loginFailure(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
