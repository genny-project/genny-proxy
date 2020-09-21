package life.genny.abn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class AbnLookup {

  private static final String UTF_8 = "UTF-8";
  private static final String callbackName = "c";
  private static final String abnKey = System.getenv("ABN_KEY");

  private static String getCallbackJson(String callbackResult) {
    String jsonRes = callbackResult.substring(callbackName.length() + 1, callbackResult.length() - 1);
    return jsonRes;
  }

  public static AbnSearchResult searchByName(String searchedName, int pageSize)
      throws URISyntaxException, IOException, SAXException, ParserConfigurationException, FactoryConfigurationError {
    AbnSearchResult results = null;

    String params = "";

    params += "callback=" + URLEncoder.encode(callbackName, UTF_8);

    params += "&name=" + URLEncoder.encode(searchedName, UTF_8);
    params += "&maxResults=" + pageSize;

    params += "&guid=" + URLEncoder.encode(abnKey, UTF_8);

    results = doRequest("ABRSearchByNameAdvancedSimpleProtocol", params);

    return results;
  }

  private static AbnSearchResult doRequest(String service, String parameters)
      throws URISyntaxException, IOException, SAXException, ParserConfigurationException, FactoryConfigurationError {
    AbnSearchResult res = null;

    URL url = new URL("https://abr.business.gov.au/json/MatchingNames.aspx?" + parameters);

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    System.out.println(url);
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Content-Type", "text/javascript; charset-utf-8");
    connection.connect();
    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line = streamReader.readLine();
      String jsonRes = getCallbackJson(line);
      Jsonb jsonb = JsonbBuilder.create();
      res = jsonb.fromJson(jsonRes, AbnSearchResult.class);
      System.out.println(res);
    }

    connection.disconnect();

    return res;
  }

}
