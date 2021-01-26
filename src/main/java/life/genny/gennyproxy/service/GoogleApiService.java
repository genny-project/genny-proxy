package life.genny.gennyproxy.service;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;


import io.vertx.mutiny.ext.web.client.WebClient;
import life.genny.gennyproxy.application.ApiKeyRetriever;
import life.genny.gennyproxy.model.address.Addresses;
import life.genny.gennyproxy.model.timezone.TimezoneResp;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ApplicationScoped
public class GoogleApiService {

    @ConfigProperty(name = "quarkus.google.api.map.path")
    private String mapPath;

    @ConfigProperty(name = "quarkus.google.api.timezone.path")
    private String timezonePath;

    @ConfigProperty(name = "quarkus.google.api.address.path")
    private String addressPath;

    @Inject
    private WebClient webClient;

    @Inject
    private ApiKeyRetriever apiKeyRetriever;

    public String retrieveGoogleMapApi( ) {
        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_MAPS_APIKEY_", "ENV_GOOGLE_MAPS_APIKEY_DEFAULT");

        return webClient.get(mapPath)
                .setQueryParam("key", apiKey)
                .setQueryParam("libraries","places,drawing")
                .send()
                .await()
                .atMost(Duration.ofSeconds(15))
                .bodyAsString();
    }

    public TimezoneResp retrieveGoogleTimeZoneApi(String location, long timestamp) {
        //639%20lonsdale%20st
        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY_", "ENV_GOOGLE_TIMEZONE_APIKEY_DEFAULT");

        return webClient.get(timezonePath)
                .setQueryParam("key", apiKey)
                .setQueryParam("location", location)
                .setQueryParam("timestamp", String.valueOf(timestamp))
                .send()
                .await()
                .atMost(Duration.ofSeconds(15))
                .bodyAsJson(TimezoneResp.class);
    }


    public Addresses retrieveGoogleAddressApi(String address){

        String apiKey = apiKeyRetriever.retrieveApiKey("ENV_GOOGLE_TIMEZONE_APIKEY_", "ENV_GOOGLE_TIMEZONE_APIKEY_DEFAULT");

        return webClient.get(addressPath)
                .setQueryParam("key", apiKey)
                .setQueryParam("address", address)
                .send()
                .await()
                .atMost(Duration.ofSeconds(15))
                .bodyAsJson(Addresses.class);

    }
}
