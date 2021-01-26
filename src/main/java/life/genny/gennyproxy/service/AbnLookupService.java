package life.genny.gennyproxy.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import com.google.gson.Gson;
import io.vertx.mutiny.ext.web.client.WebClient;
import life.genny.gennyproxy.model.abn.AbnSearchResult;
import life.genny.gennyproxy.application.ApiKeyRetriever;
 import org.xml.sax.SAXException;

import static java.net.URLEncoder.encode;


@ApplicationScoped
public class AbnLookupService {

  @Inject
  private WebClient webClient;

  @Inject
  private ApiKeyRetriever apiKeyRetriever;

  private static final String UTF_8 = "UTF-8";
  private static final String callbackName = "c";

  private static final Gson GSON = new Gson();

  public AbnSearchResult retrieveCompanyAbn(String searchedName, int pageSize) throws UnsupportedEncodingException {

    String abnKey = apiKeyRetriever.retrieveApiKey("ENV_ABN_SEARCH_APIKEY_", "ENV_ABN_SEARCH_APIKEY_DEFAULT");

    String callbackResult = webClient.get("abr.business.gov.au", "/json/MatchingNames.aspx")
            .port(443)
            .setQueryParam("callback", callbackName)
            .setQueryParam("name", searchedName)
            .setQueryParam("maxResults", String.valueOf(pageSize))
            .setQueryParam("guid", abnKey)
            .send()
            .await()
            .atMost(Duration.ofSeconds(15))
            .bodyAsString();
    System.out.println(getCallbackJson(callbackResult));
    return GSON.fromJson(getCallbackJson(callbackResult), AbnSearchResult.class);
  }

  private String getCallbackJson(String callbackResult) {
    return callbackResult.substring(callbackName.length() + 1, callbackResult.length() - 1);
  }
}
