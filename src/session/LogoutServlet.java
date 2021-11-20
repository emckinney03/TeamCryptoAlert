package session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// internal session/cookie code was modified from Kasun Dharmasdasa (https://medium.com/@kasunpdh/session-management-in-java-using-servlet-filters-and-cookies-7c536b40448f)
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        
        Cookie[] cookies = request.getCookies();
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
     	//-------------------------------------------------------------------
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
