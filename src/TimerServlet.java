

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Timer;
/**
 * Servlet implementation class TimerServlet
 */
@WebServlet("/TimerServlet")
public class TimerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public  void  init()  throws  ServletException{
		// might need to be on a seperate thread?
		
		while (true) {
			long startTime = System.currentTimeMillis();
			long elapsedTime = 0L;
			long MINUTES_PER_LOOP = 30L;
			while (elapsedTime < MINUTES_PER_LOOP*60*1000) {
			    elapsedTime = (new Date()).getTime() - startTime;
			}
			
			// Code that needs to be looped:
			
		}
	}


}
