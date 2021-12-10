
import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crypto.alert.CoinMarketCapAPI;
import crypto.alert.TwitterAPI;
import datamodel.User;
import util.UtilDB;

import java.util.ArrayList;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		System.out.println("[DEBUG] - Starting timer thread.");
		TimerThread timer = new TimerThread();
		timer.start();
	}

	private class TimerThread extends Thread {
		
		List<User> users;
		
		public TimerThread() {
			users = new ArrayList<>();
		}
		
		public void run() {
			long startTime = System.currentTimeMillis();
			int i = 0;
			while (true) {
				System.out.println("[DEBUG] - " + this.getName() + ": New Thread is running on iteration " + i++);
				try {
					
					// update list of users
					users = UtilDB.listUsers();
					
					CoinMarketCapAPI coinAPI = new CoinMarketCapAPI();
					coinAPI.getQuotes();
					

					ArrayList<String> tweets = TwitterAPI.getTweetsFromList();
					
					for (String tweet : tweets) {
						tweet = tweet.toLowerCase();
						if (tweet.contains("$btc") || tweet.contains("bitcoin")) {
							notifyUsers("bitcoin");
						} if (tweet.contains("$doge") || tweet.contains("dogecoin")) {
							notifyUsers("dogecoin");
						} if (tweet.contains("$ada") || tweet.contains("cardano")) {
							notifyUsers("cardano");
						} if (tweet.contains("$eth") || tweet.contains("ethereum")) {
							notifyUsers("ethereum");
						}
					}
					
					Thread.sleep(MINUTES * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();

				}

			}
		}
		
		private void notifyUsers(String coin) {
			
		}
	}
	
	

	// BTC DOGE ADA ETH
	// bitcoin , dogecoin, cardano, ethereum
	
	
	
}
