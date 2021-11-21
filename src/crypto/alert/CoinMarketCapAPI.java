package crypto.alert;

import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
public class CoinMarketCapAPI {

	private HashMap<String, Double> coinPriceMap;
	
	private static String apiKey = "c66d2c50-5235-4426-aef8-70e557d8a743";
	
	public CoinMarketCapAPI() {
		coinPriceMap = new HashMap<>();
		
	}
	
	// slug is term for coin. i.e bitcoin's slug is bitcoin. This may need to change to use Ids.
	public void addCoin(String slug) {
		
	}
	
	public String getQuotes() {
		
		// Set up API request
		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
		List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
		List<String> slugList = new ArrayList<>();
		slugList.add("bitcoin");
		// TODO : Get all coins from DB and add to slugList. Or however thats gonna work.
		String queryParam = "";
		for (int i = 0; i < slugList.size(); i++) { 
			if (i == 0) {
				queryParam += slugList.get(0);
			} else {
				queryParam += "," + slugList.get(0);
			}
		}
	    paratmers.add(new BasicNameValuePair("slug",queryParam));
	    
	    // Make API request
	    // TODO : do stuff with new info and what not. parse json?
	    try {
	        String result = makeAPICall(uri, paratmers);
	        System.out.println(result);
	        return result;
	      } catch (IOException e) {
	        System.out.println("Error: cannont access content - " + e.toString());
	      } catch (URISyntaxException e) {
	        System.out.println("Error: Invalid URL " + e.toString());
	      }
	    // this can def be improved
		return "NA";
	}
	
	public static String makeAPICall(String uri, List<NameValuePair> parameters)
		      throws URISyntaxException, IOException {
		    String response_content = "";

		    URIBuilder query = new URIBuilder(uri);
		    query.addParameters(parameters);

		    CloseableHttpClient client = HttpClients.createDefault();
		    HttpGet request = new HttpGet(query.build());

		    request.setHeader(HttpHeaders.ACCEPT, "application/json");
		    request.addHeader("X-CMC_PRO_API_KEY", apiKey);

		    CloseableHttpResponse response = client.execute(request);

		    try {
		      System.out.println(response.getStatusLine());
		      HttpEntity entity = response.getEntity();
		      response_content = EntityUtils.toString(entity);
		      EntityUtils.consume(entity);
		    } finally {
		      response.close();
		    }

		    return response_content;
		  }
	
}
