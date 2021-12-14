package session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datamodel.User;
import util.UtilDB;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        String userName = "";
        Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("validCredentials")) userName = cookie.getValue();
				break;
			}
		}
        System.out.println(userName + " is deleting account");
        
        User user = UtilDB.lookupUser(userName).get(0);
        
        UtilDB.deleteFollows(user.getId());
        UtilDB.deleteUser(user.getId());
        
     
		if(cookies != null){
			for(Cookie cookie : cookies){
				System.out.println(cookie.getName() + ", " + cookie.getValue());
				cookie = new Cookie(cookie.getName(),""); //deleting value of cookie  
				cookie.setMaxAge(0); //changing the maximum age to 0 seconds  
				response.addCookie(cookie); //adding cookie in the response  
				System.out.println(cookie.getName() + ", " + cookie.getValue());
			}
		}
		
        response.sendRedirect(request.getContextPath() + "/login.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
