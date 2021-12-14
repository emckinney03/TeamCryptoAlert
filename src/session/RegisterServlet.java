package session;


import datamodel.User;
import datamodel.Follow;
import util.UtilDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userPass = request.getParameter("userPass");
		String userEmail = request.getParameter("userEmail");
		UtilDB.createUser(userName, userPass, userEmail);
		boolean crypto1= false;
		boolean crypto2 = false;
		boolean crypto3 = false;
		boolean crypto4 = false;
		
		if (crypto1 == true) {
	UtilDB.createFollow(1, UtilDB.lookupUser(userName).get(0).getId());	
		}
		if (crypto2 == true) {
			UtilDB.createFollow(2, UtilDB.lookupUser(userName).get(0).getId());		
				}
		if (crypto3 == true) {
			UtilDB.createFollow(3, UtilDB.lookupUser(userName).get(0).getId());
				}
		if (crypto4 == true) {
			UtilDB.createFollow(4, UtilDB.lookupUser(userName).get(0).getId());	
				}
		response.setContentType("text/html");

		if (UtilDB.lookupUser(userName).size() > 0) {
			userRegistrationFailed(response.getWriter(), userName);
		}
		else {
			UtilDB.createUser(userName, userPass, userEmail);
			userRegistrationSuccess(response.getWriter(), userName);
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
		rd.include(request, response);
	}

	void userRegistrationSuccess(PrintWriter out, String userName) {
		out.println("<script type=\"text/javascript\">");  
		out.println("alert('User Registered: " + userName + "');");  
		out.println("</script>");
	}

	void userRegistrationFailed(PrintWriter out, String userName) {
		out.println("<script type=\"text/javascript\">");  
		out.println("alert('Username: \"" + userName + "\" is Already Taken');");  
		out.println("</script>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
