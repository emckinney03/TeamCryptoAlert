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
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import datamodel.Currency;
import util.UtilDB;

public class CoinMarketCapAPI {

	private HashMap<String, Double> coinPriceMap;
	
	private static String apiKey = "c66d2c50-5235-4426-aef8-70e557d8a743";
	
	public CoinMarketCapAPI() {
		coinPriceMap = new HashMap<>();
		List<Currency> currencyList = UtilDB.listCurrencies();
		for (Currency c : currencyList) {
			coinPriceMap.put(c.getCurrencyName(), 0.0);
		}
	}
	
	// slug is term for coin. i.e bitcoin's slug is bitcoin. This may need to change to use Ids.
	public void addCoin(String slug) {
		
	}
	

	
	public void getQuotes() {
		
		// Set up API request
		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		List<String> slugList = Arrays.asList((String[]) coinPriceMap.keySet().toArray());
		// slugList.add("bitcoin");
		// TODO : Get all coins from DB and add to slugList. Or however thats gonna work.
		String queryParam = "";
		for (int i = 0; i < slugList.size(); i++) { 
			if (i == 0) {
				queryParam += slugList.get(0);
			} else {
				queryParam += "," + slugList.get(0);
			}
		}
	    parameters.add(new BasicNameValuePair("slug",queryParam));
	    
	    try {
	        String result = makeAPICall(uri, parameters);
	        System.out.println(result);
	        parseForPrice(result, slugList);
	      } catch (IOException e) {
	        System.out.println("Error: cannont access content - " + e.toString());
	      } catch (URISyntaxException e) {
	        System.out.println("Error: Invalid URL " + e.toString());
	      }
	    // this can def be improved
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
	
	public void parseForPrice(String json, List<String> slugList) {
        if (json.equals("NA")) {
            System.out.println("Somethings broken. Skipping parse.");
            return;
        }
        int count = 0;
        JSONObject obj = new JSONObject(json).getJSONObject("data");
        for (String key: obj.keySet()) {
            JSONObject coin = obj.getJSONObject(key);
            System.out.println(coin.toString());
            this.coinPriceMap.put(slugList.get(count), coin.getJSONObject("quote").getJSONObject("USD").getDouble("price"));
            count++;
        }

    }
	
}
