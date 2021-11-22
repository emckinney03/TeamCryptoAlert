

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
    protected static final long MINUTES = 30;
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
		System.out.println("[DEBUG] - Starting timer thread.");
		TimerThread timer = new TimerThread();
		timer.start();
	}
	
	private class TimerThread extends Thread {
		public void run() {
	        long startTime = System.currentTimeMillis();
	        int i = 0;
	        while (true) {
	            System.out.println("[DEBUG] - " + this.getName() + ": New Thread is running on iteration " + i++);
	            try {
	                // TODO : run all code needed on loop
	                Thread.sleep(MINUTES * 60 *1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	           
	        }
	    }
	}


}
