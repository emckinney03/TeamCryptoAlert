package crypto.alert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONObject;

public class TwitterAPI {
	
	// bitcoin ethereum, dogecoin, cardano

	private static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAKnNVwEAAAAAMgOJo7a5myIVyoCvSq6rhmh8kl0%3DvFynAnfUJAU9nKMPb44Egr4oOr8moL6ltjbO8z4HiVpdf5KZyb";
	private static final String LIST_TWEET_URL = "https://api.twitter.com/2/lists/1462976429876989952/tweets";
	
	public static ArrayList<String> getTweetsFromList() {
		try {
			String json = makeAPICall(LIST_TWEET_URL, false);
			return parseTweets(json);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String makeAPICall(String url, boolean user)
            throws URISyntaxException, IOException {
        String response_content = "";
        List<NameValuePair> parameters = new ArrayList<>();
        if (!user)
            parameters.add(new BasicNameValuePair("expansions","author_id"));
        URIBuilder query = new URIBuilder(url);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("Authorization", "Bearer " + BEARER_TOKEN);
        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        System.out.println(response_content);
        return response_content;
    }

    public static ArrayList<String> parseTweets(String json) {
    	ArrayList<String> list = new ArrayList<>();
        if (json.equals("NA")) {
            System.out.println("Somethings broken. Skipping parse. Twitter.");
            return list;
        }
        int count = 0;
        JSONArray arr = (new JSONObject(json)).getJSONArray("data");
        Iterator<Object> it = arr.iterator();
        while (it.hasNext()) {

            JSONObject obj = (JSONObject) it.next();
            String author_id = obj.getString("author_id");
            String text = obj.getString("text");
            list.add(text);
//            try {
//                String userJson = makeAPICall("https://api.twitter.com/2/users/" + author_id, true);
//                JSONObject userJSONObj = new JSONObject(userJson);
//                String username = userJSONObj.getJSONObject("data").getString("username");
//                System.out.println(username);
//                break;
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        return list;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
