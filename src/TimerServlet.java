
import java.util.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crypto.alert.CoinMarketCapAPI;
import crypto.alert.TwitterAPI;
import datamodel.Currency;
import datamodel.Follow;
import datamodel.User;
import util.UtilDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.stream.Collectors;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
		
		private List<User> users;
		private List<Follow> follows;
		private List<Currency> currencies;
		
		public TimerThread() {
			users = new ArrayList<>();
			follows = new ArrayList<>();
			currencies = new ArrayList<>();
		}
		
		public void run() {
			long startTime = System.currentTimeMillis();
			List<Follow> bitcoinFollows = new ArrayList<>();
			List<Follow> dogecoinFollows = new ArrayList<>();
			List<Follow> cardanoFollows = new ArrayList<>();
			List<Follow> ethereumFollows = new ArrayList<>();
			int i = 0;
			while (true) {
				System.out.println("[DEBUG] - " + this.getName() + ": New Thread is running on iteration " + i++);
				try {
					
					// update list of users
					users = UtilDB.listUsers();
					follows = UtilDB.listFollow();
					currencies = UtilDB.listCurrencies();
					
					
					
					int bitcoinId = -1;
					int dogecoinId = -1;
					int cardanoId = -1;
					int ethereumId = -1;
					
					for (Currency coin : currencies) {
						if (coin.getCurrencyName().equals("bitcoin")) {
							bitcoinId = coin.getCurrencyID();
						} else if (coin.getCurrencyName().equals("dogecoin")) {
							dogecoinId = coin.getCurrencyID();
						} else if (coin.getCurrencyName().equals("cardano")) {
							cardanoId = coin.getCurrencyID();
						} else if (coin.getCurrencyName().equals("ethereum")) {
							ethereumId = coin.getCurrencyID();
						}
					}
					if (bitcoinId != -1 && dogecoinId != -1 && cardanoId != -1 && ethereumId != -1) {
						final int bitcoinId2 = bitcoinId;
						final int dogecoinId2 = dogecoinId;
						final int cardanoId2 = cardanoId;
						final int ethereumId2 = ethereumId;
						bitcoinFollows = follows.stream().filter(f -> f.getCurrencyID() == bitcoinId2).collect(Collectors.toList());
						dogecoinFollows = follows.stream().filter(f -> f.getCurrencyID() == dogecoinId2).collect(Collectors.toList());
						cardanoFollows = follows.stream().filter(f -> f.getCurrencyID() == cardanoId2).collect(Collectors.toList());
						ethereumFollows = follows.stream().filter(f -> f.getCurrencyID() == ethereumId2).collect(Collectors.toList());
						System.out.println("[DEBUG] - grabbing coin market prices.");
						CoinMarketCapAPI coinAPI = new CoinMarketCapAPI();
						coinAPI.getQuotes();
						
						System.out.println("[DEBUG] - grabbing tweet from api.");
						ArrayList<String> tweets = TwitterAPI.getTweetsFromList();
						System.out.println("[DEBUG] - sending emails.");
						for (String tweet : tweets) {
							tweet = tweet.toLowerCase();
							if (tweet.contains("$btc") || tweet.contains("bitcoin")) {
								notifyUsers(bitcoinFollows, tweet);
							} if (tweet.contains("$doge") || tweet.contains("dogecoin")) {
								notifyUsers(dogecoinFollows, tweet);
							} if (tweet.contains("$ada") || tweet.contains("cardano")) {
								notifyUsers(cardanoFollows, tweet);
							} if (tweet.contains("$eth") || tweet.contains("ethereum")) {
								notifyUsers(ethereumFollows, tweet);
							}
						}
					} else {
						System.out.println("[ERROR] - failed to acquire coin ids. Timer set for another 30 minutes.");
					}
					System.out.println("[DEBUG] - thread now sleeping.");
					Thread.sleep(MINUTES * 60 * 1000);
				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}
		
		private void notifyUsers(List<Follow> followers, String tweet) {	
			for (Follow follow : followers) {
				User user = users.stream().filter(u -> u.getId() == follow.getUserID()).findFirst().get();
				sendMail(user.getUserEmail(), tweet);
				System.out.println("[DEBUG] - send email to " + user.getUserEmail() + " info about tweet: " +  tweet);
			}
			System.out.println("[DEBUG] - done sending emails.");
		}
		
		private boolean sendMail(String to, String tweet) {
	        // Sender's email ID needs to be mentioned
	        String from = "teamcryptoalert@gmail.com";

	        // Assuming you are sending email from through gmails smtp
	        String host = "smtp.gmail.com";

	        // Get system properties
	        Properties properties = System.getProperties();

	        // Setup mail server
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "465");
	        properties.put("mail.smtp.ssl.enable", "true");
	        properties.put("mail.smtp.auth", "true");

	        // Get the Session object.// and pass username and password
	        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication("teamcryptoalert@gmail.com", "CryptoAlert8!");

	            }

	        });

	        // Used to debug SMTP issues
	        session.setDebug(true);

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Set Subject: header field
	            message.setSubject("TeamCryptoAlert Twitter notification");

	            // Now set the actual message
	            message.setText("A user followed by TeamCryptoAlert has mentioned a crypto of interest to you. See the tweet below:\n\n" + tweet);

	            System.out.println("sending...");
	            // Send message
	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	            return true;
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	            return false;
	        }
	    }
	}
	
	

	// BTC DOGE ADA ETH
	// bitcoin , dogecoin, cardano, ethereum
	
	
	
}
