package get.data;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import rows.AllDataRow;

public class GetApiData {
	
	private HashMap<String, AllDataRow> allDataMap;
	private String link;

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public GetApiData(HashMap<String, AllDataRow> map, String link) {
		super();
		this.allDataMap = map;
		this.link = link;
	}

	public String getData() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(link);

		try (CloseableHttpResponse response = httpClient.execute(request)) {
			System.out.println("Status line: " + response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println("Content-type: " + headers);
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				System.out.println("Result :\n" + result);
				return result;
			}
		}
		return null;
	}

}
