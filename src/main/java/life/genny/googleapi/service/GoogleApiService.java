package life.genny.googleapi.service;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;


import io.vertx.mutiny.ext.web.client.WebClient;
import life.genny.googleapi.model.address.Addresses;
import life.genny.googleapi.model.timezone.TimezoneResp;
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

    public String retrieveGoogleMapApi(String apiKey) {

        return webClient.get(mapPath)
                .setQueryParam("key", apiKey)
                .setQueryParam("libraries","places,drawing")
                .send()
                .await()
                .atMost(Duration.ofSeconds(15))
                .bodyAsString();
    }

    public TimezoneResp retrieveGoogleTimeZoneApi(String location, long timestamp, String apiKey) {
        //639%20lonsdale%20st
        return webClient.get(timezonePath)
                .setQueryParam("key", apiKey)
                .setQueryParam("location", location)
                .setQueryParam("timestamp", String.valueOf(timestamp))
                .send()
                .await()
                .atMost(Duration.ofSeconds(15))
                .bodyAsJson(TimezoneResp.class);
    }


    public Addresses retrieveGoogleAddressApi(String address, String apiKey){

        return webClient.get(addressPath)
                .setQueryParam("key", apiKey)
                .setQueryParam("address", address)
                .send()
                .await()
                .atMost(Duration.ofSeconds(15))
                .bodyAsJson(Addresses.class);

    }
}
